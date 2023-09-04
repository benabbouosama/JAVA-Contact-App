package dao;

import bo.Contact;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactDao {
    private Logger logger = Logger.getLogger(ContactDao.class);
    private int calculateEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
                }
            }
        }

        return dp[m][n];
    }



    public void create(Contact c) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String sqlInsert = "INSERT into Contact(nom,prenom,telephone1,telephone2,adresse,emailPerso,emailProfessionnel,genre) VALUES (?,?,?,?,?,?,?,?);";
        try (PreparedStatement stm = con.prepareStatement(sqlInsert)) {
            stm.setString(1, c.getNom());
            stm.setString(2, c.getPrenom());
            stm.setString(3, c.getTelephone1());
            stm.setString(4, c.getTelephone2());
            stm.setString(5, c.getAdresse());
            stm.setString(6, c.getEmailPerso());
            stm.setString(7, c.getEmailProfessionnel());
            stm.setBoolean(8, c.isGenre());
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur de ", e);
            throw new DataBaseException(e);
        }
    }


    public List<Contact> afficheAlphab() throws DataBaseException {
        Connection con = DBConnection.getInstance();
        if (con == null) {
            return null;
        }

        List<Contact> contacts = new LinkedList<>();

        String query = "SELECT * FROM Contact ORDER BY Nom ASC;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Contact contact = Contact.builder()
                        .id(resultSet.getInt("id"))
                        .nom(resultSet.getString("nom"))
                        .prenom(resultSet.getString("prenom"))
                        .telephone1(resultSet.getString("telephone1"))
                        .telephone2(resultSet.getString("telephone2"))
                        .adresse(resultSet.getString("adresse"))
                        .emailPerso(resultSet.getString("emailPerso"))
                        .emailProfessionnel(resultSet.getString("emailProfessionnel"))
                        .genre(resultSet.getBoolean("genre"))
                        .build();

                contacts.add(contact);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return contacts;
    }

    public void delete(String num, boolean type) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String contactQuery;
        String groupContactQuery;

        if (type) {
            contactQuery = "DELETE FROM Contact WHERE telephone1 = ?";
        } else {
            contactQuery = "DELETE FROM Contact WHERE telephone2 = ?";
        }

        groupContactQuery = "DELETE FROM group_contact WHERE id_contact IN (SELECT id FROM Contact WHERE telephone1 = ? OR telephone2 = ?)";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement contactStm = con.prepareStatement(contactQuery);
                 PreparedStatement groupContactStm = con.prepareStatement(groupContactQuery)) {

                // Set the values for the parameters in the SQL statements
                contactStm.setString(1, num);
                groupContactStm.setString(1, num);
                groupContactStm.setString(2, num);

                // Execute the SQL statements
                groupContactStm.executeUpdate();
                contactStm.executeUpdate();

                con.commit();
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackException) {
                // Log the rollback exception
                logger.error("Rollback error: ", rollbackException);
            }

            // Log the error
            logger.error("Erreur de ", e);
            // Throw the exception
            throw new DataBaseException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                // Log the error
                logger.error("Error resetting auto-commit: ", e);
            }
        }
    }




    public void modify(Contact c) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String query = "UPDATE Contact SET nom=?, prenom=?, telephone1=?, telephone2=?, adresse=?, emailPerso=?, emailProfessionnel=?, genre=? WHERE id=?;";
        try (PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, c.getNom());
            stm.setString(2, c.getPrenom());
            stm.setString(3, c.getTelephone1());
            stm.setString(4, c.getTelephone2());
            stm.setString(5, c.getAdresse());
            stm.setString(6, c.getEmailPerso());
            stm.setString(7, c.getEmailProfessionnel());
            stm.setBoolean(8, c.isGenre());
            stm.setInt(9, c.getId()); // Set the value for the "id" parameter
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur de ", e);
            throw new DataBaseException(e);
        }
    }


    public List<Contact> find(String nom) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        if (con == null) {
            return null;
        }
        List<Contact> contacts = new LinkedList<>();

        String query = "SELECT * FROM Contact WHERE nom=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, nom);  // Set the value for the parameter

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contact c = Contact.builder()
                        .id(resultSet.getInt("id"))
                        .nom(resultSet.getString("nom"))
                        .prenom(resultSet.getString("prenom"))
                        .telephone1(resultSet.getString("telephone1"))
                        .telephone2(resultSet.getString("telephone2"))
                        .adresse(resultSet.getString("adresse"))
                        .emailPerso(resultSet.getString("emailPerso"))
                        .emailProfessionnel(resultSet.getString("emailProfessionnel"))
                        .genre(resultSet.getBoolean("genre"))
                        .build();

                contacts.add(c);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return contacts;
    }


    public Contact findByNum(String num, Boolean type) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String query = null;
        if (type) {
            query = "SELECT * FROM Contact WHERE telephone1 = ?;";
        } else {
            query = "SELECT * FROM Contact WHERE telephone2 = ?;";
        }
        Contact c = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, num);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                c = Contact.builder()
                        .id(resultSet.getInt("id"))
                        .nom(resultSet.getString("nom"))
                        .prenom(resultSet.getString("prenom"))
                        .telephone1(resultSet.getString("Telephone1"))
                        .telephone2(resultSet.getString("Telephone2"))
                        .adresse(resultSet.getString("adresse"))
                        .emailPerso(resultSet.getString("emailPerso"))
                        .emailProfessionnel(resultSet.getString("emailProfessionnel"))
                        .genre(resultSet.getBoolean("genre"))
                        .build();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return c;
    }

    public List<Contact> findLike(String name) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String sql = "SELECT * FROM Contact WHERE SOUNDEX(nom) = SOUNDEX(?);";
        List<Contact> contacts = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Contact c = Contact.builder()
                        .id(resultSet.getInt("id"))
                        .nom(resultSet.getString("nom"))
                        .prenom(resultSet.getString("prenom"))
                        .telephone1(resultSet.getString("telephone1"))
                        .telephone2(resultSet.getString("telephone2"))
                        .adresse(resultSet.getString("adresse"))
                        .emailPerso(resultSet.getString("emailPerso"))
                        .emailProfessionnel(resultSet.getString("emailProfessionnel"))
                        .genre(resultSet.getBoolean("genre"))
                        .build();

                contacts.add(c);
            }
        } catch (SQLException ex) {
            logger.error("Erreur de ", ex);
            throw new DataBaseException(ex);
        }

        return contacts;
    }


    public List<Contact> findv2(String nom) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        if (con == null) {
            return null;
        }
        List<Contact> contacts = new LinkedList<>();

        String query = "SELECT * FROM Contact";
        try (Statement stmt = con.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                Contact c = Contact.builder()
                        .id(resultSet.getInt("id"))
                        .nom(resultSet.getString("nom"))
                        .prenom(resultSet.getString("prenom"))
                        .telephone1(resultSet.getString("telephone1"))
                        .telephone2(resultSet.getString("telephone2"))
                        .adresse(resultSet.getString("adresse"))
                        .emailPerso(resultSet.getString("emailPerso"))
                        .emailProfessionnel(resultSet.getString("emailProfessionnel"))
                        .genre(resultSet.getBoolean("genre"))
                        .build();

                // Calculate the edit distance between the input name and the contact's name
                int distance = calculateEditDistance(nom, c.getNom());

                // Specify the maximum allowed edit distance to consider a match
                int maxAllowedDistance = 2;

                // If the edit distance is within the allowed range, consider it a match
                if (distance <= maxAllowedDistance) {
                    contacts.add(c);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return contacts;
    }



}



