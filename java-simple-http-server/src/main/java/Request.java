/**
 * HTTPサーバーへのリクエスト
 */
public class Request {
    final String method;
    final String path;
    final String httpVersion;

    public Request(String method, String path, String httpVersion) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
    }
}
