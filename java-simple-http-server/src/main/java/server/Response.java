package server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import enums.Status;

public class Response {

    public final Status status;
    public final String contentType;
    public final int contentLength;
    public final byte[] body;

    private static DateTimeFormatter rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static String CRLF = "\r\n";

    public Response(Status status, String contentType, byte[] body){
      this.status = status;
      this.contentType = contentType;
      this.contentLength = body.length;
      this.body = body;
    }

    public void writeTo(OutputStream out) throws IOException {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        String response =
                "HTTP/1.1 " + status.statusCode + CRLF +
                "Date: " + rfc1123Formatter.format(now) + CRLF +
                "Server: server.SimpleJavaHttpServer" + CRLF +
                "Content-Type: " + contentType + CRLF +
                "Content-Length: " + String.valueOf(contentLength) + CRLF +
                "Connection: Close" + CRLF +
                CRLF;

        out.write(response.getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }
}
