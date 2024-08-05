package model;

import gui.Main;

public class ClientFlow implements ProgramFlow, Runnable{

    public static final int ClientIdle = 0;
    public static final int ClientListening = 1;
    public static final int ClientEnded = 2;

    // initial client state
    int clientState = ClientFlow.ClientIdle;

    @Override
    public boolean setup() {
        Main.mainOut("ClientFlow ServerFlow");
        return false;
    }

    @Override
    public void operationLoop() {
        Main.mainOut("ClientFlow Loop");
    }

    @Override
    public void run() {
        setup();
        operationLoop();
        Main.setOperationMode(Main.MODE_IDLE);
    }
}
