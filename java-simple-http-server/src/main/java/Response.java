import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import enums.Header;
import enums.Status;

public class Response {

    private Status status;
    private String contentType;
    private OffsetDateTime lastModified;
    private int contentLength;
    private byte[] body;

    public Response(Status status, String contentType, OffsetDateTime lastModified, byte[] body){
      this.status = status;
      this.contentType = contentType;
      this.lastModified = lastModified;
      this.contentLength = body.length;
      this.body = body;
    }

    public void writeTo(OutputStream out) throws IOException {

        String CRLF = "\r\n";
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(status.statusCode).append(CRLF)
          .append(Header.CONTENT_TYPE.withValue(contentType)).append(CRLF)
          .append(Header.CONTENT_LENGTH.withValue(String.valueOf(contentLength))).append(CRLF)
          .append(Header.LAST_MODIFIED.withValue(formatter.format(lastModified))).append(CRLF)
          .append(Header.SERVER.withValue("SimpleJavaHTTPServer")).append(CRLF)
          .append(Header.CONNECTION.withValue("Close")).append(CRLF)
          .append(CRLF);

        out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }
}
