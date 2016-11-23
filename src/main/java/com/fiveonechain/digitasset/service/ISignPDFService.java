package com.fiveonechain.digitasset.service;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;

/**
 * Created by fanjl on 2016/11/23.
 */
public interface ISignPDFService {

    PDDocument imageToPdf(String outPath, String content, String imagePath, int imagex, int imagey);

    byte[] sign(InputStream content, Certificate certificate, KeyPair keyPair);

}
