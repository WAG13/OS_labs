package manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class Manager {

    public enum Status {
        SHORT_CIRCUIT, CANCELLED, SUCCESS, UNDEFINED
    }

    private static final double ZERO_VALUE = 0;

    private Thread thread;
    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets;
    private final int N;

    private Manager(ServerSocket serverSocket, int N) {
        this.serverSocket = serverSocket;
        this.sockets = new ArrayList<>();
        this.N = N;
    }

    public void run(double x, BiConsumer<Optional<Double>, Status> onCalculated) throws IOException {

        double[] results = new double[N];
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < N; i++) {
            Socket socket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeDouble(x);

            int finalI = i;
            Runnable runnable = () -> {
                double inputDouble;
                try {
                    inputDouble = in.readDouble();
                    System.out.println("Manager: got " + inputDouble);

                    if (Double.compare(inputDouble, ZERO_VALUE) == 0) {
                        // got zero value, so we already know the result
                        if (counter.get() < N - 1) {
                            onCalculated.accept(Optional.of(ZERO_VALUE), Status.SHORT_CIRCUIT);
                        } else {
                            onCalculated.accept(Optional.of(ZERO_VALUE), Status.SUCCESS);
                        }
                        closeAll();
                        return;
                    } else if (Double.isNaN(inputDouble)) {
                        onCalculated.accept(Optional.empty(), Status.UNDEFINED);
                        closeAll();
                        return;
                    }

                    results[finalI] = inputDouble;
                    if (counter.incrementAndGet() == N) {
                        // last function sent result, so we can start calculation
                        calculateResult(results, onCalculated);
                        closeAll();
                    }

                } catch (IOException e) {
                    onCalculated.accept(Optional.empty(), Status.CANCELLED);
                }
            };
            thread = new Thread(runnable);
            thread.start();
            sockets.add(socket);
        }
    }

    public void closeAll() {
        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void calculateResult(double[] results, BiConsumer<Optional<Double>, Status> onCalculated) {
        double out = 1;
        for (double r : results) {
            out *= r;
            if (r == 0) {
                throw new AssertionError("No zeros should be in results");
            }
        }
        onCalculated.accept(Optional.of(out), Status.SUCCESS);
    }

    public static Manager createOnPort(int port, int N) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        if (N < 2) throw new IllegalArgumentException("N should be >1");
        return new Manager(serverSocket, N);
    }
}
