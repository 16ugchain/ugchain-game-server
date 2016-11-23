package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.exception.GuaranteeCertificateException;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by yuanshichao on 2016/11/23.
 */

@Component
public class CertificateServiceImpl implements CertificateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateServiceImpl.class);

    private static final String BC_PROVIDER = "BC";
    private static final String KEYPAIR_ALGORITHM = "RSA";
    private static final String SIGN_ALGORITHM = "SHA256withRSA";
    private static final int KEY_SIZE = 2048;
    private static final int SERIAL_NUMBER_SIZE = 128;
    private static final int EXPIRE_CYCLE_IN_YEAR = 32;
    private static final String KEYSTORE_TYPE = "pkcs12";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public KeyPair generateKeyPair() {
        SecureRandom sr = new SecureRandom();
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(KEYPAIR_ALGORITHM, BC_PROVIDER);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            LOGGER.error(e.getMessage());
            throw new GuaranteeCertificateException(e);
        }
        keyGen.initialize(KEY_SIZE, sr);
        KeyPair keypair = keyGen.generateKeyPair();
        return keypair;
    }

    @Override
    public Certificate signSelfCertificate(KeyPair keyPair, String commonName) {

        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, commonName);

        LocalDateTime now = LocalDateTime.now();
        Date notBefore = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date notAfter = Date.from(now.plusYears(EXPIRE_CYCLE_IN_YEAR).atZone(ZoneId.systemDefault()).toInstant());
        BigInteger serialNumber = new BigInteger(SERIAL_NUMBER_SIZE, new SecureRandom());

        X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                nameBuilder.build(),
                serialNumber,
                notBefore,
                notAfter,
                nameBuilder.build(),
                keyPair.getPublic());

        ContentSigner contentSigner;
        X509Certificate certificate;
        try {
            contentSigner = new JcaContentSignerBuilder(SIGN_ALGORITHM)
                    .setProvider(BC_PROVIDER).build(keyPair.getPrivate());
            certificate = new JcaX509CertificateConverter().getCertificate(certificateBuilder.build(contentSigner));
        } catch (OperatorCreationException | CertificateException e) {
            LOGGER.error(e.getMessage());
            throw new GuaranteeCertificateException(e);
        }

        return certificate;
    }

    @Override
    public byte[] generatePKCS12(Certificate cert, KeyPair keyPair, String alias, String password) {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            KeyStore result = KeyStore.getInstance(KEYSTORE_TYPE, BC_PROVIDER);
            result.load(null, null);
            result.setKeyEntry(alias, keyPair.getPrivate(),
                    password.toCharArray(), new Certificate[]{cert});
            result.store(bos, password.toCharArray());
            return bos.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new GuaranteeCertificateException(e);
        }
    }

    @Override
    public KeyPair loadKeyPair(byte[] p12Bytes, String alias, String password) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(p12Bytes)) {
            KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE, BC_PROVIDER);
            ks.load(bis, password.toCharArray());
            Certificate cert = ks.getCertificate(alias);
            PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
            return new KeyPair(cert.getPublicKey(), key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new GuaranteeCertificateException(e);
        }
    }

    @Override
    public Certificate loadCertificate(byte[] p12Bytes, String alias, String password) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(p12Bytes)) {
            KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE, BC_PROVIDER);
            ks.load(bis, password.toCharArray());
            Certificate cert = ks.getCertificate(alias);
            return cert;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new GuaranteeCertificateException(e);
        }
    }
}
