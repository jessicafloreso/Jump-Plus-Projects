package utilities;

public class ColorsUtility {
	
	// ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void reset() {
    	System.out.println(RESET);
    }
    public static void resetInLine() {
    	System.out.print(RESET);
    }
    // Utility methods to print colored text
    public static void printRed(String text) {
        System.out.println(RED + text + RESET);
    }

    public static void printGreen(String text) {
        System.out.println(GREEN + text + RESET);
    }

    public static void printYellow(String text) {
        System.out.println(YELLOW + text + RESET);
    }

    public static void printBlue(String text) {
        System.out.println(BLUE + text + RESET);
    }
    
    public static void startCyan() {
        System.out.println(CYAN);
    }
    
    public static void startYellow() {
        System.out.println(YELLOW);
    }
    public static void startGreen() {
        System.out.println(GREEN);
    }
    public static void startPurple() {
        System.out.println(PURPLE);
    }
    public static void startRed() {
        System.out.println(RED);
    }
    public static void startBlue() {
        System.out.println(BLUE);
    }
    
}
