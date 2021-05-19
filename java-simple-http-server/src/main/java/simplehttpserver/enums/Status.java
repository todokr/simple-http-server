package simplehttpserver.enums;

public enum Status {
    OK("200 OK"),
    BAD_REQUEST("400 BadRequest"),
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 NotFound"),
    INTERNAL_SERVER_ERROR("500 InternalServerError");

    public final String statusCode;

    Status(String statusCode) {
        this.statusCode = statusCode;
    }
}
