package business.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by mz on 16/06/17.
 * <p>
 * A client with its connections and streams.
 */
public class Client {

    public final Socket inputSocket;
    public final Socket outputSocket;
    public final DataOutputStream outputStream;
    public final DataInputStream inputStream;

    public Client(Socket inputSocket, Socket outputSocket) {
        this.inputSocket = inputSocket;
        this.outputSocket = outputSocket;

        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            dis = new DataInputStream(inputSocket.getInputStream());
            dos = new DataOutputStream(outputSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Problems opening client connection");
            e.printStackTrace();
        }
        this.inputStream = dis;
        this.outputStream = dos;
    }

    public void closeConnections() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputSocket != null) {
                inputSocket.close();
            }
            if (outputSocket != null) {
                outputSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return inputSocket.getInetAddress().toString();
    }
}
