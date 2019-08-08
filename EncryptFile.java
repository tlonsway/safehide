import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.nio.file.Files;
public class EncryptFile {
    public static void encrypt(File pfile, File secret, boolean overwrite) {
        try {
            byte[] ofile = Files.readAllBytes(pfile.toPath());
            byte[] ofilename = pfile.getName().getBytes();
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
            byte[] encfile = Cryptography.encryptData(ofile,key,iv);
            byte[] encfilename = Cryptography.encryptData(ofilename,key,iv);
            char[] base64namecarr = Base64.getEncoder().encodeToString(encfilename).toCharArray();
            for(int i=0;i<base64namecarr.length;i++) {
                if (base64namecarr[i]=='/') {
                    base64namecarr[i]='@';
                }
            }
            String base64name = new String(base64namecarr);
            File out = new File(pfile.getAbsolutePath().substring(0,pfile.getAbsolutePath().lastIndexOf(File.separator)+1) + base64name + ".crypt");
            out.createNewFile();
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(out));
            writer.write(encfile);
            writer.flush();
            writer.close();
            if (overwrite) {
                BufferedOutputStream wipe = new BufferedOutputStream(new FileOutputStream(pfile));
                for(int i=0;i<10000;i++) {
                    byte[] wb = new byte[2];
                    new Random().nextBytes(wb);
                    wipe.write(wb);
                    wipe.flush();
                }
                wipe.close();
                pfile.delete();
            }
        } catch (Exception e) {
            System.out.println("ERROR ENCRYPTING FILE");
            e.printStackTrace();
        }
    }
    public static void encryptDest(File pfile, File secret, boolean overwrite, File dest) {
        try {
            byte[] ofile = Files.readAllBytes(pfile.toPath());
            byte[] ofilename = pfile.getName().getBytes();
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
            byte[] encfile = Cryptography.encryptData(ofile,key,iv);
            byte[] encfilename = Cryptography.encryptData(ofilename,key,iv);
            char[] base64namecarr = Base64.getEncoder().encodeToString(encfilename).toCharArray();
            for(int i=0;i<base64namecarr.length;i++) {
                if (base64namecarr[i]=='/') {
                    base64namecarr[i]='@';
                }
            }
            String base64name = new String(base64namecarr);
            File out = new File(dest.getAbsolutePath() + File.separator + base64name + ".crypt");
            out.createNewFile();
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(out));
            writer.write(encfile);
            writer.flush();
            writer.close();
            if (overwrite) {
                BufferedOutputStream wipe = new BufferedOutputStream(new FileOutputStream(pfile));
                for(int i=0;i<10000;i++) {
                    byte[] wb = new byte[2];
                    new Random().nextBytes(wb);
                    wipe.write(wb);
                    wipe.flush();
                }
                wipe.close();
                pfile.delete();
            }
        } catch (Exception e) {
            System.out.println("ERROR ENCRYPTING FILE");
            e.printStackTrace();
        }
    }
}