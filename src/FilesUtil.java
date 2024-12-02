//This is where I am handling all the file management, it handles the read, write and creation of files as well
//as the validation of user input for files and file searches.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FilesUtil {
    private static final Scanner kb = new Scanner(System.in);
    public static final String TEXT_RED    = "\u001B[31m";
    public static final String TEXT_GREEN  = "\u001B[32m";
    //Function to verify the file is an existing valid .txt file.
    public static boolean isValidTxt(String filename) {
        //ensuring .txt extension is at the end of the filename
        filename = addTxtExt(filename);

        File f = new File(filename);

        //checks if the file exists
        //also makes sure it isn't a folder named "filename".txt with isFile()
        //https://stackoverflow.com/a/1816707
        if (f.isFile()) {
            return true;
        }
        //if the file is not found it will move to the fileNotFound() function and pass down the filename
        return fileNotFound(filename);
    }

    //Function so the user can choose to create a new file
    public static boolean fileNotFound(String filename) {
        String input;

        while (true) {
            System.out.println("""
                    Error: File not found.
                    Would you like to create it now?
                    1. Yes
                    2. No
                    Choose 1 or 2:(Enter '0' to return to main menu)""");
            input = kb.nextLine().toLowerCase();

            switch (input) {
                case "1":
                case "y":
                case "yes":
                    return createNewFile(filename);
                case "2":
                case "n":
                case "no":
                    return false;
                case "0":
                    Main.showMenu();
                default:
                    System.out.println(ColourUtil.red("Please enter '1' or '2'"));
            }
        }
    }

    //Function to create new files
    public static boolean createNewFile(String filename) {
        String input;

        System.out.println(" --- New File Creation --- ");
        while (true) {
            //I am using a more complex regex from stack overflow: https://stackoverflow.com/a/45615798
            //previously I used ^[a-zA-Z0-9_-.]+$ but I wanted something more robust for use in Windows
            if (!filename.matches("\\A(?!(?:COM[0-9]|CON|LPT[0-9]|NUL|PRN|AUX|com[0-9]|con|lpt[0-9]|nul|prn|aux)|\\s|[.]{2,})[^\\\\/:*\"?<>|]{1,254}(?<![\\s.])\\z")) {
                System.out.println("""
                         ---------------------------------------------------------------------------------------
                        | Invalid filename! Please try again using only alphanumeric characters and '-' '_' '.' |
                        |   - Your file cannot end in a space or '.'                                            |
                        |   - Your file cannot use any windows reserved words or begin with 2 dots              |
                         ---------------------------------------------------------------------------------------""");

                System.out.print("Enter File Name: ");
                System.out.println("(Enter '0' to return to main menu)");
                filename = kb.nextLine();
                if (filename.equals("0")) {
                    Main.showMenu();
                }
                continue;
            }

            File newFile = new File(filename);

            if (newFile.isFile()) {
                System.out.println(ColourUtil.red("File " + filename + " already exists, use another name."));
                continue;
            }

            filename = addTxtExt(filename);
            break;
        }

        System.out.println("Enter content for the new file: (Press enter to leave blank)");
        System.out.println("(Enter '0' to return to main menu)");
        input = kb.nextLine();
        if (input.equals("0")) {
            Main.showMenu();
        }

        try {
            writeFile(filename, input);
            if (input.isEmpty()) {
                System.out.println(ColourUtil.green("File created successfully - However you will need to populate it before you can use it for Encrypting a file"));
                return true;
            }

            System.out.println(ColourUtil.green("File created successfully, you can now use " + filename));
            return true;
        } catch (Exception e) {
            System.out.println(ColourUtil.red("Failed to create file: " + e.getMessage()));
            return false;
        }
    }

    //Function to read files that allows input of file name or path.
    public static String readFile(String filename) {
        try {
            //ensuring .txt extension is at the end of the filename
            filename = addTxtExt(filename);

            File f = new File(filename);

            StringBuilder fileContent = new StringBuilder();
            Scanner fileReader = new Scanner(f);

            while (fileReader.hasNextLine()) {
                fileContent.append(fileReader.nextLine());
                if (fileReader.hasNextLine()) {
                    fileContent.append("\n");
                }
            }

            //checking to make sure the file has something to read
            if (fileContent.isEmpty()) {
                return null;
            }

            return fileContent.toString();

        } catch (FileNotFoundException e) {
            System.out.println(ColourUtil.red("File not found error: " + e.getMessage()));
            return null;
        }
    }

    //Function to write into files. using this in my createNewFile() function as well to write a new file.
    public static void writeFile(String filename, String text) {
        try {
            FileWriter f = new FileWriter(filename);
            f.write(text);
            f.close();
        } catch (IOException e) {
            System.out.println(ColourUtil.red("File write error: " + e.getMessage()));
        }
    }

    //Function to confirm if the file is empty and warns the user before overwriting it, separate from my writeFile
    //so it can only be used in particular scenarios.
    public static boolean confirmFileEmpty(String filename) {
        String fileContent = readFile(filename);
        if (fileContent != null) {
            while (true) {
                System.out.println("\nChecking if " + filename + " is empty...");
                System.out.println("""
                        \u001B[91mWarning: This file already contains data.\u001B[0m
                        Do you want to overwrite it?
                        1. Yes
                        2. No
                        Choose 1 or 2: (Enter '0' to return to main menu)""");
                String choice = kb.nextLine();
                switch (choice) {
                    case "1":
                    case "y":
                    case "yes":
                        return true;
                    case "2":
                    case "n":
                    case "no":
                        System.out.println(ColourUtil.red("File Write Canceled"));
                        return false;
                    case "0":
                        Main.showMenu();
                    default:
                        System.out.println("Please enter '1' or '2'");
                }
            }
        }
        return true;
    }

    //Function to add the .txt extension to a file in case the user doesn't include it in their input
    public static String addTxtExt(String filename) {
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        return filename;
    }

    //Function to completely validate the file is correct to be encrypted/decrypted
    //returns the content of a file once validated
    public static String getValidFileContent(String prompt) {
        String filename, fileContent;

        while (true) {
            System.out.println(prompt);
            System.out.println("(Enter '0' to return to main menu)");
            filename = kb.nextLine();
            if (filename.equals("0")) {
                Main.showMenu();
            }

            //ensures the file is a valid text file, reads and if there is content within the file to return
            if (isValidTxt(filename)) {
                fileContent = readFile(filename);
                if (fileContent != null) {
                    return fileContent;
                }
            }
            System.out.println(ColourUtil.red("Error: File is empty"));
        }
    }
}