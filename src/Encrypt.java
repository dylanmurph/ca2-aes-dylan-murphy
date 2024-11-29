import java.io.File;

public class Encrypt {
    public static void encryptFile() {
        String fileContent, encryptedContent;

        while (true) {
            fileContent = FilesUtil.getValidFileContent("Enter name of file to encrypt:");
            //if getValidFileContent returned an empty String, rerun prompt to obtain file
            if (fileContent.isEmpty()) {
                continue;
            }
            break;
        }

        //generating the secure key and passing it into my encrypt function
        String key = CipherUtil.generateNewKey();
        encryptedContent = CipherUtil.encrypt(fileContent, key);
        //CipherUtil.encrypt() will throw an exception if there's a problem, but I use this if statement to
        //ensure the code doesn't continue
        if (encryptedContent == null) {
            return;
        }

        if (FilesUtil.confirmFileEmpty("ciphertext.txt")) {
            FilesUtil.writeFile("ciphertext.txt", encryptedContent);
            System.out.println("\nFile encrypted successfully!");
            System.out.println("Encrypted file saved to: " + new File("ciphertext.txt").getAbsolutePath());
            System.out.println("Key for decryption (Keep this safe) :");
            System.out.println(key);
            return;
        }
        System.out.println("Error encrypting file.");
    }
}