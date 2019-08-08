import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.nio.file.*;
public class EncryptDir {
    public static void encrypt(Path dir, File secret, boolean overwrite, File superdir) {
        try {
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
            String dirname = dir.toFile().getName();
            byte[] dirnamebytes = dirname.getBytes();
            byte[] dirnamenc = Cryptography.encryptData(dirnamebytes,key,iv);
            char[] base64dirname = Base64.getEncoder().encodeToString(dirnamenc).toCharArray();
            for(int i=0;i<base64dirname.length;i++) {
                if (base64dirname[i]=='/') {
                    base64dirname[i]='@';
                }
            }
            String base64name = new String(base64dirname);
            File cryptdir = null;
            if (superdir==null) {
                cryptdir = new File(dir.toAbsolutePath().toString().substring(0,dir.toAbsolutePath().toString().lastIndexOf(File.separator)+1) + base64name);
            } else {
                cryptdir = new File(superdir.getAbsolutePath().toString() + File.separator + base64name);
            }
            cryptdir.mkdir();
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for(Path file : stream) {
                try {
                    File tf = file.toFile();
                    if (tf.isDirectory()) {
                        System.out.println("ENCRYPTING DIR: " + tf.toString());
                        EncryptDir.encrypt(tf.toPath(),secret,overwrite,cryptdir);
                    } else if (tf.isFile()) {
                        System.out.println("ENCRYPTING FILE: " + tf.toString());
                        EncryptFile.encryptDest(tf,secret,overwrite,cryptdir);
                    }
                } catch (Exception e2) {
                    System.out.println("ERROR ENCRYPTING A FILE/SUBDIR IN TARGET DIR");
                    e2.printStackTrace();
                }
            }
            stream.close();
            dir.toFile().delete();
        } catch (Exception e) {
            System.out.println("ERROR ENCRYPTING DIRECTORY CONTENTS");
            e.printStackTrace();
        }
    }
}