package model;

import gui.ConsoleGUI;

public class ClientFlow implements ProgramFlow, Runnable{

    public static final int ClientIdle = 0;
    public static final int ClientListening = 1;
    public static final int ClientEnded = 2;

    // initial client state
    int clientState = ClientFlow.ClientIdle;

    @Override
    public boolean setup() {
        ConsoleGUI.mainOut("ClientFlow ServerFlow");
        return false;
    }

    @Override
    public void operationLoop() {
        ConsoleGUI.mainOut("ClientFlow Loop");
    }

    @Override
    public void run() {
        setup();
        operationLoop();
        ConsoleGUI.setOperationMode(ConsoleGUI.MODE_IDLE);
    }
}
