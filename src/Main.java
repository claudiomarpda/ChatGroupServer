import business.control.ServerPool;

import java.util.Scanner;

/**
 * Runs the ServerPool and lets user turn server off.
 * This is a chat group application.
 * All connected clients will receive any possible sent message from others.
 */
public class Main {

    public static void main(String[] args) {

        final String SERVER_EXIT = "EXIT";

        ServerPool serverPool = new ServerPool();
        Thread serverThread = new Thread(serverPool);
        serverThread.start();

        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            System.out.println("Type " + SERVER_EXIT + " to turn server off > ");
            userInput = scanner.next();
        }
        while (!userInput.equals(SERVER_EXIT));

        serverPool.turnOff();
        System.out.println("ServerPool OFF");
    }
}
