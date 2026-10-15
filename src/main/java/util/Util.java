package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    //body.length is quicker, but making a function allows for better error handling
    public static int contentLength(byte[] body) {
        int contentLength = 0; //Must initialise with a value

        try {
            contentLength = body.length;
        } catch (Exception e) {
            System.out.println("Error finding the content length: " + e);
        }

        return contentLength;
    }

    /**
     *
     * @param key The key for which the value should be retrieved.
     * @return A {@link String} which is the value of the key.
     * @throws Exception {@code FileInputStream()} and {@code load()} require this.
     */
    public static String retrieveProperty(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/scheduler.properties")); //Loading the .properties file

        String value = properties.getProperty(key);
        return value;
    }}

