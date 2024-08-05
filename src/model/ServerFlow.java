package model;

import gui.ConsoleGUI;

public class ServerFlow implements ProgramFlow, Runnable {

    public static final int ServerIdle = 0;
    public static final int ServerBroadcasting = 0;
    public static final int ServerConnected = 0;

    @Override
    public boolean setup() {
        ConsoleGUI.mainOut("ServerFlow Setup");
        return false;
    }

    @Override
    public void operationLoop() {
        ConsoleGUI.mainOut("ServerFlow Loop");

    }

    @Override
    public void run() {
        setup();
        operationLoop();
        ConsoleGUI.setOperationMode(ConsoleGUI.MODE_IDLE);

    }
}
