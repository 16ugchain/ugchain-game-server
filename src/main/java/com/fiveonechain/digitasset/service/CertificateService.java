package com.fiveonechain.digitasset.service;

import java.security.KeyPair;
import java.security.cert.Certificate;

/**
 * Created by yuanshichao on 2016/11/23.
 */
public interface CertificateService {

    KeyPair generateKeyPair();

    Certificate signSelfCertificate(KeyPair keyPair, String commonName);

    byte[] generatePKCS12(Certificate cert, KeyPair keyPair, String alias, String password);

    KeyPair loadKeyPair(byte[] p12Bytes, String alias, String password);

    Certificate loadCertificate(byte[] p12Bytes, String alias, String password);
}
