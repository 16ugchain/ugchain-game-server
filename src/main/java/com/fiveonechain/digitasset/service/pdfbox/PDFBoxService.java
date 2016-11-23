package com.fiveonechain.digitasset.service.pdfbox;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by fanjl on 2016/11/23.
 */
public class PDFBoxService {
    static {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
    }

    public static void main(String[] args) throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        File file = new File("results/objects/addImage.pdf");
        CreateSignature createSignature = new CreateSignature();
        createSignature.signDetached(file);
    }
}
