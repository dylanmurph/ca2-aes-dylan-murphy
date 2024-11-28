import java.util.Scanner;

public class DecryptionUtil {
    private static final Scanner kb = new Scanner(System.in);

    public static void decryptFile() {
        String ciphertextFile, fileContent, decryptedContent, inputKey;

        while (true) {
            System.out.println("Enter name of file to decrypt:");
            ciphertextFile = kb.nextLine();
            fileContent = FilesUtil.readFile(ciphertextFile);

            if (FilesUtil.isValidTxt(ciphertextFile) && !fileContent.isEmpty()) {
                break;
            } else {
                System.out.println("Please Try Again");
            }
        }

        System.out.println("Enter the decryption key:");
        inputKey = kb.nextLine();

        //placeholder
        decryptedContent = "Decrypted text placeholder";

        FilesUtil.writeFile("plaintext.txt", decryptedContent);
        System.out.println("File decrypted successfully to plaintext.txt \nkey: " + inputKey);
    }
}