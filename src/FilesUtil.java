//This is where I am handling all the file management, it handles the read, write and creation of files as well
//as the validation of user input for files and file searches.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FilesUtil {
    private static final Scanner kb = new Scanner(System.in);

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

    //Here the user can choose to create a new file or return to re-enter the filename
    public static boolean fileNotFound(String filename) {
        String input;

        while (true) {
            System.out.println("""
                    Would you like to create it now?
                    1. Yes
                    2. No""");
            input = kb.nextLine();

            switch (input) {
                case "1":
                    return createNewFile(filename);
                case "2":
                    System.out.println("Please try again and ensure your filename is correct.");
                    return false;
                default:
                    System.out.println("Please enter '1' or '2'");
            }
        }
    }

    //Function to read files with an input that allows the user to enter file name or path.
    public static String readFile(String filename) {
        try {
            //ensuring .txt extension is at the end of the filename
            filename = addTxtExt(filename);

            File f = new File(filename);

            StringBuilder fileContent = new StringBuilder();
            Scanner fileReader = new Scanner(f);

            while (fileReader.hasNextLine()) {
                fileContent.append(fileReader.nextLine());
            }

            //checking to make sure the file has something to read
            if (fileContent.isEmpty()) {
                System.out.println("Error: File is empty");
                return "";
            }

            return fileContent.toString();

        } catch (FileNotFoundException e) {
            System.out.println("File not found error: " + e.getMessage());
            return "";
        }
    }

    //Function to write into files. using this in my createNewFile() function as well to write a new file.
    public static void writeFile(String filename, String text) {
        try {
            FileWriter f = new FileWriter(filename);
            f.write(text);
            f.close();
        } catch (IOException e) {
            System.out.println("File write error: " + e.getMessage());
        }
    }

    //Function to create new files
    public static boolean createNewFile(String filename) {
        String input;

        System.out.println(" --- New File Creation --- ");
        while (true) {
            if (!filename.matches("\\A(?!(?:COM[0-9]|CON|LPT[0-9]|NUL|PRN|AUX|com[0-9]|con|lpt[0-9]|nul|prn|aux)|\\s|[.]{2,})[^\\\\/:*\"?<>|]{1,254}(?<![\\s.])\\z")) {
                System.out.println("""
                     ---------------------------------------------------------------------------------------
                    | Invalid filename! Please try again using only alphanumeric characters and '-' '_' '.' |
                    |   - Your file cannot end in a space or '.'                                            |
                    |   - Your file cannot use any windows reserved words or begin with 2 dots              |
                     ---------------------------------------------------------------------------------------""");

                System.out.println("Enter File Name: ");
                filename = kb.nextLine();
                continue;
            }

            File newFile = new File(filename);

            if (newFile.isFile()) {
                System.out.println("File " + filename + " already exists, use another name.");
                continue;
            }

            filename = addTxtExt(filename);
            break;
        }

        System.out.println("Enter content for the new file: ");
        input = kb.nextLine();

        try {
            writeFile(filename, input);
            if (input.isEmpty()) {
                System.out.println("File created successfully - However you will need to populate it before you can use it");
                return true;
            }

            System.out.println("File created successfully, you can now use " + filename);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to create file: " + e.getMessage());
            return false;
        }
    }

    //Function to add the .txt extension to a file in case the user doesn't include it in their input
    public static String addTxtExt(String filename) {
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        return filename;
    }
}