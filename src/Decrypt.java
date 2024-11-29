import java.util.Scanner;
import java.io.File;

public class Decrypt {
    private static final Scanner kb = new Scanner(System.in);

    public static void decryptFile() {
        String fileContent, decryptedContent;

        while (true) {
            fileContent = FilesUtil.getValidFileContent("Enter name of file to decrypt:");
            //if getValidFileContent returned an empty String, rerun prompt to obtain file
            if (fileContent.isEmpty()) {
                continue;
            }
            break;
        }

        System.out.println("Enter the decryption key:");
        System.out.println("(Enter '0' to return to main menu)");
        String key = kb.nextLine();
        if (key.equals("0")) {
            Main.showMenu();
        }

        //attempting to decrypt the fileContent using the user input key
        decryptedContent = CipherUtil.decrypt(fileContent, key);
        //CipherUtil.decrypt() will throw an exception if there's a problem, but I use this if statement to
        //ensure the code doesn't continue
        if (decryptedContent == null) {
            System.out.println("Make sure you are selecting the correct file.");
            return;
        }

        if (FilesUtil.confirmFileEmpty("plaintext.txt")) {
            FilesUtil.writeFile("plaintext.txt", decryptedContent);
            System.out.println("\nFile decrypted successfully!");
            System.out.println("Decrypted file saved to: " + new File("plaintext.txt").getAbsolutePath());
            System.out.println("Decrypted content:");
            System.out.println(decryptedContent);
            return;
        }
        System.out.println("Error decrypting file.");
    }
}