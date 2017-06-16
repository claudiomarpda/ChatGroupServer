package business.control;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by mz on 13/06/17.
 */
public interface Mediator {

    void addOutputStream(DataOutputStream stream);
    void removeOutputStream(DataOutputStream stream);
    void sendMessage(String message);
    void closeAllStreams() throws IOException;
}
