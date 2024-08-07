package model;

import gui.ConsoleGUI;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientFlow implements ProgramFlow, Runnable {

    public static final int ClientIdle = 0;
    public static final int ClientConnected = 1;
    public static final int ClientListening = 2;
    public static final int ClientEnded = 3;

    // initial client state
    int clientState = ClientFlow.ClientIdle;

    // server credentials
    String serverIP;
    String serverPort;

    @Override
    public boolean setup() {
        ConsoleGUI.mainOutNL("Client setup");
        //scanner object
        Scanner scanner1 = new Scanner(System.in);

        ConsoleGUI.mainOutSL("Server IP : ");
//        serverIP = scanner1.next();
        ConsoleGUI.mainOutSL("Server Port : ");
        serverPort = scanner1.next();

        // Socket object instantiation
        try {
            Socket clientSocket = new Socket("localhost", 4000);
            ConsoleGUI.consoleGraphic(String.format("Connected to Server | %s:%s", serverIP, serverPort));

        } catch (UnknownHostException uhe) {
            ConsoleGUI.mainOutNL("Unknown Host Exception");
            uhe.printStackTrace();

        } catch (IOException ioe) {
            ConsoleGUI.mainOutNL("IO Exception");
            ioe.printStackTrace();
        }

        return false;
    }

    @Override
    public void operationLoop() {
        ConsoleGUI.mainOutNL("Client Loop");

    }

    @Override
    public void run() {
        setup();
        operationLoop();
        ConsoleGUI.setOperationMode(ConsoleGUI.MODE_IDLE);
    }
}
