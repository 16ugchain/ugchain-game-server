package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.CerConfig;
import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.PdfSignException;
import com.fiveonechain.digitasset.service.CertificateService;
import com.fiveonechain.digitasset.service.IGuaranteeCorpService;
import com.fiveonechain.digitasset.service.ISignPDFService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;

/**
 * Created by fanjl on 2016/11/23.
 */
@RestController
@RequestMapping("pdf")
public class CorpContractController {
    @Autowired
    private IGuaranteeCorpService guaranteeCorpService;
    @Autowired
    private CerConfig cerConfig;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private ISignPDFService signPDFService;

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public Result addCorpDetail(@AuthenticationPrincipal UserContext userContext

    ) {
        GuaranteeCorp guaranteeCorp = guaranteeCorpService.findByUserId(userContext.getUserId());
        Certificate certificate = certificateService.loadCertificate(guaranteeCorp.getPkcs12(),guaranteeCorp.getCorp_name(),cerConfig.getPassword());
        KeyPair keyPair = certificateService.loadKeyPair(guaranteeCorp.getPkcs12(),guaranteeCorp.getCorp_name(),cerConfig.getPassword());
        try {
            InputStream inputStream = new FileInputStream("results/objects/addImage.pdf");
            byte[] data = signPDFService.sign(inputStream,certificate,keyPair);
            Result result = ResultUtil.success(data);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/createPdf", method = RequestMethod.POST)
    public Result addCorpDetail(@AuthenticationPrincipal UserContext userContext,
                                @RequestParam("content") String content,
                                @RequestParam("pdfPath") String path,
                                @RequestParam("imagePath") String imagePath

    ) {
        signPDFService.imageToPdf(path,content,imagePath,20,20);
        Result result = ResultUtil.success();
        return result;
    }

    @ExceptionHandler(PdfSignException.class)
    @ResponseBody
    public Result handlePdfSignException() {
        return ResultUtil.buildErrorResult(ErrorInfo.PDF_SIGN_ERROR);
    }
}
