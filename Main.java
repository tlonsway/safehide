import java.io.*;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("AVAILABLE ARGUMENTS: GENERATE, ENCRYPT, DECRYPT");
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
        } else if (args[0].equals("ENCRYPT")) {
            if (args.length < 4) {
                System.out.println("Invalid Syntax, correct syntax is: ENCRYPT FILEPATH SECRETPATH OVERRIDE(true/false)");
                System.exit(2);
            }
            EncryptFile.encrypt(new File(args[1]),new File(args[2]),Boolean.parseBoolean(args[3].toLowerCase()));
        } else if (args[0].equals("DECRYPT")) {
            if (args.length < 4) {
                System.out.println("Invalid Syntax, correct syntax is: DECRYPT FILEPATH SECRETPATH OVERRIDE(true/false)");
                System.exit(2);
            }
            DecryptFile.decrypt(new File(args[1]),new File(args[2]),Boolean.parseBoolean(args[3].toLowerCase()));
        }
    }   
}