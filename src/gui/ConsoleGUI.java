package gui;

import model.ClientFlow;
import model.ServerFlow;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleGUI {

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


    /** console formatted graphic output */
    public static synchronized void consoleGraphic(String titleMessage) {
        // clearing the screen
        // does NOT work in virtual environments like IDE Runtimes Environments
        try {
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            } else {
                Runtime.getRuntime().exec("clear");

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        // formatted Title Message
        String formattedTitle = String.format("%s%s%s", titleMessage, new String(new char[68 - titleMessage.length()]).replace("\0", " "), "|");
        // gui.ConsoleGUI Header OutPut
        String mainOut = """
                +---------------------------------------------------------------------+
                | 88        88888888  8888  88          88888888  88    88  88888888  |
                | 88        88    88  88 8  88          88        88    88     88     |
                | 88        88888888  88  8 88   ===    88        88888888     88     |
                | 88        88    88  88   888          88        88    88     88     |
                | 88888888  88    88  88    88          88888888  88    88     88     |
                |                                                       version 1.0.0 |
                +---------------------------------------------------------------------+
                | %s
                +---------------------------------------------------------------------+
                """.formatted(formattedTitle);
        System.out.println(mainOut);

    }


    /** Main NEWLINE Output for the program */
    public static synchronized void mainOutNL(String message) {
        System.out.println(message);
    }

    /** Main SAMELINE Output for the program */
    public static synchronized void mainOutSL(String message) {
        System.out.print(message);
    }


    /** choose Operation Mode */
    private static int modeChooserPrompt() {
        String cPhrase = "";
        int mode = 9;

        while (mode == 9) {
            cPhrase = scanner.nextLine();

            if (cPhrase.matches("exit")) {
                mode = ConsoleGUI.MODE_END;

            } else if (Objects.equals(cPhrase.toLowerCase(), "start server")) {
                mode = ConsoleGUI.MODE_SERVER;

            } else if (Objects.equals(cPhrase.toLowerCase(), "start client")) {
                mode = ConsoleGUI.MODE_CLIENT;
            } else {
                mainOutNL("Incorrect Response");
            }

        }

        return mode;
    }


    /** public setter for operation mode control variable */
    public static void setOperationMode(int m) {
        ConsoleGUI.operationMode = m;
    }


    public static void main(String[] args) {

        while (operationMode != ConsoleGUI.MODE_END) {
            consoleGraphic("Welcome to LAN-CHAT! use [START] to enter (SERVER) or (CLIENT) mode");
            setOperationMode(modeChooserPrompt());

            switch (operationMode) {
                case ConsoleGUI.MODE_CLIENT:

                    try {
                        consoleGraphic("ClientFlow Mode");
                        ClientFlow client = new ClientFlow();
                        Thread clientThread = new Thread(client);
                        mainOutNL(Thread.currentThread().toString());
                        clientThread.start();
                        clientThread.join();

                        operationMode = ConsoleGUI.MODE_IDLE;
                        break;

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                case ConsoleGUI.MODE_SERVER:
                    try {
                        consoleGraphic("ServerFlow Mode");
                        ServerFlow server = new ServerFlow();
                        Thread serverThread = new Thread(server);
                        serverThread.start();
                        serverThread.join();


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            }
        }


    }
}
