import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
public class InitKeys {
    public static void main(boolean overwrite) {
        SecretKey key = Cryptography.genKey();
        byte[] keydata = key.getEncoded();
        byte[] iv = Cryptography.genIV();
        System.out.println("BEGIN_AES128_KEY");
        System.out.println(Base64.getEncoder().encodeToString(keydata));
        System.out.println("END_KEY");
        System.out.println("BEGIN_AES128_IV");
        System.out.println(Base64.getEncoder().encodeToString(iv));
        System.out.println("END_IV");
        File out = new File("key.secret");
        if (out.exists() && !overwrite) {
            System.out.print("\nERROR EXPORTING KEYS, KEYFILE ALREADY EXISTS");
            try {
                key = null;
                keydata = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                iv = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                System.gc();
            } catch (Exception e) {
                System.out.println("ERROR CLEARING SECRET FROM MEMORY, SHUT DOWN COMPUTER");
                e.printStackTrace();
            }
            System.exit(-1);
        }
        try {
            out.createNewFile();
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(out));
            writer.write(keydata);
            writer.write(iv);
            //fake data so key size cannot be predicted
            int randbytes = (int)(Math.random()*2049);
            byte[] fake = new byte[randbytes];
            new Random().nextBytes(fake);
            writer.write(fake);
            writer.flush();
            writer.close();
            System.out.print("\nKEYFILE EXPORT SUCCESSFUL");
            try {
                key = null;
                keydata = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                iv = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                System.gc();
            } catch (Exception e) {
                System.out.println("\nERROR CLEARING SECRET FROM MEMORY, SHUT DOWN COMPUTER");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.print("\nERROR EXPORTING KEYS");
            e.printStackTrace();
            try {
                key = null;
                keydata = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                iv = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                System.gc();
            } catch (Exception e2) {
                System.out.println("\nERROR CLEARING SECRET FROM MEMORY, SHUT DOWN COMPUTER");
                e.printStackTrace();
            }
            System.exit(-2);
        }
    }
}