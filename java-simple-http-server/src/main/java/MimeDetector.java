import java.io.IOException;
import java.util.Properties;

public class MimeDetector {

    private final Properties prop;

    public MimeDetector(String configFileName) {
        var in = this.getClass().getResourceAsStream(configFileName);
        var props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Failed to load mime config");
        }
        this.prop = props;
    }

    /**
     * 与えられたファイル名の拡張子に対応するMIMEを返す。
     * 該当する拡張子が無い場合はapplication/octet-streamを返す。
     */
    public String getMime(String fileName) {
        var ext = fileName.substring(fileName.indexOf(".") + 1);
        return this.prop.getProperty(ext, "application/octet-stream");
    }
}
