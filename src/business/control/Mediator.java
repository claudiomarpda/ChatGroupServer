package business.control;

import business.model.Client;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by mz on 13/06/17.
 *
 * The Mediator among clients and the main server.
 */
public interface Mediator {

    void addClient(Client client);

    void removeClient(Client client);

    void sendMessage(String message);

    void closeAllConnections() throws IOException;

    Iterator<String> getClientsAddress();
}
