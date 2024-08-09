package model;

import gui.ConsoleGUI;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientFlow implements ProgramFlow, Runnable {

    public static final int ClientIdle = 0;
    public static final int ClientConnected = 1;
    public static final int ClientListening = 2;
    public static final int ClientEnded = 3;

    // scanner for getting user input
    Scanner scanner = new Scanner(System.in);

    // initial client state
    int clientState;

    // server credentials
    String serverIP;
    String serverPort;

    // Client socket variables
    Socket client;
    DataInputStream clientIn;
    DataOutputStream clientOut;

    public ClientFlow() {
        clientState = ClientIdle;
    }


    @Override
    public boolean setup() {
        ConsoleGUI.mainOutNL("Client setup");

        ConsoleGUI.mainOutSL("Server IP : ");
        serverIP = scanner.nextLine();
        ConsoleGUI.mainOutSL("Server Port : ");
        serverPort = scanner.nextLine();

        // Socket object instantiation
        try {
            client = new Socket("localhost", 4000);
            // Input Output Data Streams
            clientOut = new DataOutputStream(client.getOutputStream());
            clientIn = new DataInputStream(new BufferedInputStream(client.getInputStream()));

            ConsoleGUI.consoleGraphic(String.format("Connected to Server | %s:%s", serverIP, serverPort));

            return true;

        } catch (UnknownHostException uhe) {
            ConsoleGUI.mainOutNL("Unknown Host Exception");
            uhe.printStackTrace();

            return false;

        } catch (IOException ioe) {
            ConsoleGUI.mainOutNL("IO Exception");
            ioe.printStackTrace();

            return false;
        }


    }

    @Override
    public void operationLoop() {
        try {
            String clientOutMsg = "";
            while (clientState != ClientEnded) {
                ConsoleGUI.mainOutSL("client : ");
                clientOutMsg = scanner.nextLine();
                if (clientOutMsg.equals("#")) {
                    clientState = ClientEnded;
                }
                clientOut.writeUTF(clientOutMsg);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        if (setup()) {
            operationLoop();
        }

        try {
            clientIn.close();
            clientOut.close();
            client.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ConsoleGUI.setOperationMode(ConsoleGUI.MODE_IDLE);
    }
}
