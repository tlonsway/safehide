import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
public class Cryptography {
    public static SecretKey genKey() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            SecretKey k = keygen.generateKey();
            return k;
        } catch (Exception e) {
            System.out.println("An error occured while generating a secure key");
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] genIV() {
        SecureRandom rand = new SecureRandom();
        byte[] iv = new byte[16];
        rand.nextBytes(iv);
        return iv;
    }
    public static byte[] encryptData(byte[] plain, SecretKey k, byte[] iv) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec ks = new SecretKeySpec(k.getEncoded(),"AES");
        IvParameterSpec ivs = new IvParameterSpec(iv);
        c.init(Cipher.ENCRYPT_MODE,ks,ivs);
        byte[] enc = c.doFinal(plain);
        return enc;
    }
    public static byte[] decryptData(byte[] plain, SecretKey k, byte[] iv) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec ks = new SecretKeySpec(k.getEncoded(),"AES");
        IvParameterSpec ivs = new IvParameterSpec(iv);
        c.init(Cipher.DECRYPT_MODE,ks,ivs);
        byte[] enc = c.doFinal(plain);
        return enc;
    }
}