import utils.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    private String method;
    private String path;
    private String httpVersion;
    private Map<String, String> headers = new HashMap<>();

    private static Pattern headerPattern = Pattern.compile("^(?<key>.+?):\\s*(?<value>.+)$");
    private static Logger logger = new Logger(Request.class.getSimpleName());

    private Request(String method, String path, String httpVersion, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

    public static Request from(InputStream input) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String requestLine = reader.readLine();

        if (requestLine == null) return null;

        logger.log(requestLine);
        String[] requestLineItems = requestLine.split("\\s+");
        String method      = requestLineItems[0];
        String path        = requestLineItems[1];
        String httpVersion = requestLineItems[2];

        Map<String, String> h = new HashMap<>();
        String pair = reader.readLine();
        while (!(pair == null || pair.isEmpty())) {
            Matcher matcher = headerPattern.matcher(pair);
            if(matcher.find()) {
                h.put(matcher.group("key"), matcher.group("value"));
            }
            pair = reader.readLine();
        }

        return new Request(method, path, httpVersion, h);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
