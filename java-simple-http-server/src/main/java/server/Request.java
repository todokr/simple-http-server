package server;

import java.nio.charset.StandardCharsets;

public class Request {

    public final String method;
    public final String path;
    public final String httpVersion;
    private final byte[] body;

    Request(String method, String path, String httpVersion, byte[] body) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.body = body;
    }

    public String getBodyAsText() {
        return new String(this.body, StandardCharsets.UTF_8);
    }

    public byte[] getBodyAsBytes() {
        return this.body;
    }
}
