//AES ENCRYPTION PROJECT - D00223094 - Dylan Murphy
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        showMenu();
    }

    public static void showMenu() {
        Scanner kb = new Scanner(System.in);
        String input;

        do {
            System.out.println("--- AES Encryption Menu ---");
            System.out.println("1. Encrypt a File");
            System.out.println("2. Decrypt a File");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");

            input = kb.nextLine();

            switch (input) {
                case "1":
                    encryptFile();
                    break;
                case "2":
                    decryptFile();
                    break;
                case "3":
                    quitApplication();
                    break;
                default:
                    System.out.println("Error, Please type a valid option.");
            }
        } while (!input.equals("3"));
    }

    private static void encryptFile() {
        System.out.println("Encrypting file...");
    }

    private static void decryptFile() {
        System.out.println("Decrypting file...");
    }

    private static void quitApplication() {
        System.out.println("Exiting the application.");
    }
}
