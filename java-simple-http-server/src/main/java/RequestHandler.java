import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.Header;
import enums.Status;

public class RequestHandler {

    protected static final Path PUBLIC_DIR_PATH = Paths.get("public").toAbsolutePath();
    protected static final String INDEX_FILE_NAME = "index.html";
    protected static final String FORBIDDEN_FILE_NAME = "403.html";
    protected static final String NOTFOUND_FILE_NAME = "404.html";

    private static byte[] EMPTY_BODY = {};
    private static Pattern textFileMimePattern = Pattern.compile("^text/.+|application/(json|xml|(x-)?javascript|ecmascript)");

    public Response handleRequest(Request request) throws IOException {

        if (request == null) {
            return new Response(Status.BAD_REQUEST, "text/html; charset=utf-8",
                    null, "Bad Request".getBytes(StandardCharsets.UTF_8));
        }

        Status status;
        Path path;
        Path pathToTest = Paths.get(PUBLIC_DIR_PATH.toString(), request.getPath()).normalize();

        if (!pathToTest.startsWith(PUBLIC_DIR_PATH)) { // ディレクトリトラバーサル防止
            path = PUBLIC_DIR_PATH.resolve(FORBIDDEN_FILE_NAME);
            status = Status.FORBIDDEN;
        } else if (Files.isRegularFile(pathToTest)) {
            path = pathToTest;
            status = Status.OK;
        } else if (Files.isDirectory(pathToTest) && Files.isRegularFile(pathToTest.resolve(INDEX_FILE_NAME))) {
            path = pathToTest.resolve(INDEX_FILE_NAME);
            status = Status.OK;
        } else {
            path = PUBLIC_DIR_PATH.resolve(NOTFOUND_FILE_NAME);
            status = Status.NOT_FOUND;
        }

        String contentType;
        String mimeFromName;
        String mimeFromContent;
        if ((mimeFromName = URLConnection.guessContentTypeFromName(path.toString())) != null) {
            contentType = mimeFromName;
        } else {
            try (InputStream in = Files.newInputStream(path)) {
                if ((mimeFromContent = URLConnection.guessContentTypeFromStream(in)) != null) {
                    contentType = mimeFromContent;
                } else {
                    contentType = "application/octet-stream";
                }
            }
        }

        Matcher textFileMatcher = textFileMimePattern.matcher(contentType);
        if (textFileMatcher.matches()) {
            // バイト列からエンコーディングを推定する処理を自前で用意するのは激しく面倒なのでUTF-8で決め打ち
            contentType = contentType + ";charset=" + StandardCharsets.UTF_8.name();
        }

        OffsetDateTime lastModified = OffsetDateTime.ofInstant(Instant.ofEpochMilli(path.toFile().lastModified()), ZoneOffset.UTC);
        String ifModifiedSinceHeader = request.getHeader(Header.IF_MODIFIED_SINCE.key);
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        if(ifModifiedSinceHeader == null || lastModified.isBefore(OffsetDateTime.parse(ifModifiedSinceHeader, formatter))) {
            return new Response(status, contentType, lastModified, Files.readAllBytes(path));
        } else {
            return new Response(Status.NOT_MODIFIED, contentType, lastModified, EMPTY_BODY);
        }
    }
}
