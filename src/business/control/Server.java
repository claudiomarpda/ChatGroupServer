package business.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by mz on 12/06/17.
 * <p>
 * Handles communication with a single client.
 * It has a thread to receive messages and notify the Mediator
 */
public class Server implements Runnable {

    private static final String CHAT_EXIT = "EXIT";

    private DataInputStream dis; // to receive data
    private DataOutputStream dos; // to send data
    private Mediator mediator; // sends message to all clients
    private String clientIpAddress;

    /**
     * Instantiated by ServerPool from a new client.
     *
     * @param inputSocket  provides input stream
     * @param outputSocket provides output stream
     * @param mediator     notifies all clients
     * @throws IOException when streams go wrong
     */
    public Server(Socket inputSocket, Socket outputSocket, Mediator mediator) throws IOException {
        dis = new DataInputStream(inputSocket.getInputStream());
        dos = new DataOutputStream(outputSocket.getOutputStream());
        this.mediator = mediator;
        // stream from which all clients can be notified at once
        this.mediator.addOutputStream(dos);
        clientIpAddress = inputSocket.getInetAddress().toString();
    }

    @Override
    public void run() {
        String receivedMessage;
        try {
            // receives random messages
            do {
                receivedMessage = dis.readUTF(); // waits until get a message
                System.out.println("Received message: " + receivedMessage);

                // tells mediator that has received a message
                // so it can trigger it to all other clients
                mediator.sendMessage(clientIpAddress + " says: \t " + receivedMessage);
            }
            // loop condition must be changed according to business rules
            while (!receivedMessage.equals(CHAT_EXIT));
        } catch (IOException e) {
            mediator.removeOutputStream(dos);
            System.out.println("Connection finished");
        }
    }
}
