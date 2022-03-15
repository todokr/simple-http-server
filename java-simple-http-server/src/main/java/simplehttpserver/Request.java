package simplehttpserver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static simplehttpserver.Config.*;

/**
 * HTTPサーバーへのリクエスト
 */
public class Request {
    final String method;
    final String path;
    final String httpVersion;
    final Path normalizedPath;

    public Request(String method, String path, String httpVersion) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.normalizedPath = Paths.get(PUBLIC_DIR_NAME, path).normalize();
    }

    public boolean isDirectoryTraversal() {
        return !normalizedPath.startsWith(PUBLIC_DIR_NAME + "/");
    }

    public boolean isRequestForExistFile() {
        return Files.isRegularFile(normalizedPath);
    }
}
