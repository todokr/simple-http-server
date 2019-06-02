package application;

public interface Request {
    public enum Method {
        Get,
        Post
    }

    Method getMethod();
}
