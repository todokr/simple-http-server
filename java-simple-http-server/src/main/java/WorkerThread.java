import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerThread extends Thread {

    private final Socket socket;
    private final RequestParser parser;
    private final RequestHandler handler;

    public WorkerThread(Socket socket, RequestParser parser, RequestHandler handler) {
        this.socket = socket;
        this.parser = parser;
        this.handler = handler;
    }

    public void run() {
        try (Socket s = socket;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream()) {
            var request = parser.fromInputStream(in);
            var response = handler.handleRequest(request);
            out.write(response.toBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
