import business.control.ServerPool;

import java.util.Scanner;

/**
 * This is a chat group application.
 * Main class starts the server for accepting clients connection.
 * The admin user can stop the server in a safe way or list all clients' address.
 */
public class Main {

    public static void main(String[] args) {

        final String COMMAND_EXIT = "EXIT";
        final String COMMAND_LIST= "LIST";

        ServerPool serverPool = new ServerPool();
        new Thread(serverPool).start();

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        do {
            System.out.println("Type " + COMMAND_EXIT + " to close the server");
            System.out.println("Type " + COMMAND_LIST + " to list the address of all connected clients");
            System.out.print("> ");
            if(userInput.equals(COMMAND_LIST)) {
                serverPool.printClientsAddress();
            }
            userInput = scanner.next();
        }
        while (!userInput.equals(COMMAND_EXIT));

        serverPool.close();
        System.out.println("ServerPool OFF");
    }
}
