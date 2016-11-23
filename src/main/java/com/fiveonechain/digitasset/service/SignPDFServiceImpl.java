package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.exception.PdfSignException;
import com.fiveonechain.digitasset.service.signature.CMSProcessableInputStream;
import com.fiveonechain.digitasset.service.signature.CreateSignature;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
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
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.util.Store;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanjl on 2016/11/23.
 */
@Component
public class SignPDFServiceImpl implements ISignPDFService {
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

    public PDDocument signPdf(PDDocument pdfFile, String outPath, KeyStore keyStore, String password) {
        try {
            CreateSignature signing = new CreateSignature(keyStore, password.toCharArray());
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(new FileInputStream(outPath), password.toCharArray());
            // sign PDF
            FileOutputStream fos = new FileOutputStream(outPath);
            signing.signDetached(pdfFile, fos, null);
            File output = new File(outPath);
            PDDocument pdDocument = PDDocument.load(output);
            return pdDocument;
        } catch (IOException e) {
            throw new PdfSignException(outPath);
        } catch (CertificateException e) {
            throw new PdfSignException(outPath);
        } catch (NoSuchAlgorithmException e) {
            throw new PdfSignException(outPath);
        } catch (KeyStoreException e) {
            throw new PdfSignException(outPath);
        } catch (UnrecoverableKeyException e) {
            throw new PdfSignException(outPath);
        }

    }

    @Override
    public byte[] sign(InputStream content,Certificate certificate, KeyPair keyPair)  {
            try
            {
                List<Certificate> certList = new ArrayList<Certificate>();
                certList.add(certificate);
                Store certs = new JcaCertStore(certList);
                CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
                org.bouncycastle.asn1.x509.Certificate cert = org.bouncycastle.asn1.x509.Certificate.getInstance(ASN1Primitive.fromByteArray(certificate.getEncoded()));
                ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
                gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(sha1Signer, new X509CertificateHolder(cert)));
                gen.addCertificates(certs);
                CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
                CMSSignedData signedData = gen.generate(msg, false);
                return signedData.getEncoded();
            }
            catch (GeneralSecurityException e)
            {
                throw new PdfSignException(e.getMessage());
            }
            catch (CMSException e)
            {
                throw new PdfSignException(e.getMessage());
            } catch (OperatorCreationException e)
            {
                throw new PdfSignException(e.getMessage());
            } catch (IOException e) {
                throw new PdfSignException(e.getMessage());
            }
    }


}
