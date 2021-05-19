package simplehttpserver;

import simplehttpserver.enums.Status;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Response {
    final Status status;
    final String contentType;
    final byte[] body;

    private static final DateTimeFormatter rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static final String CRLF = "\r\n";

    public Response(Status status, String contentType, byte[] body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public byte[] toBytes() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        var header =  "HTTP/1.1 " + status.statusCode + CRLF +
                "Date: " + rfc1123Formatter.format(now) + CRLF +
                "Server: SimpleJavaHttpServer" + CRLF +
                "Content-Type: " + contentType + CRLF +
                "Content-Length: " + body.length + CRLF +
                "Connection: Close" + CRLF +
                CRLF;
        var headerBytes = header.getBytes(StandardCharsets.UTF_8);
        var buff = ByteBuffer.allocate(headerBytes.length + body.length);
        buff.put(headerBytes);
        buff.put(body);
        return buff.array();
    }
}
