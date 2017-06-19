package business.control;

import business.model.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mz on 12/06/17.
 * <p>
 * Provides a multi thread server for a Chat application.
 * ServerPool can receive several connections and initiates a server for every new client.
 * All connected clients will receive any message sent from others clients.
 */
public class ServerPool implements Runnable {

    private final int INPUT_PORT = 4444;
    private final int OUTPUT_PORT = 4445;

    private ExecutorService executor;
    private ServerSocket inputServerSocket;
    private ServerSocket outputServerSocket;
    private boolean serverOn;
    private Mediator mediator;

    public ServerPool() {
        mediator = new ClientMediator();
        this.executor = Executors.newCachedThreadPool();
        try {
            this.inputServerSocket = new ServerSocket(INPUT_PORT);
            this.outputServerSocket = new ServerSocket(OUTPUT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverOn = true;
    }

    @Override
    public void run() {
        try {
            while (serverOn) {
                System.out.println("Waiting for connections...");
                Socket inputSocket = inputServerSocket.accept();
                Socket outputSocket = outputServerSocket.accept();
                executor.execute(new Server(new Client(inputSocket, outputSocket), mediator));
                System.out.println("Received connection from " + inputSocket.getInetAddress());
            }
        } catch (SocketException e) {
            System.out.println("ServerSocket closed.");
        } catch (IOException e) {
            System.err.println("Pool Server DOWN! This should not happen"); // should not happen
            e.printStackTrace();
        }
    }

    /**
     * Closes server pool and its listening sockets.
     */
    public void close() {
        executor.shutdownNow();
        serverOn = false;
        try {
            if (inputServerSocket != null) {
                inputServerSocket.close();
            }
            if (outputServerSocket != null) {
                outputServerSocket.close();
            }
            mediator.closeAllConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printClientsAddress() {
        System.out.println("Printing address of all connected clients");
        mediator.getClientsAddress().forEachRemaining(System.out::println);
    }
}
