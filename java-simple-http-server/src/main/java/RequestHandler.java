import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import enums.Status;

public class RequestHandler {

    private static Path PUBLIC_DIR_PATH = Paths.get("public").toAbsolutePath();
    private static Path BAD_REQUEST_HTML_PATH = PUBLIC_DIR_PATH.resolve("400.html");
    private static Path FORBIDDEN_HTML_PATH = PUBLIC_DIR_PATH.resolve("403.html");
    private static Path NOT_FOUND_HTML_PATH = PUBLIC_DIR_PATH.resolve("404.html");
    private static String INDEX_FILE_NAME = "index.html";
    private static String HTML_MIME = "text/html;charset=utf8";
    private static MimeDetector mimeDetector = new MimeDetector("mimes.properties");

    public Response handleRequest(Request request) throws IOException {

        if (request == null) {
            return new Response(Status.BAD_REQUEST, HTML_MIME, Files.readAllBytes(BAD_REQUEST_HTML_PATH));
        }

        Path resourcePath = Paths.get(PUBLIC_DIR_PATH.toString(), request.path).normalize();

        if (!resourcePath.startsWith(PUBLIC_DIR_PATH)) { // ディレクトリトラバーサル
            return new Response(Status.FORBIDDEN, HTML_MIME, Files.readAllBytes(FORBIDDEN_HTML_PATH));
        }

        if (Files.isRegularFile(resourcePath)) {
            String mime = mimeDetector.getMime(resourcePath);
            return new Response(Status.OK, mime, Files.readAllBytes(PUBLIC_DIR_PATH.resolve(resourcePath)));
        }

        Path indexFilePath = resourcePath.resolve(INDEX_FILE_NAME);
        if (Files.isDirectory(resourcePath) && Files.exists(indexFilePath)) {
            return new Response(Status.OK, HTML_MIME, Files.readAllBytes(indexFilePath));
        }

        return new Response(Status.NOT_FOUND, HTML_MIME, Files.readAllBytes(NOT_FOUND_HTML_PATH));
    }
}
