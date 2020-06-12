import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import enums.Status;

public record Response(
        Status status,
        String contentType,
        byte[] body
) {

    private static final DateTimeFormatter rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static final String CRLF = "\r\n";

    public int contentLength() {
        return body.length;
    }

    public void writeTo(OutputStream out) throws IOException {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        var response =
                "HTTP/1.1 " + status.statusCode + CRLF +
                "Date: " + rfc1123Formatter.format(now) + CRLF +
                "Server: SimpleJavaHttpServer" + CRLF +
                "Content-Type: " + contentType + CRLF +
                "Content-Length: " + contentLength() + CRLF +
                "Connection: Close" + CRLF +
                CRLF;

        out.write(response.getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }
}
