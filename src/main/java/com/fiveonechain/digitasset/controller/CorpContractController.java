package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.CerConfig;
import com.fiveonechain.digitasset.domain.Contract;
import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.PdfSignException;
import com.fiveonechain.digitasset.exception.PdfVerifyException;
import com.fiveonechain.digitasset.service.CertificateService;
import com.fiveonechain.digitasset.service.GuaranteeCorpService;
import com.fiveonechain.digitasset.service.SignPDFService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.KeyPair;
import java.security.cert.Certificate;

/**
 * Created by fanjl on 2016/11/23.
 */
@RestController
@RequestMapping("pdf")
public class CorpContractController {
    @Autowired
    private GuaranteeCorpService guaranteeCorpService;
    @Autowired
    private CerConfig cerConfig;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private SignPDFService signPDFService;

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public Result sign(@AuthenticationPrincipal UserContext userContext

    ) {
        GuaranteeCorp guaranteeCorp = guaranteeCorpService.findByUserId(userContext.getUserId());
        if (guaranteeCorp == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.CORP_NOT_FOUND);
            return result;
        }
        Certificate certificate = certificateService.loadCertificate(guaranteeCorp.getPkcs12(), guaranteeCorp.getCorp_name(), cerConfig.getPassword());
        if (certificate == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.CORP_CERTIFICATE_NOT_FOUND);
            return result;
        }
        KeyPair keyPair = certificateService.loadKeyPair(guaranteeCorp.getPkcs12(), guaranteeCorp.getCorp_name(), cerConfig.getPassword());
        if (certificate == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.CORP_KEYPAIR_NOT_FOUND);
            return result;
        }
        try {
            // TODO: generate contract

            InputStream inputStream = new FileInputStream("results/objects/addImage.pdf");
            byte[] data = signPDFService.sign(inputStream, certificate, keyPair, cerConfig.getPassword());
            Contract contract = new Contract();
            contract.setContent(data);
            int contractId = signPDFService.insertSignContract(contract);
            if (contractId == -1) {
                Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
                return result;
            } else {
                Result result = ResultUtil.success(contractId);
                return result;
            }
        } catch (FileNotFoundException e) {
            throw new PdfSignException(e.getMessage());
        } catch (IOException e) {
            throw new PdfSignException(e.getMessage());
        }
    }

    @RequestMapping(value = "/{contractId}/verify", method = RequestMethod.POST)
    public Result verify(@AuthenticationPrincipal UserContext userContext,
                         @PathVariable("contractId") int contract_id
    ) {
        Contract contract = signPDFService.findContractById(contract_id);
        if (contract == null) {
            return ResultUtil.buildErrorResult(ErrorInfo.CONTRACT_NOT_FOUND);
        }
        byte[] content = contract.getContent();
        boolean result = signPDFService.verifyPKCS7(content);
        return ResultUtil.success(result);
    }


    @RequestMapping(value = "/{contractId}/downloadSignPdf", method = RequestMethod.POST)
    public Result downloadSignPdf(@AuthenticationPrincipal UserContext userContext,
                                  @PathVariable("contractId") int contractId
    ) {
        try {
            Contract contract = signPDFService.findContractById(contractId);
            String filePath = cerConfig.getSignPdfPath();
            FileOutputStream file = new FileOutputStream(filePath);
            PDDocument document = PDDocument.load(contract.getContent());
            document.saveIncremental(file);
            return ResultUtil.success(filePath);
        } catch (IOException e) {
            return ResultUtil.buildErrorResult(ErrorInfo.DOWNLOAD_ERROR);
        }
    }


    @ExceptionHandler(PdfSignException.class)
    @ResponseBody
    public Result handlePdfSignException() {
        return ResultUtil.buildErrorResult(ErrorInfo.PDF_SIGN_ERROR);
    }

    @ExceptionHandler(PdfVerifyException.class)
    @ResponseBody
    public Result handlePdfVerifyException() {
        return ResultUtil.buildErrorResult(ErrorInfo.PDF_VERIFY_ERROR);
    }
}
