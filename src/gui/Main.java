package gui;

import model.ClientFlow;
import model.ServerFlow;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    // Operation Modes
    public final static int MODE_IDLE = 1;
    private final static int MODE_SERVER = 2;
    private final static int MODE_CLIENT = 3;
    private final static int MODE_END = 0;

    // scanner Object
    static Scanner scanner = new Scanner(System.in);

    // Getting the Running os
    static final String os = System.getProperty("os.name");

    // operationMode variable
    static int operationMode = Main.MODE_IDLE;

    // console formatted graphic output
    private static void consoleGraphic(String titleMessage) {
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
        // gui.Main Header OutPut
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

    public static synchronized void mainOut(String message) {
        System.out.println(Thread.currentThread().getName() + " : " + message);
    }

    // choose Operation Mode
    private static int modeChooserPrompt() {
        String cPhrase = "";
        int mode = 9;

        while (mode == 9) {
            cPhrase = scanner.nextLine();

            if (cPhrase.matches("exit")) {
                mode = Main.MODE_END;

            } else if (Objects.equals(cPhrase.toLowerCase(), "start server")) {
                mode = Main.MODE_SERVER;

            } else if (Objects.equals(cPhrase.toLowerCase(), "start client")) {
                mode = Main.MODE_CLIENT;
            } else {
                mainOut("Incorrect Response");
            }

        }

        return mode;
    }

    /**
     * public setter for operation mode control variable
     */
    public static void setOperationMode(int m) {
        Main.operationMode = m;
    }


    public static void main(String[] args) {

        while (operationMode != Main.MODE_END) {
            consoleGraphic("Welcome to LAN-CHAT! use [START] to enter (SERVER) or (CLIENT) mode");
            setOperationMode(modeChooserPrompt());

            switch (operationMode) {
                case Main.MODE_CLIENT:

                    try {
                        consoleGraphic("ClientFlow Mode");
                        ClientFlow client = new ClientFlow();
                        Thread clientThread = new Thread(client);
                        mainOut(Thread.currentThread().toString());
                        clientThread.start();
                        clientThread.join();
                        operationMode = Main.MODE_IDLE;
                        break;

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                case Main.MODE_SERVER:
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


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}