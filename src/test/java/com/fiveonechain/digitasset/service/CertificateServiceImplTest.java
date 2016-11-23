package com.fiveonechain.digitasset.service;

import org.junit.Test;

import java.security.KeyPair;
import java.security.cert.Certificate;

import static org.junit.Assert.*;

/**
 * Created by yuanshichao on 2016/11/23.
 */
public class CertificateServiceImplTest {

    @Test
    public void generateKeyPair() throws Exception {

        CertificateServiceImpl srv = new CertificateServiceImpl();

        KeyPair keyPair = srv.generateKeyPair();
        Certificate cert = srv.signSelfCertificate(keyPair, "test");
        byte[] bytes = srv.generatePKCS12(cert, keyPair, "test", "123456");

        cert = srv.loadCertificate(bytes, "test", "123456");
        System.out.println(cert.toString());

        keyPair = srv.loadKeyPair(bytes, "test", "123456");

        System.out.println(keyPair.getPrivate().toString());
    }

}