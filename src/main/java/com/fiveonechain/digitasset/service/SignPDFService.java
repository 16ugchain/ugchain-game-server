package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.Contract;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;

/**
 * Created by fanjl on 2016/11/23.
 */
public interface SignPDFService {

    PDDocument imageToPdf(String outPath, String content, String imagePath, int imagex, int imagey);

    byte[] sign(InputStream content, Certificate certificate, KeyPair keyPair,String password);

    boolean verifyPKCS7(byte[] byteArray);

    int insertSignContract(Contract contract);

    Contract findContractById(int contract_id);

}
