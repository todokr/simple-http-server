import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import enums.Status;

public class RequestHandler {

    private static final Path BAD_REQUEST_HTML_PATH = Paths.get("public/400.html");
    private static final Path FORBIDDEN_HTML_PATH = Paths.get("public/403.html");
    private static final Path NOT_FOUND_HTML_PATH = Paths.get("public/404.html");
    private static final String HTML_MIME = "text/html;charset=utf8";
    private static final MimeDetector mimeDetector = new MimeDetector("mimes.properties");

    public Response handleRequest(RequestContext requestCtx) throws IOException {

        if (requestCtx == null) {
            return new Response(Status.BAD_REQUEST, HTML_MIME, Files.readAllBytes(BAD_REQUEST_HTML_PATH));
        }

        if (requestCtx.isDirectoryTraversal()) {
            return new Response(Status.FORBIDDEN, HTML_MIME, Files.readAllBytes(FORBIDDEN_HTML_PATH));
        }

        if (requestCtx.isRequestForFile()) {
            var mime = mimeDetector.getMime(requestCtx.path());
            return new Response(Status.OK, mime, Files.readAllBytes(requestCtx.resourcePath()));
        }

        if (requestCtx.hasIndexFile()) {
            return new Response(Status.OK, HTML_MIME, Files.readAllBytes(requestCtx.indexFilePath()));
        }

        return new Response(Status.NOT_FOUND, HTML_MIME, Files.readAllBytes(NOT_FOUND_HTML_PATH));
    }
}
