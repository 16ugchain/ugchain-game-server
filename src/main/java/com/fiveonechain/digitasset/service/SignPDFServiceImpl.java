package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.Contract;
import com.fiveonechain.digitasset.exception.PdfSignException;
import com.fiveonechain.digitasset.exception.PdfVerifyException;
import com.fiveonechain.digitasset.mapper.ContractMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import com.fiveonechain.digitasset.service.signature.CreateSignature;
import com.fiveonechain.digitasset.service.signature.ShowSignature;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * Created by fanjl on 2016/11/23.
 */
@Component
public class SignPDFServiceImpl implements SignPDFService {
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private SequenceMapper sequenceMapper;


    @Override
    public PDDocument imageToPdf(String outPath, String content, String imagePath, int imagex, int imagey) {
        //生成pdf，再加入图片
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(content);
            contents.endText();
            //add image to file
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
            contents.drawImage(pdImage, imagex, imagey);
            contents.close();
            doc.save(outPath);
        } catch (IOException e) {
            throw new PdfSignException(outPath);
        } finally {
            try {
                doc.close();
            } catch (IOException e) {
                throw new PdfSignException(outPath);
            }
        }

        return doc;
    }


    @Override
    public byte[] sign(InputStream content, Certificate certificate, KeyPair keyPair,String password) {
        try {
            CreateSignature createSignature = new CreateSignature(certificate,keyPair, password.toCharArray());
            PDDocument document = PDDocument.load(content);
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            createSignature.signDetached(document,fos);
            document.close();
            byte[] data = fos.toByteArray();
            return data;
        } catch (GeneralSecurityException e) {
            throw new PdfSignException(e.getMessage());
        }  catch (IOException e) {
            throw new PdfSignException(e.getMessage());
        }
    }

    @Override
    public boolean verifyPKCS7(byte[] byteArray) {
        ShowSignature show = new ShowSignature(byteArray);
        try {
            return  show.showSignature();
        } catch (IOException e) {
            throw new PdfVerifyException(e);
        } catch (CertificateException e) {
            throw new PdfVerifyException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new PdfVerifyException(e);
        } catch (InvalidKeyException e) {
            throw new PdfVerifyException(e);
        } catch (NoSuchProviderException e) {
            throw new PdfVerifyException(e);
        } catch (SignatureException e) {
            throw new PdfVerifyException(e);
        }
    }

    @Override
    public int insertSignContract(Contract contract) {
        int contractId = sequenceMapper.nextId(sequenceMapper.CONTRACT);
        contract.setContract_id(contractId);
        if (contractMapper.insert(contract) != 1) {
            return -1;
        } else {
            return contractId;
        }
    }

    @Override
    public Contract findContractById(int contract_id) {
        return contractMapper.findByContractId(contract_id);
    }


}
