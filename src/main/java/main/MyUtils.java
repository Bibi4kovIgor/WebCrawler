package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyUtils {

    /**
     * Get database path and database driver properties
     *
     * @param propertyName
     * @return String propertyType
     * @throws IOException
     */
    public static String lodProperty(String propertyName) {
        Properties prop = new Properties();
        String propertyValue = "";
        try {
            InputStream input = new FileInputStream("src/main/java/properties/config.properties");
            prop.load(input);
            propertyValue = prop.getProperty(propertyName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertyValue;
    }
}
