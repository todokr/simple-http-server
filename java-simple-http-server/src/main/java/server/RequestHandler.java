package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import application.Controller;
import enums.Status;

public class RequestHandler {

    private static Path BAD_REQUEST_HTML_PATH = Paths.get("public/400.html");
    private static Path FORBIDDEN_HTML_PATH = Paths.get("public/403.html");
    private static Path NOT_FOUND_HTML_PATH = Paths.get("public/404.html");
    private static String HTML_MIME = "text/html;charset=utf8";
    private static MimeDetector mimeDetector = new MimeDetector("mimes.properties");


    public Response handleRequest(Request request) throws IOException {

        if (request == null) {
            return new Response(Status.BAD_REQUEST, HTML_MIME, Files.readAllBytes(BAD_REQUEST_HTML_PATH));
        }

        // ここから static

        Path resourcePath = Paths.get("public", request.path).normalize();

        if (!resourcePath.startsWith("public/")) { // ディレクトリトラバーサル
            return new Response(Status.FORBIDDEN, HTML_MIME, Files.readAllBytes(FORBIDDEN_HTML_PATH));
        }

        if (Files.isRegularFile(resourcePath)) {
            String mime = mimeDetector.getMime(resourcePath);
            return new Response(Status.OK, mime, Files.readAllBytes(resourcePath));
        }

        Path indexFilePath = resourcePath.resolve("index.html");
        if (Files.isDirectory(resourcePath) && Files.exists(indexFilePath)) {
            return new Response(Status.OK, HTML_MIME, Files.readAllBytes(indexFilePath));
        }

        // ここまで static

        return new Response(Status.NOT_FOUND, HTML_MIME, Files.readAllBytes(NOT_FOUND_HTML_PATH));
    }

    class StaticContentHandler {

        private Map<String, Class> clazz;

        StaticContentHandler(Map<String, Class> clazz) {
            this.clazz = clazz;
        }
    }

    class DynamicContentHandler {}

}
