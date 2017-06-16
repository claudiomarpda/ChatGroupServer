package business.control;

import business.model.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mz on 13/06/17.
 * <p>
 * Mediator has a list of clients so it can use their connections and streams.
 * It can send message to all clients, when notified.
 */
public class ClientMediator implements Mediator {

    private List<Client> clientList;

    public ClientMediator() {
        clientList = new ArrayList<>();
    }

    /**
     * Sends a message through every stream.
     *
     * @param message from Server and its client.
     */
    @Override
    public void sendMessage(String message) {
        System.out.println("sending for " + clientList.size() + " client(s)");
        for (Client c : clientList) {
            try {
                c.outputStream.writeUTF(message);
            } catch (IOException e) {
                // in case something is wrong with the stream
                // removes it from the list
                c.closeConnections();
                clientList.remove(c);
                System.out.println("Client removed");
            }
        }
    }

    @Override
    public void closeAllConnections() throws IOException {
        /*
        Avoids ConcurrentModificationException, even though only one thread is calling it.
        Synchronizing solves the problem, but it has low performance.
         */
        synchronized (this) {
            for (Client c : clientList) {
                c.closeConnections();
            }
        }
    }

    @Override
    public void addClient(Client client) {
        clientList.add(client);
    }

    @Override
    public void removeClient(Client client) {
        if (clientList != null) {
            clientList.remove(client);
        }
    }
}
