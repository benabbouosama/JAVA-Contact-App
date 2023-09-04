package dao;

import bo.Contact;
import bo.Group;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GroupDao {

    private Logger logger = Logger.getLogger(ContactDao.class);


    public void createGrp(String name) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String sqlInsert = "INSERT INTO `Group` (nom) VALUES (?);";
        try (PreparedStatement stm = con.prepareStatement(sqlInsert)) {
            stm.setString(1, name);
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur de ", e);
            throw new DataBaseException(e);
        }
    }



    public void addContactToGrp(Group grp, Contact c) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String sqlAdd = "INSERT into group_contact (id_grp,id_contact) values(?,?);";
        try(PreparedStatement stm = con.prepareStatement(sqlAdd)){
            stm.setInt(1,grp.getId());
            stm.setInt(2,c.getId());
            stm.executeUpdate();
            logger.info("Le contact "+c.getNom()+" a été ajouté au groupe "+grp.getNom()+" avec succès .");
        } catch(SQLException ex){
            logger.error("Erreur de ", ex);
            throw new DataBaseException(ex);
        }
    }

    public void deleteGrp(Group grp) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String sqlDel = " DELETE FROM `GROUP` WHERE id_grp = ?;";
        String sqlDel1= " DELETE FROM group_contact WHERE id_grp = ?;";
        try (PreparedStatement stm = con.prepareStatement(sqlDel);
        PreparedStatement stm1 = con.prepareStatement(sqlDel1)){
           stm.setInt(1,grp.getId());
           stm1.setInt(1,grp.getId());
           stm.executeUpdate();
           stm1.executeUpdate();
           logger.info(" Le groupe "+grp.getNom()+" a été supprimé avec succès .");
        }catch(SQLException e){
            logger.error("Erreur de ", e);
            throw new DataBaseException(e);
        }
    }

    public Group findGrp(String name) throws DataBaseException {
        Connection con = DBConnection.getInstance();
        String sqlFind = "SELECT * FROM `GROUP` WHERE nom = ?;";
        Group group = null;
        try (PreparedStatement stm = con.prepareStatement(sqlFind)) {
            stm.setString(1, name);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                // Assuming Group class exists with appropriate constructors
                group = new Group(resultSet.getInt("id_grp"),
                        resultSet.getString("nom")
                        );
            }
        } catch (SQLException e) {
            logger.error("Erreur de ", e);
            throw new DataBaseException(e);
        }
        return group;
    }

    public void createGrpByContactName() throws DataBaseException{
        ContactDao contactDao = new ContactDao();
        GroupDao groupDao = new GroupDao();

        List<Contact> contacts = contactDao.afficheAlphab();
        for (Contact c : contacts){
            String name = c.getNom();
            if(groupDao.findGrp(name)!=null)
                continue;
            groupDao.createGrp(name);
            groupDao.addContactToGrp(findGrp(name),c);
        }
    }


    public List<Contact> findGrpContacts(String name) throws DataBaseException {
        List<Contact> contacts = new ArrayList<>();
        Connection con = DBConnection.getInstance();
        String sqlSelect = "SELECT * FROM CONTACT c INNER JOIN GROUP_CONTACT gc ON c.ID = gc.ID_CONTACT INNER JOIN `GROUP` g ON gc.ID_GRP = g.ID_GRP WHERE g.NOM = ?;";

        try (PreparedStatement stm = con.prepareStatement(sqlSelect)) {
            stm.setString(1, name);
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                int contactId = resultSet.getInt("ID");
                String contactName = resultSet.getString("NOM");
                String telephone1 = resultSet.getString("TELEPHONE1");
                String telephone2 = resultSet.getString("TELEPHONE2");

                Contact contact = new Contact(contactId, contactName, telephone1, telephone2);
                contacts.add(contact);
            }
        } catch (SQLException ex) {
            throw new DataBaseException(ex);
        }

        return contacts;
    }




}
