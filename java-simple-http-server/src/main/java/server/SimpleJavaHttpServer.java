package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleJavaHttpServer {

    private static int PORT = 8080;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        RequestParser parser = new RequestParser();
        RequestHandler handler = new RequestHandler(); // こいつをWebApplicationに書き換え

        System.out.println("HTTP Server Start! Listening at " + PORT + "!");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread worker = new WorkerThread(socket, parser, handler);
                worker.start();
            } catch (IOException e) {
                System.err.println("Failed to dispatch: " + e.getMessage());
            }
        }
    }
}
