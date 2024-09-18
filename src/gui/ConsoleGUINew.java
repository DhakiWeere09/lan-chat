package gui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleGUINew {

    // Operation Modes
    public final static int MODE_IDLE = 1;
    private final static int MODE_SERVER = 2;
    private final static int MODE_CLIENT = 3;
    private final static int MODE_END = 0;

    // scanner
    static Scanner scanner = new Scanner(System.in);

    // Getting the Running os
    static final String os = System.getProperty("os.name");

    // operationMode variable */
    static int operationMode = ConsoleGUI.MODE_IDLE;

    // function variables
    static LinkedList<String> outputs = new LinkedList<>();

    /** Main Console out New Line */
    private void consoleOutNL(String msg){
        System.out.println(msg);
    }

    /** Main Console out Same Line  */
    private void consoleOutSL(String msg){
        System.out.print(msg);
    }

    /** Logging a new output in queue */
    public void outputQueue(String senderName, String msg, boolean isSameline){
        String.format("%s:%s:%d", senderName, msg, (isSameline ? 1 : 0));
    }






    public static void main(String[] args) {
        outputs.add("hi");

    }
}
