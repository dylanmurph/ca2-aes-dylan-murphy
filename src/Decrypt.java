import java.util.Scanner;
import java.io.File;

public class Decrypt {

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

        //attempting to decrypt the fileContent using the user input key
        //user has 3 attempts before being locked out for 10 seconds and this multiplies each 3 failed attempts
        decryptedContent = verifyKey(fileContent);

        if (FilesUtil.confirmFileEmpty("plaintext.txt")) {
            FilesUtil.writeFile("plaintext.txt", decryptedContent);
            System.out.println(ColourUtil.green("\nFile decrypted successfully!"));
            System.out.println("Decrypted file saved to: \n" + new File("plaintext.txt").getAbsolutePath());
            System.out.println("Decrypted content:");
            System.out.println(decryptedContent);
            return;
        }
        System.out.println(ColourUtil.red("Error decrypting file."));
    }

    //Function to manage the number of attempts and lock users entering incorrect keys
    //returns decrypted content upon successful key input
    private static String verifyKey(String fileContent) {
        Scanner kb = new Scanner(System.in);
        int attempts = 0, failures = 0;
        while (true) {
            System.out.println("Enter the decryption key:");
            System.out.println("(Enter '0' to return to main menu)");
            String key = kb.nextLine();

            //if the user inputs nothing, it won't count as an attempt and restart the loop
            if (key.isEmpty()) {
                System.out.println(ColourUtil.red("Error: No key provided, please try again"));
                continue;
            }
            else if (key.equals("0")) {
                Main.showMenu();
            }

            String decryptedContent = CipherUtil.decrypt(fileContent, key);

            //if decryptedContent isn't empty it means the decryption was successful
            //if its empty it informs the user of an incorrect key, also provides error details from .decrypt()
            //once the user has 3 failed attempts it will lock them out for increasing amounts of time
            if (decryptedContent != null) {
                return decryptedContent;
            } else {
                attempts++;
                System.out.println(ColourUtil.red("Incorrect key. Attempt " + attempts + " of 3"));

                if (attempts >= 3) {
                    failures++;
                    attempts = 0;
                    lockAttempt(failures);
                }
            }
        }
    }

    //Function to process the lock and uses a multiple of total failures to calculate how long to lock the user for
    private static void lockAttempt(int failures) {
        int lockTime = 10 * failures;
        System.out.println(ColourUtil.red("Too many attempts, try again in " + lockTime + " seconds"));

        try {
            Thread.sleep(lockTime * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}