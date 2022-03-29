package simplehttpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class SimpleJavaHttpServer {

    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {

        var serverSocket = new ServerSocket(PORT);
        var parser = new RequestParser();
        var handler = new RequestHandler();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutting down HTTP Server...");
                serverSocket.close();
            } catch (IOException e) {
                System.exit(1);
            }
        }));

        System.out.println("HTTP Server Start! Listening at " + PORT + "!");

        while (true) {
            try {
                var socket = serverSocket.accept();
                var worker = new WorkerThread(socket, parser, handler);
                worker.start();
            } catch (IOException e) {
                System.err.println("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
