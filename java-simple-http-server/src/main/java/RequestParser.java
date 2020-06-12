import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class RequestParser {

    private static final Pattern requestLinePattern =
            Pattern.compile("(?<method>.*) (?<path>.*?) (?<version>.*?)");
    private static final String publicDirName = "public";

    /**
     * InputStreamからHTTPリクエストをパースし、Requestを生成する。
     * InvalidなHTTPリクエストの場合はnullを返す。
     */
    public RequestContext fromInputStream(InputStream in) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(in));
        var requestLine = reader.readLine();

        if (requestLine == null) return null;

        var matcher = requestLinePattern.matcher(requestLine);

        if (!matcher.find()) return null;

        var method      = matcher.group("method");
        var targetPath  = matcher.group("path");
        var httpVersion = matcher.group("version");

        return new RequestContext(method, targetPath, httpVersion, publicDirName);
    }
}
