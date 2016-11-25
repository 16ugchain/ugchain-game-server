/*
 * Copyright 2015 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public abstract class CreateSignatureBase implements SignatureInterface
{
    private PrivateKey privateKey;
    private Certificate certificate;
    private boolean externalSigning;

    public CreateSignatureBase(Certificate certificate, KeyPair keyPairs, char[] pin)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException
    {
            setPrivateKey(keyPairs.getPrivate());
            setCertificate(certificate);
            if (certificate instanceof X509Certificate)
            {
                // avoid expired certificate
                ((X509Certificate) certificate).checkValidity();
            }


        if (certificate == null)
        {
            throw new IOException("Could not find certificate");
        }
    }

    public final void setPrivateKey(PrivateKey privateKey)
    {
        this.privateKey = privateKey;
    }

    public final void setCertificate(Certificate certificate)
    {
        this.certificate = certificate;
    }

    @Override
    public byte[] sign(InputStream content) throws IOException
    {
        //TODO this method should be private
        try
        {
            List<Certificate> certList = new ArrayList<Certificate>();
            certList.add(certificate);
            Store certs = new JcaCertStore(certList);
            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
            org.bouncycastle.asn1.x509.Certificate cert = org.bouncycastle.asn1.x509.Certificate.getInstance(ASN1Primitive.fromByteArray(certificate.getEncoded()));
            ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").build(privateKey);
            gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(sha1Signer, new X509CertificateHolder(cert)));
            gen.addCertificates(certs);
            CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
            CMSSignedData signedData = gen.generate(msg, false);
            return signedData.getEncoded();
        }
        catch (GeneralSecurityException e)
        {
            throw new IOException(e);
        }
        catch (CMSException e)
        {
            throw new IOException(e);
        }
        catch (OperatorCreationException e)
        {
            throw new IOException(e);
        }
    }

    public void setExternalSigning(boolean externalSigning)
    {
        this.externalSigning = externalSigning;
    }

    public boolean isExternalSigning()
    {
        return externalSigning;
    }
}
