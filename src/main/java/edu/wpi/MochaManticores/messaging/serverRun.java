package edu.wpi.MochaManticores.messaging;

public class serverRun {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    public static void main(String[] args) {
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "| ======== STARTING MESSAGING SERVER ======== |");
        messageServer server = new messageServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
