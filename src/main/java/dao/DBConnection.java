package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;


public class DBConnection {
    private Logger logger = Logger.getLogger(getClass());
    private static Connection connection;
    private static String dbUrl;
    private static String login;
    private static String password;
    private static String driver;

    private DBConnection() throws DataBaseException {
        try {
            // Load configuration from conf.properties file
            Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
            dbUrl = dbProperties.getProperty("db.url");
            login = dbProperties.getProperty("db.login");
            password = dbProperties.getProperty("db.password");
            driver = dbProperties.getProperty("db.driver");

            // Load the driver
            Class.forName(driver);

            // Create a connection to the database
            connection = DriverManager.getConnection(dbUrl, login, password);

        } catch (Exception e) {
            // Trace the error
            logger.error(e);
            // Raise the exception stack
            throw new DataBaseException(e);
        }
    }

    public static Connection getInstance() throws DataBaseException {
        if (connection == null) {
            try {
                new DBConnection();
            } catch (DataBaseException e) {
                throw e;
            }
        }

        return connection;
    }
}

