package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerThread extends Thread {

    private Socket socket;
    private RequestParser parser;
    private RequestHandler handler;

    public WorkerThread(Socket socket, RequestParser parser, RequestHandler handler) {
        this.socket = socket;
        this.parser = parser;
        this.handler = handler;
    }

    public void run() {
        try (Socket s = socket;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream()) {
            Request request = parser.fromInputStream(in);
            Response response = handler.handleRequest(request);
            response.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
