import java.io.*;
import java.nio.file.*;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("AVAILABLE ARGUMENTS: GENERATE, ENCRYPTFILE, DECRYPTFILE, ENCRYPTDIR, DECRYPTDIR");
            System.exit(2);
        }
        if (args[0].equals("GENERATE")) {
            if (args.length == 1) {
                System.out.println("GENERATE REQUIRES OVERWRITE ARGUMENT, TRUE/FALSE");
                System.out.println("WARNING: DO NOT OVERWRITE KEYS THAT HAVE BEEN USED TO ENCRYPT DATA");
                System.exit(2);
            }
            boolean overwrite = Boolean.parseBoolean(args[1].toLowerCase());
            InitKeys.main(overwrite);
        } else if (args[0].equals("ENCRYPTFILE")) {
            if (args.length < 4) {
                System.out.println("Invalid Syntax, correct syntax is: ENCRYPTFILE FILEPATH SECRETPATH OVERRIDE(true/false)");
                System.exit(2);
            }
            EncryptFile.encrypt(new File(args[1]),new File(args[2]),Boolean.parseBoolean(args[3].toLowerCase()));
        } else if (args[0].equals("DECRYPTFILE")) {
            if (args.length < 4) {
                System.out.println("Invalid Syntax, correct syntax is: DECRYPTFILE FILEPATH SECRETPATH OVERRIDE(true/false)");
                System.exit(2);
            }
            DecryptFile.decrypt(new File(args[1]),new File(args[2]),Boolean.parseBoolean(args[3].toLowerCase()));
        } else if (args[0].equals("ENCRYPTDIR")) {
            if (args.length < 4) {
                System.out.println("Invalid Syntax, correct syntax is: ENCRYPTDIR DIRPATH SECRETPATH OVERRIDE(true/false)");
                System.exit(2);
            }
            EncryptDir.encrypt(new File(args[1]).toPath(),new File(args[2]),Boolean.parseBoolean(args[3].toLowerCase()),null);
        } else if (args[0].equals("DECRYPTDIR")) {
            if (args.length < 4) {
                System.out.println("Invalid Syntax, correct syntax is: DECRYPTDIR DIRPATH SECRETPATH OVERRIDE(true/false)");
                System.exit(2);
            }
            DecryptDir.decrypt(new File(args[1]).toPath(),new File(args[2]),Boolean.parseBoolean(args[3].toLowerCase()),null);
        }
    }   
}