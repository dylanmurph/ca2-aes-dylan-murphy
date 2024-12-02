public class ColourUtil {
    public static final String RED    = "\u001B[91m";
    public static final String GREEN  = "\u001B[92m";
    public static final String RESET = "\u001B[0m";

    public static String red(String output){
        return RED + output + RESET;
    }
    public static String green(String output){
        return GREEN + output + RESET;
    }
}
