package bo;

public class ContactBuilder {

    private int id;
    private String nom;
    private String prenom;
    private String telephone1;
    private String telephone2;
    private String adresse;
    private String emailPerso;
    private String emailProfessionnel;
    private boolean genre;

    public ContactBuilder id(int id) {
        this.id = id;
        return this;
    }
    public ContactBuilder nom(String name) {
        this.nom = name;
        return this;
    }
    public ContactBuilder prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }
    public ContactBuilder telephone1(String tele1) {
        this.telephone1 = tele1;
        return this;
    }
    public ContactBuilder telephone2(String tele2) {
        this.telephone2 = tele2;
        return this;
    }
    public ContactBuilder adresse(String adr) {
        this.adresse = adr;
        return this;
    }
    public ContactBuilder emailPerso(String emailP) {
        this.emailPerso = emailP;
        return this;
    }
    public ContactBuilder emailProfessionnel(String emailPro) {
        this.emailProfessionnel = emailPro;
        return this;
    }
    public ContactBuilder genre(boolean a) {
        this.genre = a;
        return this;
    }

    public Contact build() {
        return new Contact(id,nom,prenom,telephone1,telephone2,adresse,emailPerso,emailProfessionnel,genre);
    }

}
