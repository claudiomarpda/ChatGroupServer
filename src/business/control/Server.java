package business.control;

import business.model.Client;

import java.io.IOException;

/**
 * Created by mz on 12/06/17.
 * <p>
 * Handles communication with a single client.
 * It has a thread to receive messages and notify the Mediator
 */
class Server implements Runnable {

    private static final String CHAT_EXIT = "EXIT";
    private Mediator mediator; // sends message to all clients
    private Client client;

    Server(Client client, Mediator mediator) {
        this.client = client;
        this.mediator = mediator;
        mediator.addClient(client);
    }

    @Override
    public void run() {
        String receivedMessage;
        String clientIpAddress = client.inputSocket.getInetAddress().toString();
        try {
            // receives random messages
            do {
                receivedMessage = client.inputStream.readUTF(); // waits until get a message
                System.out.println("Received message: " + receivedMessage);

                // tells mediator that has received a message
                // so it can trigger it to all other clients
                mediator.sendMessage(clientIpAddress + " says: \t " + receivedMessage);
            }
            // loop condition must be changed according to business rules
            while (!receivedMessage.equals(CHAT_EXIT));
        } catch (IOException e) {
            mediator.removeClient(client);
            System.out.println("Connection finished");
        }
    }
}
