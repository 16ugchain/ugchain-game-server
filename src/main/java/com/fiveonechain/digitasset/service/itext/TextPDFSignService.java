package com.fiveonechain.digitasset.service.itext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by fanjl on 2016/11/22.
 */
public class TextPDFSignService {
    protected byte[] digest;
    protected MessageDigest md;

    public TextPDFSignService(){}

    protected TextPDFSignService(String password, String algorithm, String provider) throws GeneralSecurityException {
        if (provider == null)
            md = MessageDigest.getInstance(algorithm);
        else
            md = MessageDigest.getInstance(algorithm, provider);
        digest = md.digest(password.getBytes());
    }

    public static TextPDFSignService getInstance(String password, String algorithm) throws GeneralSecurityException {
        return new TextPDFSignService(password, algorithm, null);
    }

    public int getDigestSize() {
        return digest.length;
    }

    public String getDigestAsHexString() {
        return new BigInteger(1, digest).toString(16);
    }


    public boolean checkPassword(String password) {
        return Arrays.equals(digest, md.digest(password.getBytes()));
    }

    public static void showTest(String algorithm) {
        try {
            TextPDFSignService app = getInstance("password", algorithm);
            System.out.println("Digest using " + algorithm + ": " + app.getDigestSize());
            System.out.println("Digest: " + app.getDigestAsHexString());
            System.out.println("Is the password 'password'? " + app.checkPassword("password"));
            System.out.println("Is the password 'secret'? " + app.checkPassword("secret"));
        } catch (GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testAll() {
        showTest("MD5");
        showTest("SHA-1");
        showTest("SHA-224");
        showTest("SHA-256");
        showTest("SHA-384");
        showTest("SHA-512");
        showTest("RIPEMD128");
        showTest("RIPEMD160");
        showTest("RIPEMD256");
    }

    public static final String SRC = "src/main/resources/hello.pdf";
    public static final String DEST = "results/chapter3/hello_cacert.pdf";

    public void sign(String src, String dest,
                     Certificate[] chain, PrivateKey pk,
                     String digestAlgorithm, String provider, CryptoStandard subfilter,
                     String reason, String location,
                     Collection<CrlClient> crlList,
                     OcspClient ocspClient,
                     TSAClient tsaClient,
                     int estimatedSize)
            throws GeneralSecurityException, IOException, DocumentException {
        // Creating the reader and the stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        // Creating the appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason(reason);
        appearance.setLocation(location);
        appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, "sig");
        // Creating the signature
        ExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize, subfilter);
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException, DocumentException {
        String path = "PRIVATE";
        char[] pass = "PASSWORD".toCharArray();

        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance("pkcs12", provider.getName());
        ks.load(new FileInputStream(path), pass);
        String alias = (String)ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, pass);
        Certificate[] chain = ks.getCertificateChain(alias);
        TextPDFSignService app = new TextPDFSignService();
        app.sign(SRC, DEST, chain, pk, DigestAlgorithms.SHA256, provider.getName(), CryptoStandard.CMS, "Test", "Ghent", null, null, null, 0);
    }

}
