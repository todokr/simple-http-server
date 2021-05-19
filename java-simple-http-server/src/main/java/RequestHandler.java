import enums.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestHandler {

    private static final Path BAD_REQUEST_HTML_PATH = Paths.get("public/400.html");
    private static final Path FORBIDDEN_HTML_PATH = Paths.get("public/403.html");
    private static final Path NOT_FOUND_HTML_PATH = Paths.get("public/404.html");
    private static final String HTML_MIME = "text/html;charset=utf8";
    private static final String INDEX_FILE = "index.html";
    private static final String PUBLIC_DIR_NAME = "public";

    private static final MimeDetector mimeDetector = new MimeDetector("mimes.properties");

    public Response handleRequest(Request request) throws IOException {

        if (request == null) {
            return new Response(Status.BAD_REQUEST, HTML_MIME, Files.readAllBytes(BAD_REQUEST_HTML_PATH));
        }

        var normalizedRequestPath = normalizePath(request.path);

        if (isDirectoryTraversal(normalizedRequestPath)) {
            return new Response(Status.FORBIDDEN, HTML_MIME, Files.readAllBytes(FORBIDDEN_HTML_PATH));
        }

        if (isRequestForFile(normalizedRequestPath)) {
            var mime = mimeDetector.getMime(request.path);
            return new Response(Status.OK, mime, Files.readAllBytes(normalizedRequestPath));
        }

        var indexFilePath = indexFilePath(normalizedRequestPath);
        if (Files.exists(indexFilePath)) {
            return new Response(Status.OK, HTML_MIME, Files.readAllBytes(indexFilePath));
        }

        return new Response(Status.NOT_FOUND, HTML_MIME, Files.readAllBytes(NOT_FOUND_HTML_PATH));
    }


    private Path normalizePath(String requestPath) {
        return Paths.get(PUBLIC_DIR_NAME, requestPath).normalize();
    }

    private boolean isDirectoryTraversal(Path normalizedRequestPath) {
        return !normalizedRequestPath.startsWith(PUBLIC_DIR_NAME + "/");
    }

    private boolean isRequestForFile(Path normalizedRequestPath) {
        return Files.isRegularFile(normalizedRequestPath);
    }

    public Path indexFilePath(Path normalizedRequestPath) {
        return normalizedRequestPath.resolve(INDEX_FILE);
    }
}
