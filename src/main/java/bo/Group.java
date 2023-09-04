package bo;

import java.util.List;

public class Group {
    private int id;
    private String nom;
    private List<Contact> contacts;


    public Group(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Group(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact c){
        this.contacts.add(c);
    }

    @Override
    public String toString() {
        return "Group{" +
                "nom='" + nom + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
