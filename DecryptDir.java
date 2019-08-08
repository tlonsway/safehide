import java.util.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.nio.file.*;
public class DecryptDir {
    public static void decrypt(Path dir, File secret, boolean overwrite, File superdir) {
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
            char[] dirnameb64 = dirname.toCharArray();
            for(int i=0;i<dirnameb64.length;i++) {
                if (dirnameb64[i]=='@') {
                    dirnameb64[i]='/';
                }
            }
            byte[] encname = Base64.getDecoder().decode(new String(dirnameb64));
            byte[] dirnamedecoded = Cryptography.decryptData(encname,key,iv);
            String pdirname = new String(dirnamedecoded);
            File dcryptdir = null;
            if (superdir==null) {
                dcryptdir = new File(dir.toAbsolutePath().toString().substring(0,dir.toAbsolutePath().toString().lastIndexOf(File.separator)+1) + pdirname);
            } else {
                dcryptdir = new File(superdir.getAbsolutePath().toString() + File.separator + pdirname);
            }
            dcryptdir.mkdir();
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for(Path file : stream) {
                try {
                    File tf = file.toFile();
                    if (tf.isDirectory()) {
                        System.out.println("DECRYPTING DIR: " + tf.toString());
                        DecryptDir.decrypt(tf.toPath(),secret,overwrite,dcryptdir);
                    } else if (tf.isFile()) {
                        System.out.println("DECRYPTING FILE: " + tf.toString());
                        DecryptFile.decryptDest(tf,secret,overwrite,dcryptdir);
                    }
                } catch (Exception e2) {
                    System.out.println("ERROR DECRYPTING A FILE/SUBDIR IN TARGET DIR");
                    e2.printStackTrace();
                }
            }
            stream.close();
            dir.toFile().delete();
        } catch (Exception e) {
            System.out.println("ERROR DECRYPTING DIRECTORY CONTENTS");
            e.printStackTrace();
        }
    }
}