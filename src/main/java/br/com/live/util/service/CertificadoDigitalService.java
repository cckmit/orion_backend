package br.com.live.util.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;

@Service
public class CertificadoDigitalService {

    private static final String CAMINHO_ARQUIVO_PFX = "C:\\tempArquivos\\LIVE!FRANCHISINGLTDA.pfx";
    private static final char[] PASSWORD = "123456".toCharArray();

    public CertificadoDigitalService() {
    }

    public String assinarXmlNfs() throws Exception {
        try (InputStream inputStream = new FileInputStream(CAMINHO_ARQUIVO_PFX)) {
            KeyStore keyStore = carregarKeyStore(inputStream);
            X509Certificate certificado = (X509Certificate) keyStore.getCertificate(keyStore.aliases().nextElement());
            String digestValue, signatureValue, certificadoBase64;
            Document documento = criarDocumentoXML();

            // Realizar assinatura
            DOMSignContext dsc = criarContextoAssinatura(keyStore, documento, certificado);
            Element signatureElement = (Element) documento.getElementsByTagName("Signature").item(0);
            digestValue = signatureElement.getElementsByTagName("DigestValue").item(0).getTextContent();
            signatureValue = signatureElement.getElementsByTagName("SignatureValue").item(0).getTextContent();
            certificadoBase64 = Base64.getEncoder().encodeToString(certificado.getEncoded());

            String xmlAssinado = getXmlAssinado(digestValue, signatureValue, certificadoBase64);

            return xmlAssinado;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao assinar o XML");
            return "";
        }
    }

    private KeyStore carregarKeyStore(InputStream inputStream) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(inputStream, PASSWORD);
        System.out.println("Certificado carregado com sucesso");
        return keyStore;
    }

    private Document criarDocumentoXML() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf.newDocumentBuilder().newDocument();
    }

    private DOMSignContext criarContextoAssinatura(KeyStore keyStore, Document documento, X509Certificate certificado)
            throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException,
            ParserConfigurationException, MarshalException, XMLSignatureException, InvalidAlgorithmParameterException, KeyException {

        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
                Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                null, null);

        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                        (C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                Collections.singletonList(ref));

        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(certificado.getPublicKey());

        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

        String alias = keyStore.aliases().nextElement();

        System.out.println("Alias do certificado: " + alias);

        PrivateKey chavePrivada = (PrivateKey) keyStore.getKey(keyStore.aliases().nextElement(), PASSWORD);
        DOMSignContext dsc = new DOMSignContext(chavePrivada, documento);

        XMLSignature signature = fac.newXMLSignature(si, ki);
        signature.sign(dsc);

        return dsc;
    }

    public String getXmlAssinado(String digestValue, String signatureValue, String certificado) {

        return "<xd:Signature>"
                + "<xd:SignedInfo>"
                + "<xd:CanonicalizationMethod xd:Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n20010315\"/>"
                + "<xd:SignatureMethod xd:Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>"
                + "<xd:Reference URI=\"\">"
                + "<xd:Transforms>"
                + "<xd:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n20010315\"/>"
                + "<xd:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>"
                + "</xd:Transforms>"
                + "<xd:DigestMethod xd:Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>"
                + "<xd:DigestValue>" + digestValue + "</xd:DigestValue>"
                + "</xd:Reference>"
                + "</xd:SignedInfo>"
                + "<xd:SignatureValue>" + signatureValue + "</xd:SignatureValue>"
                + "<xd:KeyInfo>"
                + "<xd:X509Data>"
                + "<xd:X509Certificate>" + certificado + "</xd:X509Certificate>"
                + "</xd:X509Data>"
                + "</xd:KeyInfo>"
                + "</xd:Signature>";
    }
}
