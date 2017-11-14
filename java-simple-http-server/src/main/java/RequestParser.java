import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    /**
     * InputStreamからHTTPリクエストをパースし、Requestを生成する。
     * InvalidなHTTPリクエストの場合はnullを返す。
     */
    public Request fromInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String requestLine = reader.readLine();

        if (requestLine == null) return null;

        // http://httpwg.org/specs/rfc7230.html#request.line
        String[] requestLineItems = requestLine.split("\\s");
        String method      = requestLineItems[0];
        String targetPath  = requestLineItems[1];
        String httpVersion = requestLineItems[2];

        return new Request(method, targetPath, httpVersion);
    }
}
