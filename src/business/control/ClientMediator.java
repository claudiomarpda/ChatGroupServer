package business.control;

import business.model.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mz on 13/06/17.
 * <p>
 * Mediator has a collection of clients so it can use their connections and streams.
 * It can send message to all clients, when notified.
 */
class ClientMediator implements Mediator {

    private List<Client> clientList;

    public ClientMediator() {
        clientList = new ArrayList<>();
    }

    /**
     * Sends a message through every client stream.
     *
     * @param message from Server and its client.
     */
    @Override
    public void sendMessage(String message) {
        for (Client c : clientList) {
            try {
                c.outputStream.writeUTF(message);
            } catch (IOException e) {
                // in case something is wrong with the stream
                // removes it from the list
                c.closeConnections();
            }
        }
    }

    @Override
    public void closeAllConnections() throws IOException {
        for (Client c : clientList) {
            c.closeConnections();
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

    @Override
    public Iterator<String> getClientsAddress() {
        return clientList.stream().map(Client::getAddress).iterator();
    }
}
