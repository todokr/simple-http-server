package simplehttpserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import simplehttpserver.enums.Status;
import static simplehttpserver.Config.*;

public class RequestHandler {

    private static final MimeDetector mimeDetector = new MimeDetector("mimes.properties");

    public Response handleRequest(Request request) throws IOException {

        return switch (request) {
            case null -> new Response(Status.BAD_REQUEST, HTML_MIME, Files.readAllBytes(BAD_REQUEST_HTML_PATH));
            case Request r && r.isDirectoryTraversal() -> new Response(Status.FORBIDDEN, HTML_MIME, Files.readAllBytes(FORBIDDEN_HTML_PATH));
            case Request r && r.isRequestForExistFile() -> {
                var mime = mimeDetector.fromFileName(request.path);
                yield  new Response(Status.OK, mime, Files.readAllBytes(r.normalizedPath));
            }
            case Request r && hasIndexFile(r.normalizedPath) -> new Response(Status.OK, HTML_MIME, Files.readAllBytes(indexFilePath(r.normalizedPath)));
            default -> new Response(Status.NOT_FOUND, HTML_MIME, Files.readAllBytes(NOT_FOUND_HTML_PATH));
        };
    }

    private boolean hasIndexFile(Path normalizedRequestPath) {
        return Files.exists(indexFilePath(normalizedRequestPath));
    }

    private Path indexFilePath(Path normalizedRequestPath) {
        return normalizedRequestPath.resolve(INDEX_FILE);
    }
}
