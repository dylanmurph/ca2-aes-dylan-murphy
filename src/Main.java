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
            System.out.print("""
                    \n  -------------------------
                     | --- AES  Encryption --- |
                     | 1. Encrypt a File       |
                     | 2. Decrypt a File       |
                     | 3. Quit                 |
                     | -- Choose 1 , 2 or 3 -- |
                      -------------------------
                      Enter Option: \s""");

            input = kb.nextLine();

            switch (input) {
                case "1":
                    Encrypt.encryptFile();
                    break;
                case "2":
                    Decrypt.decryptFile();
                    break;
                case "3":
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Error, Please type a valid option.");
            }
        } while (!input.equals("3"));
    }
}
