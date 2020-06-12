import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * HTTPサーバーへのリクエスト
 */
public record RequestContext(
        String method,
        String path,
        String httpVersion,
        String publicDirName
) {

    /**
     * 公開ディレクトリを親として解決した、リクエスト対象コンテンツへのパス。
     */
    public Path resourcePath() {
        return Paths.get(publicDirName, path).normalize();
    }

    /**
     * リクエストのパスがHTTPサーバーが公開しているディレクトリ外のコンテンツを対象にしているかを検査する。
     * @return 公開ディレクトリ外へのリクエストの場合、true
     */
    public boolean isDirectoryTraversal() {
        return !resourcePath().startsWith(publicDirName + "/");
    }

    public boolean isRequestForFile() {
        return Files.isRegularFile(resourcePath());
    }

    public Path indexFilePath() {
        return resourcePath().resolve("index.html");
    }

    public boolean hasIndexFile() {
        return Files.exists(indexFilePath());
    }
}
