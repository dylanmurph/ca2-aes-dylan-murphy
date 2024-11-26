import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    public String readFile(String fileLocation){
        try {
            StringBuilder text = new StringBuilder();
            File f = new File(fileLocation);
            Scanner fileReader = new Scanner(f);

            while (fileReader.hasNextLine()) {
                text.append(fileReader.nextLine());
            }

            fileReader.close();
            return text.toString();

        } catch (FileNotFoundException e) {
            System.out.println("File not found error: " + e.getMessage());
            return "";
        }
    }

    public void writeFile(String filename, String text) {
        try {
            FileWriter f = new FileWriter(filename);
            f.write(text);
            f.close();
        }catch (IOException e) {
            System.out.println("File write error: " + e.getMessage());
        }

    }
}