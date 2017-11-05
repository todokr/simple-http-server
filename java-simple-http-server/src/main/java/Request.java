public class Request {

    public final String method;
    public final String path;
    public final String httpVersion;

    Request(String method, String path, String httpVersion) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
    }
}
