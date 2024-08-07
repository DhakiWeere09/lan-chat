package model;

import gui.ConsoleGUI;

import java.io.Console;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;

public class ServerFlow implements ProgramFlow, Runnable {

    public static final int ServerIdle = 0;
    public static final int ServerBroadcasting = 1;
    public static final int ServerConnected = 2;
    public static final int ServerEnded = 3;

    //Server config variables
    int serverState;
    int serverPort;
    int serverTimeOut;
    int clientCount = 0;
    LinkedList<Socket> clients = new LinkedList<>();

    // Server Socket
    ServerSocket serverSocket;

    public ServerFlow() {
        try {
            serverState = ServerIdle;
            serverTimeOut = 5000;
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
            clients.add(serverSocket.accept());
            ConsoleGUI.mainOutNL(clients.toString());

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
            ioe.printStackTrace();

            return false;
        }

    }

    @Override
    public void operationLoop() {
        ConsoleGUI.mainOutNL("ServerFlow Loop");

    }

    @Override
    public void run() {
        try {
            if (setup()) {
                operationLoop();
            }
            serverSocket.close();
            ConsoleGUI.setOperationMode(ConsoleGUI.MODE_IDLE);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
}
