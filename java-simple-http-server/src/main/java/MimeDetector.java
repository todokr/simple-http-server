import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class MimeDetector {

    private Properties prop;

    public MimeDetector(String configFileName) {
        InputStream in = this.getClass().getResourceAsStream(configFileName);
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            System.err.println("Failed to load mime config");
        }
        this.prop = p;
    }

    /**
     * 与えられたPathの拡張子に対応するMIMEを返す。
     * 該当する拡張子が無い場合はapplication/octet-streamを返す。
     */
    public String getMime(Path path) {
        String fileName = path.getFileName().toString();
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return this.prop.getProperty(ext, "application/octet-stream");
    }
}
