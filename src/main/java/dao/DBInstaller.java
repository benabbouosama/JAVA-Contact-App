package dao;



import org.apache.log4j.Logger;
import utils.FileManager;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;


public class DBInstaller {

    private static Logger LOGGER = Logger.getLogger(DBInstaller.class);

    public static void createDataBaseTables() throws DataBaseException {

        try {
            Connection con = DBConnection.getInstance();


            String sql = """
                                CREATE TABLE Contact (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        nom VARCHAR(255),
                                        prenom VARCHAR(255),
                                        telephone1 VARCHAR(255),
                                        telephone2 VARCHAR(255),
                                        adresse VARCHAR(255),
                                        emailPerso VARCHAR(255),
                                        emailProfessionnel VARCHAR(255),
                                        genre BOOLEAN
                                );
                                CREATE TABLE `group` (
                                        id_grp INT AUTO_INCREMENT PRIMARY KEY,
                                nom VARCHAR(255) 
                                                  );
                                        
                                CREATE TABLE group_contact (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        id_grp INT,
                                        id_contact INT,
                                        FOREIGN KEY (id_grp) REFERENCES `group`(id_grp),
                                        FOREIGN KEY (id_contact) REFERENCES contact(id)
                                                  );
                                              
  
                              
                                                             
                                        
                              
                             
                    """;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }catch (Exception ex){
            LOGGER.error(ex);
            throw  new DataBaseException(ex);
        }

    }

    public static boolean checkIfAlreadyInstalled() throws IOException {

        String userHomeDirectory = System.getProperty("user.home");
        Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
        //String dbName = dbProperties.getProperty("db.name");
        String dbName="dbapp2";
        String dataBaseFile = userHomeDirectory + "\\" + dbName + ".mv.db";
        System.out.println(dbName);
        return FileManager.fileExists(dataBaseFile);

    }

    //

}
