package business.control;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mz on 13/06/17.
 * <p>
 * Mediator han an output stream to every client so it can send data to all of them.
 */
public class ClientMediator implements Mediator {

    private List<DataOutputStream> streamList;

    public ClientMediator() {
        streamList = new ArrayList<>();
    }

    @Override
    public void addOutputStream(DataOutputStream stream) {
        streamList.add(stream);
    }

    /**
     * Sends a message through every stream.
     * @param message received from Server.
     */
    @Override
    public void sendMessage(String message) {
        System.out.println("sending for " + streamList.size() + " client(s)");
        for (DataOutputStream o : streamList) {
            try {
                o.writeUTF(message);
            } catch (IOException e) {
                // in case something is wrong with the stream
                // removes it from the list
                streamList.remove(o);
                System.out.println("Stream removed");
            }
        }
    }

    @Override
    public void closeAllStreams() throws IOException {
        for (DataOutputStream o : streamList) {
            o.close();
        }
    }

    @Override
    public void removeOutputStream(DataOutputStream stream) {
        if (streamList != null) {
            streamList.remove(stream);
        }
    }
}
