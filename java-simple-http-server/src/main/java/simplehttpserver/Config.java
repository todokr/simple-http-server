package simplehttpserver;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    public static final Path BAD_REQUEST_HTML_PATH = Paths.get("public/400.html");
    public static final Path FORBIDDEN_HTML_PATH = Paths.get("public/403.html");
    public static final Path NOT_FOUND_HTML_PATH = Paths.get("public/404.html");
    public static final String HTML_MIME = "text/html;charset=utf8";
    public static final String INDEX_FILE = "index.html";
    public static final String PUBLIC_DIR_NAME = "public";
}
