public class TestFile {
    public static void main(String[] args) {
        FileManager fm = new FileManager();

        String originalFile = fm.readFile("test.txt");

        System.out.println(fm.readFile("test.txt"));

        fm.writeFile("test.txt", "this is an updated test file");

        System.out.println(fm.readFile("test.txt"));

        fm.writeFile("test.txt", originalFile);
    }
}