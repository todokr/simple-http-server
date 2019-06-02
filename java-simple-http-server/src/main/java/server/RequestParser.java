package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    public static Pattern requestLinePattern = Pattern.compile("(?<method>.*) (?<path>.*?) (?<version>.*?)");

    /**
     * InputStreamからHTTPリクエストをパースし、Requestを生成する。
     * InvalidなHTTPリクエストの場合はnullを返す。
     */
    public Request fromInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String requestLine = reader.readLine();

        if (requestLine == null) return null;

        Matcher matcher = requestLinePattern.matcher(requestLine);

        if (!matcher.find()) return null;

        String method      = matcher.group("method");
        String targetPath  = matcher.group("path");
        String httpVersion = matcher.group("version");

        return new Request(method, targetPath, httpVersion);
    }
}
