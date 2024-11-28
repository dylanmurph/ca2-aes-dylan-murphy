import java.util.Scanner;

public class EncryptionUtil {
    private static final Scanner kb = new Scanner(System.in);

    public static void encryptFile() {
        String plaintextFile, fileContent, encryptedContent, key;

        while (true) {
            System.out.println("Enter name of file to encrypt");
            plaintextFile = kb.nextLine();
            fileContent = FilesUtil.readFile(plaintextFile);

            if (FilesUtil.isValidTxt(plaintextFile) && !fileContent.isEmpty()) {
                break;
            } else {
                System.out.println("Please Try Again");
            }
        }

        //placeholder
        encryptedContent = "Encrypted text placeholder";
        key = "key output placeholder";

        FilesUtil.writeFile("ciphertext.txt", encryptedContent);
        System.out.println("File encrypted successfully to ciphertext.txt \nKey: " + key);
    }
}