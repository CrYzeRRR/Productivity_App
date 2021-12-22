import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;

public class ManageProperties {

    static final String fileName = "config.properties";
    static Properties prop = new Properties();
    static Data dataObj;

    public static void main(String[] args) {

        dataObj = new Data();
        loadProperties();
        System.out.println();
        displayProperties();
        System.out.println();

        writeProperties(args[0], args[1], args[2]);
        System.out.println();
        loadProperties();
        displayProperties();
    }

    static void displayProperties() {
        System.out.println(dataObj.url);
        System.out.println(dataObj.user);
        System.out.println(dataObj.password);
    }

    static void writeProperties(String url, String user, String password) {
        try (OutputStream output = new FileOutputStream(fileName)) {

            // set the properties value
            prop.setProperty("url", url);
            prop.setProperty("user", user);
            prop.setProperty("password", password);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Err write: " + io.getMessage());
        }
    }

    static void loadProperties() {
        try (InputStream input = new FileInputStream(fileName)) {

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            // System.out.println(prop.getProperty("url"));
            // System.out.println(prop.getProperty("user"));
            // System.out.println(prop.getProperty("password"));

            dataObj.url = prop.getProperty("url");
            dataObj.user = prop.getProperty("user");
            dataObj.password = prop.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Err load: " + ex.getMessage());
        }
    }
}

class Data {
    public String url;
    public String user;
    public String password;
}

