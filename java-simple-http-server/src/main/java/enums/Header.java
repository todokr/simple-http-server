package enums;

public enum Header {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LAST_MODIFIED("Last-Modified"),
    CONNECTION("Connection"),
    SERVER("Server"),
    IF_MODIFIED_SINCE("If-Modified-Since");

    public final String key;
    Header(String key) {
        this.key = key;
    }

    public String withValue(String value) {
        return this.key + ": " + value;
    }
}
