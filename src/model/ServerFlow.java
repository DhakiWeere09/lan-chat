package model;

import gui.ConsoleGUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerFlow implements ProgramFlow, Runnable {

    public static final int ServerIdle = 0;
    public static final int ServerBroadcasting = 1;
    public static final int ServerConnected = 2;
    public static final int ServerEnded = 3;

    // Scanner Object
    Scanner scanner = new Scanner(System.in);

    // Server config variables
    int serverState;
    int serverPort;
    int serverTimeOut;
    int clientCount = 0;
    LinkedList<Socket> clients = new LinkedList<>();

    // Server Socket
    ServerSocket serverSocket;
    DataInputStream serverIn;
    DataOutputStream serverOut;

    public ServerFlow() {
        try {
            serverState = ServerIdle;
            serverTimeOut = 20000;
            serverPort = 4000;

            // server socket creation
            serverSocket = new ServerSocket(serverPort);
            serverSocket.setSoTimeout(serverTimeOut);


        } catch (IOException e) {
            ConsoleGUI.mainOutNL("IO Exception");
            e.printStackTrace();
        }
    }


    @Override
    public boolean setup() {
        ConsoleGUI.mainOutNL("ServerFlow Setup");

        try {
            // accepting connection and adding the accepted SOCKET object to clients LinkedList
            clients.add(serverSocket.accept());
            // changing server state
            this.serverState = ServerConnected;
            // setting Input Output Streams
            serverIn = new DataInputStream(new BufferedInputStream(clients.getFirst().getInputStream()));
            serverOut = new DataOutputStream(new BufferedOutputStream(clients.getFirst().getOutputStream()));


            ConsoleGUI.consoleGraphic(String.format(
                    "%d Clients Connected | Server : %s:%s",
                    clients.size(),
                    serverSocket.getInetAddress().toString(),
                    serverSocket.getLocalPort()
            ));

            return true;

        } catch (SocketTimeoutException timeoutE) {
            ConsoleGUI.mainOutNL("Server Connection Timed Out");

            return false;

        } catch (IOException ioe) {
            ConsoleGUI.mainOutNL(ioe.getMessage());

            return false;
        }

    }

    @Override
    public void operationLoop() {
        try {
            String serverInMsg = "";
            String serverOutMsg = "";

            while (serverState != ServerEnded) {
                serverInMsg = serverIn.readUTF();

                if (serverInMsg.equals("#")) {
                    serverState = ServerEnded;
                }else{
                    ConsoleGUI.mainOutNL("Client : " + serverInMsg);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        try {
            if (setup()) {
                operationLoop();
            }

            serverState = ServerEnded;

            serverSocket.close();
            serverIn.close();
            serverOut.close();
            ConsoleGUI.setOperationMode(ConsoleGUI.MODE_IDLE);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
}
