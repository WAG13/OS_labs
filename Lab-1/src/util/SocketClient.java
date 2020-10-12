package util;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 1234;

    private DataOutputStream out;
    private DataInputStream in;

    private SocketClient(DataInputStream in, DataOutputStream out) {
        this.out = out;
        this.in = in;
    }

    public double get() throws IOException {
        return in.readDouble();
    }

    public void send(double message) throws IOException {
        out.writeDouble(message);
    }

    public static SocketClient create() throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return new SocketClient(in, out);
    }

}