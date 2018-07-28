package com.xinfu.attorneyuser.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSA {

    private static String priKey;
    private String pubKey;

    public static RSA instance;


    /**
     * 获取工具类实例
     * @return
     */
    public static RSA getInstance(){
        if(instance == null){
            instance = new RSA();
        }
        return instance;
    }
    public static void main(String[] args) {
        RSA rsa = new RSA();
        String str = "123456";
        System.out.println("原文:"+str);
        String crypt = rsa.encryptByPrivateKey(str);
        System.out.println("私钥加密密文:"+crypt);
//		crypt = "BS5+J6LXPt4ecR4nTcMg8n8jzR5qISa5As8LhGcyRMOtWmTTiktza8Hkt3fhpymFW5ykSL2XTvW1HU25zuuHqlmiL/YYflMAXZBBZPJ4QSYdGEcgvDus4VXahcMNSRAqfJv7bp/hYY32kR0qNDIetAE3l/YvDb9yqY4OXNmFIhs=";
//		crypt = "AKvhH8jnqAR8QcA4IeeG5R9JF%2ByvoA5Q3NIKiQZLkxaSBzykHLWjjqlnL73Lys9h6XLiHZju0HpkGedrhxQggC6uHYfZwXHbniiyQ6rBzGaR%2FfEeRAhAsybclRfTsmtGMtdsatYRnR5X%2Fx%2F3WQe9inDp%2FT8jHcHBb5tA7dFzAno%3D";
        String result = rsa.decryptByPublicKey(crypt);
        try {
            System.out.println("原文:"+result.getBytes("utf-8").toString()+"==长度："+crypt.length());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("---");

        str = "123456我要加密这段文字。";
        System.out.println("原文:"+str);
        crypt = rsa.encryptByPublicKey(str);
        System.out.println("公钥加密密文:"+crypt);
        result = rsa.decryptByPrivateKey(crypt);
        System.out.println("原文:"+result+"==长度："+crypt.length());

        System.out.println("---");

        str = "1234";
        System.out.println("原文："+str);
//		String str1 = rsa.signByPrivateKey(str);
        String str1 = sign(str, priKey);
        System.out.println("签名结果："+str1);
        if(rsa.verifyByPublicKey(str1, str)){
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
    }

    public RSA(){
//		priKey = readStringFromFile("java_private.pem");
//		pubKey = readStringFromFile("java_public.pem");
//		priKey = 	"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKbVhyEl0dXL2Jew"+
//					"N9B8wQHaZ07nC5oSKpQR6sc7sdsoqS+G4h9zuFpBXlspOIpnvoWeSRckwX6icytC"+
//					"uuqThOJaVhmiKV6w8zzyOvTVn0yaHMpXu9ZuhWFhx3xzDYy5rQfAAzKX1MO3k4ta"+
//					"ZPTQLj8u7vaVEvbVq/FFO+YNCgCpAgMBAAECgYB1x4s1eJiyAc4wEITm2Bv+Lez/"+
//					"BBfptmd+z0NbUiZW3VbLqcLbh3ufpERzwR8cfu8/L6bUAuvjddYutVZ2Ip0Nd7dG"+
//					"5rrktH+7R8UT89fn87bUa5NlLee+egyoz/PJ63X4JjEg5OJbkXMbK4YrTypS0IAx"+
//					"nZv+7BeSsCrzNlpWAQJBANgmHMDNrIWvU3qVf7u8SS/g+WrlvKMWOXtYjH2OqWoO"+
//					"Vtmh4Or1PbaPIMnPAXFYiYYW8wcLYnVmVCez5qaysWkCQQDFl9XONZIMFAvdJ5S2"+
//					"UFk63bEYtCroKZjddTlE6K/j+Vj2IaCFm94i4x1YzJR0KrykrtBTLRi7nuWmdJMJ"+
//					"r61BAkA7dxDGAk+KX9fJi8OedIh2AaDcxeOFwqGBy7Sq/kqhgNxn918XhOy7gtj0"+
//					"bFzrP/5lw36M25b00XgpjBbSmaqxAkBnBN/TUHjh1T3OQ0m0uDWdjGI+KAlK3A04"+
//					"QVrng43ZBXMNeMDRiE+Lzu/JEXjBDFsoXYB+LT/86j5/x721yiNBAkEAgi0F5BvA"+
//					"wYZQXqAx3iyuj8R9uUKpLePafyBRHnLNrFux2VD0ZX3pXCmfDDmtM/NMO491dI84"+
//					"6NbVOvxWcNPQ/Q==";
		pubKey = 	"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2IFe9Y+YrYWLxB7R+W/Bt+KQZ" +
                "l//mCvz5v2y1Lf6HldmaTe8H1S+RaQTSuqpvC7yCKtP+oCdhpIOwdbPbR4QDcxpJ" +
                "gZw9XDmQQX/ki0yV2r+Pn1WB2wZmX6tbvws+lhL8DsCq+9WIaqMDqMpWwEkKT31W" +
                "hba+39DwfBRES24YJQIDAQAB";

        priKey = 	"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM9nUm7rPNhSgvsd"+
                "jMuCd5E7IMJB/80A1YY7jYV9fBCKdhVKmqea26QYuw6FW7B00fppEUTSazduSmn9"+
                "Yvhx9UOCcI75b0nq9FWm5O4P+Kp8l31M1pwsJ3cm+DceGOrFsl47vh9idiqj+abI"+
                "lJ4sTmJmDghmbks9YFlZSndQsIBlAgMBAAECgYAasa6vbgF3yi7niScc7l7bR2Pw"+
                "/LOivA+/ZhzR6JO2QUvvc5myJsFMPo6c0Nc7P93iv/EkDX0VNlHHkIBTf79URHXM"+
                "gXwMad4pHAeOiqxk5A9w/szDCBoETngtoqQGJq+QINxwPVvDEO4i224Uj3MKg2fo"+
                "4SDy3P1GCAAj1ahNoQJBAP4FV9vLWdLOOwOLnBpXt6vru4HT5VIf9fCeBIemuQ4C"+
                "/yRtgU38zXWgZ8AAmS6EjBEUDnN/tWid6UBKfgPDwAkCQQDRBP+Y9wIYIaSxeL7B"+
                "nHhPT25yAJCGK+l6r2qeaHVQr81O9YjusEi8E2M5OxCRolKxC3L7hrLJX8z1oyOV"+
                "dNx9AkBqYGhzpgv+qNiz2mJL8dH8ECMc8lTFeJbw5eu1tw8mHAEnCyisNSMBkGQC"+
                "Vv3PKjjR6hlHKwMYRZDpmIh/IRmpAkEAr1soLGaeZSxkhVetgbUJ4k/bct0yYr4Y"+
                "ZQshwcAVHBpBforT1JwkiVUim3MIFYY/JbVbQ9XfzL4Ir9OsGMkv6QJAPaQnyNY5"+
                "/D0PhXqODOM6jtAHHRfaSi4gve6AZ0iRz6YlB8beJ1ywZaJZWD9Cuw3zy4dDpCOn"+
                "A4tBsIdpMMoT+w==";
//        pubKey = 	"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPZ1Ju6zzYUoL7HYzLgneROyDC"+
//                "Qf/NANWGO42FfXwQinYVSpqnmtukGLsOhVuwdNH6aRFE0ms3bkpp/WL4cfVDgnCO"+
//                "+W9J6vRVpuTuD/iqfJd9TNacLCd3Jvg3HhjqxbJeO74fYnYqo/mmyJSeLE5iZg4I"+
//                "Zm5LPWBZWUp3ULCAZQIDAQAB";
    }

    /**
     * 使用私钥加密
     * @String data
     */
    public String encryptByPrivateKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pribyte = base64decode(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.ENCRYPT_MODE, privateKey);
            str = base64encode(c1.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    /**
     * 使用私钥解密
     * @String data
     */
    public String decryptByPrivateKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pribyte = base64decode(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] temp = c1.doFinal(base64decode(data));
            str = new String(temp);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }


    /**
     * 使用公钥加密
     * @String data
     */
    public String encryptByPublicKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.ENCRYPT_MODE, rsaPubKey);
            str = base64encode(c1.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    /**
     * 使用公钥解密
     * String data
     */
    public String decryptByPublicKey(String data) {
        // 加密
        String str = "";
        try {
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c1.init(Cipher.DECRYPT_MODE, rsaPubKey);
            byte[] temp = c1.doFinal(base64decode(data));
            str = new String(temp);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
    public static String sign(String content, String privateKey)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64Utils.decode(privateKey) );
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update( content.getBytes());
            byte[] signed = signature.sign();
            return Base64Utils.encode(signed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 本方法使用SHA1withRSA签名算法产生签名
     * @String src 签名的原字符串
     * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
     */
    public String signByPrivateKey(String src) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pribyte = base64decode(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes());
            byte[] signature = sigEng.sign();
            return base64encode(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用共钥验证签名
     * @param sign
     * @param src
     * @return
     */
    public boolean verifyByPublicKey(String sign, String src) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pubbyte = base64decode(pubKey.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            sigEng.initVerify(rsaPubKey);
            sigEng.update(src.getBytes());
            byte[] sign1 = base64decode(sign);
            return sigEng.verify(sign1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  base64加密
     * @param bstr
     * @return
     */
    @SuppressWarnings("restriction")
    private String base64encode(byte[] bstr) {
        String str =  new Base64Utils().encode(bstr);
        str = str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        return str;
    }

    /**
     * base64解密
     * @param str
     * @return byte[]
     */
    @SuppressWarnings("restriction")
    private byte[] base64decode(String str) {
        byte[] bt = null;
        bt = Base64Utils.decode(str);

        return bt;
    }

    /**
     * 从文件中读取所有字符串
     * @param fileName
     * @return	String
     */
    private String readStringFromFile(String fileName){
        StringBuffer str = new StringBuffer();
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            char[] temp = new char[1024];
            while (fr.read(temp) != -1) {
                str.append(temp);
            }
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return str.toString();
    }
}
