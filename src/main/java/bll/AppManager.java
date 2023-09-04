package bll;

import bo.Contact;
import bo.Group;
import dao.ContactDao;
import dao.DataBaseException;
import dao.GroupDao;
import org.apache.log4j.Logger;

import java.util.List;

public class AppManager {
    private static Logger LOGGER = Logger.getLogger(AppManager.class);

    private ContactDao contactDao = new ContactDao();
    private GroupDao groupDao = new GroupDao();


    public void ajouterContact(Contact a) throws DataBaseException ,BusinessLogicException {
        //On vérifie d'abord que le contact n'existe pas
        Contact contact = rechercherContactParNum(a.getTelephone1(),true);
        Contact contact1 = rechercherContactParNum(a.getTelephone2(),false);
        if ( contact != null & contact1 != null) {
            throw new BusinessLogicException("Le contact existe déjà ! ");
        }
        contactDao.create(a);
    }

    public Contact rechercherContactParNum(String num, Boolean type) throws DataBaseException {
        Contact c = contactDao.findByNum(num,type);
        return c;
    }

    public List<Contact> rechercherContactParNom(String nom ) throws DataBaseException {
        List <Contact> a = contactDao.find(nom);
        if (a.isEmpty()){
            List <Contact> c = contactDao.findv2(nom);
            if(c.isEmpty()){
                List <Contact> b = contactDao.findLike(nom);
                return b;
            }
            return c;
        }
        return a;
    }

    public void modifierContact(Contact c) throws DataBaseException , BusinessLogicException{
        // On vérifie d'abord que le contact existe déjà
        Contact contact = rechercherContactParNum(c.getTelephone1(),true);
        Contact contact1 = rechercherContactParNum(c.getTelephone2(),false);

        if ( contact == null & contact1 == null) {
            throw new BusinessLogicException("Le contact n'existe pas ");
        }

        //sinon on modifie le contact
        contactDao.modify(c);
    }

    public void supprimerContact(String num, boolean type) throws BusinessLogicException, DataBaseException {
        // Check if the contact exists before attempting to delete
        Contact existingContact = rechercherContactParNum(num, type);
        if (existingContact == null) {
            throw new BusinessLogicException("Le contact n'existe pas");
        }
        List<Contact> conts = afficherContactsGrp(existingContact.getNom());

        // The contact exists, proceed with the deletion
        try {
            ContactDao contactDao = new ContactDao();
            contactDao.delete(num, type);
            if(conts.isEmpty())
                supprimerGroupe(existingContact.getNom());
        } catch (DataBaseException e) {
            throw new BusinessLogicException("Une erreur s'est produite lors de la suppression du contact", e);
        }
    }



    public List<Contact> listeAlpha() throws DataBaseException , BusinessLogicException {
        List<Contact> contacts;
        contacts = contactDao.afficheAlphab();
        if (contacts == null){
            throw new BusinessLogicException("Il n'existe aucun contact dans la base de données ");
        }
        return contacts;
    }

    public void ajouterGroupe(String name) throws DataBaseException, BusinessLogicException {
        Group grp1 = groupDao.findGrp(name);
        if(grp1 != null)
            throw new BusinessLogicException("Le groupe existe déjà");
        groupDao.createGrp(name);
    }

    public void supprimerGroupe(String name) throws DataBaseException, BusinessLogicException {
        Group grp = groupDao.findGrp(name);
        if(grp == null)
            throw new BusinessLogicException("il n'existe aucun groupe portant ce nom !! ");
        groupDao.deleteGrp(grp);
    }

    public Group rechercherGroupe(String name) throws DataBaseException, BusinessLogicException {
        Group grp = groupDao.findGrp(name);
        if (grp == null)
            throw new BusinessLogicException("il n'existe aucun groupe portant ce nom !! ");
        return grp;
    }

    public void creerGroupesParContactsNoms() throws DataBaseException {
        groupDao.createGrpByContactName();
    }

    public void ajoutContactAuGrp(String grpN, Contact c) throws DataBaseException, BusinessLogicException {
        Group grp = groupDao.findGrp(grpN);
        if(grp == null)
            throw new BusinessLogicException("il n'existe aucun groupe portant ce nom !!");
        if(c == null)
            throw new BusinessLogicException("il n'existe aucun contact portant ce numéro !!");
        groupDao.addContactToGrp(grp,c);
    }

    public List<Contact> afficherContactsGrp(String name) throws DataBaseException, BusinessLogicException {
        Group grp = groupDao.findGrp(name);
        if(grp == null)
            throw new BusinessLogicException("il n'existe aucun groupe portant ce nom !!");
        List<Contact> myContacts = groupDao.findGrpContacts(name);
        return myContacts;
    }
}
