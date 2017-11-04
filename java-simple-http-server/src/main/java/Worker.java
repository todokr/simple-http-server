import java.io.IOException;
import java.net.Socket;

import utils.Logger;

public class Worker extends Thread {

    private static Logger logger = new Logger(Worker.class.getSimpleName());
    private Socket socket;
    private RequestHandler requestHandler = new RequestHandler();

    public Worker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (Socket s = socket) {
            Request request = Request.from(s.getInputStream());
            Response response = requestHandler.handleRequest(request);
            response.writeTo(s.getOutputStream());
        } catch (IOException e) {
            logger.error("Failed to process: " + e.getMessage());
        }
    }
}
