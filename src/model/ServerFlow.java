package model;

import gui.Main;

public class ServerFlow implements ProgramFlow, Runnable {

    public static final int ServerIdle = 0;
    public static final int ServerBroadcasting = 0;
    public static final int ServerConnected = 0;

    @Override
    public boolean setup() {
        Main.mainOut("ServerFlow Setup");
        return false;
    }

    @Override
    public void operationLoop() {
        Main.mainOut("ServerFlow Loop");

    }

    @Override
    public void run() {
        setup();
        operationLoop();
        Main.setOperationMode(Main.MODE_IDLE);

    }
}
