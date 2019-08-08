import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.nio.file.Files;
public class DecryptFile {
    public static void decrypt(File efile, File secret, boolean overwrite) {
        try {
            byte[] ofile = Files.readAllBytes(efile.toPath());
            String ofilename = efile.getName();
            byte[] keyfile = Files.readAllBytes(secret.toPath());
            byte[] keybytes = new byte[16];
            byte[] iv = new byte[16];
            for(int i=0;i<16;i++) {
                keybytes[i]=keyfile[i];
            }
            for (int i=16;i<32;i++) {
                iv[i-16]=keyfile[i];
            }
            SecretKey key = new SecretKeySpec(keybytes,"AES");
            byte[] decfile = Cryptography.decryptData(ofile,key,iv);
            char[] base64encnamecarr = ofilename.substring(0,ofilename.indexOf(".")).toCharArray();
            for(int i=0;i<base64encnamecarr.length;i++) {
                if (base64encnamecarr[i]=='@') {
                    base64encnamecarr[i]='/';
                }
            }
            byte[] encname = Base64.getDecoder().decode(new String(base64encnamecarr));
            byte[] filenamedecoded = Cryptography.decryptData(encname,key,iv);
            File out = new File(new String(filenamedecoded));
            out.createNewFile();
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(out));
            writer.write(decfile);
            writer.flush();
            writer.close();
            if (overwrite) {
                BufferedOutputStream wipe = new BufferedOutputStream(new FileOutputStream(efile));
                for(int i=0;i<10000;i++) {
                    byte[] wb = new byte[2];
                    new Random().nextBytes(wb);
                    wipe.write(wb);
                    wipe.flush();
                }
                wipe.close();
                efile.delete();
            }
        } catch (Exception e) {
            System.out.println("ERROR DECRYPTING FILE");
            e.printStackTrace();
        }
    }
}