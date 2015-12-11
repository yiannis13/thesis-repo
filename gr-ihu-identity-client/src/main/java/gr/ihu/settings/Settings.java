package gr.ihu.settings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author John
 */
public class Settings {

    private static Settings settings;
    private Map<String, String> settingsMap;
    private final static Object lock = new Object();

    private Settings() {
        load();
    }

    public final synchronized void load() {
        //load file as an InputStream
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        //create a Property object
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        settingsMap = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            settingsMap.put(key, value);
        }
    }

    public URI getServiceUri() {
        String uriStr = this.settingsMap.get("gr.ihu.base.url");
        try {
            return new URI(uriStr);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getUsersPath() {
        return this.settingsMap.get("gr.ihu.identity.users");
    }

    public String getLoginPath() {
        return this.settingsMap.get("gr.ihu.identity.login");
    }
    
    public String getPostalPath() {
        return this.settingsMap.get("gr.ihu.identity.postal");
    }
    
    public String getBankPath() {
        return this.settingsMap.get("gr.ihu.identity.bank");
    }

    public static Settings getInstance() {
        if (settings != null) {
            return settings;
        } else {
            synchronized (lock) {
                settings = new Settings();
                return settings;
            }
        }
    }//end of getInstance() method

}
