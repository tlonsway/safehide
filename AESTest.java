import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
public class AESTest {
    public static void main(String[] args) {
        SecretKey key = Cryptography.genKey();
        byte[] iv = Cryptography.genIV();
        String data = "Hello this is a sample data test, -=[];',./_+{}:\"<>?!@#$%^&*()1234567890\\";
        try {
            System.out.println("Original: " + data);
            byte[] encrypted = Cryptography.encryptData(data.getBytes(),key,iv);
            System.out.println("Encrypted(base64): " + Base64.getEncoder().encodeToString(encrypted));
            byte[] decrypted = Cryptography.decryptData(encrypted,key,iv);
            System.out.println("Decrypted: " + new String(decrypted));
        } catch (Exception e) {
            System.out.println("An error occured testing the Cryptography class");
            e.printStackTrace();
        }
    }
}