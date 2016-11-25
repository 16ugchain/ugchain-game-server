/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fiveonechain.digitasset.service.signature;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * This will read a document from the filesystem, decrypt it and do something with the signature.
 *
 * @author Ben Litchfield
 */
public final class ShowSignature {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowSignature.class);

    private byte[] content;

    public ShowSignature(byte[] content) {
        this.content = content;
    }


    public boolean showSignature() throws IOException, CertificateException,
            NoSuchAlgorithmException, InvalidKeyException,
            NoSuchProviderException, SignatureException {

        PDDocument document = null;
        try {
            document = PDDocument.load(content);
            for (PDSignature sig : document.getSignatureDictionaries()) {
                COSDictionary sigDict = sig.getCOSObject();
                COSString contents = (COSString) sigDict.getDictionaryObject(COSName.CONTENTS);
                ByteArrayInputStream fis = new ByteArrayInputStream(content);
                byte[] buf = null;
                try {
                    buf = sig.getSignedContent(fis);
                } finally {
                    fis.close();
                }
                LOGGER.info("Signature found");
                LOGGER.info("Name:     " + sig.getName());
                LOGGER.info("Modified: " + sdf.format(sig.getSignDate().getTime()));
                String subFilter = sig.getSubFilter();
                if (subFilter != null) {
                    if (subFilter.equals("adbe.pkcs7.detached")) {
                        return verifyPKCS7(buf, contents, sig);
                        //TODO check certificate chain, revocation lists, timestamp...
                    } else if (subFilter.equals("adbe.pkcs7.sha1")) {
                        COSString certString = (COSString) sigDict.getDictionaryObject(
                                COSName.CONTENTS);
                        byte[] certData = certString.getBytes();
                        CertificateFactory factory = CertificateFactory.getInstance("X.509");
                        ByteArrayInputStream certStream = new ByteArrayInputStream(certData);
                        Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
                        System.out.println("certs=" + certs);
                        byte[] hash = MessageDigest.getInstance("SHA1").digest(buf);
                        return verifyPKCS7(hash, contents, sig);
                        //TODO check certificate chain, revocation lists, timestamp...
                    } else if (subFilter.equals("adbe.x509.rsa_sha1")) {
                        // example: PDFBOX-2693.pdf
                        COSString certString = (COSString) sigDict.getDictionaryObject(
                                COSName.getPDFName("Cert"));
                        byte[] certData = certString.getBytes();
                        CertificateFactory factory = CertificateFactory.getInstance("X.509");
                        ByteArrayInputStream certStream = new ByteArrayInputStream(certData);
                        Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
                        LOGGER.info("certs=" + certs);
                        //TODO verify signature
                    } else {
                        LOGGER.error("Unknown certificate type: " + subFilter);
                    }
                } else {
                    throw new IOException("Missing subfilter for cert dictionary");
                }
            }
        } catch (CMSException ex) {
            throw new IOException(ex);
        } catch (OperatorCreationException ex) {
            throw new IOException(ex);
        } finally {
            if (document != null) {
                document.close();
            }
        }
        return false;

    }

    /**
     * Verify a PKCS7 signature.
     *
     * @param byteArray the byte sequence that has been signed
     * @param contents  the /Contents field as a COSString
     * @param sig       the PDF signature (the /V dictionary)
     * @throws CertificateException
     * @throws CMSException
     * @throws StoreException
     * @throws OperatorCreationException
     */
    private boolean verifyPKCS7(byte[] byteArray, COSString contents, PDSignature sig)
            throws CMSException, CertificateException, StoreException, OperatorCreationException {
        CMSProcessable signedContent = new CMSProcessableByteArray(byteArray);
        CMSSignedData signedData = new CMSSignedData(signedContent, contents.getBytes());
        Store certificatesStore = signedData.getCertificates();
        Collection<SignerInformation> signers = signedData.getSignerInfos().getSigners();
        SignerInformation signerInformation = signers.iterator().next();
        Collection matches = certificatesStore.getMatches(signerInformation.getSID());
        X509CertificateHolder certificateHolder = (X509CertificateHolder) matches.iterator().next();
        X509Certificate certFromSignedData = new JcaX509CertificateConverter().getCertificate(certificateHolder);
        LOGGER.info("certFromSignedData: " + certFromSignedData);
        certFromSignedData.checkValidity(sig.getSignDate().getTime());

        if (signerInformation.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certFromSignedData))) {
            LOGGER.info("Signature verified");
            return true;
        } else {
            LOGGER.info("Signature verification failed");
            return false;
        }
    }

}
