package bo;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String telephone1;
    private String telephone2;
    private String adresse;
    private String emailPerso;
    private String emailProfessionnel;
    private boolean genre;

    private String g;

    public Contact() {
    }

    public Contact(int id, String nom, String prenom, String telephone1, String telephone2, String adresse, String emailPerso, String emailProfessionnel, boolean genre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.adresse = adresse;
        this.emailPerso = emailPerso;
        this.emailProfessionnel = emailProfessionnel;
        this.genre = genre;
    }

    public Contact(int id, String nom, String prenom, String telephone1, String telephone2, String adresse, String emailPerso, String emailProfessionnel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.adresse = adresse;
        this.emailPerso = emailPerso;
        this.emailProfessionnel = emailProfessionnel;
    }

    public Contact(String nom, String prenom, String telephone1, String telephone2, String adresse, String emailPerso, String emailProfessionnel, boolean genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.adresse = adresse;
        this.emailPerso = emailPerso;
        this.emailProfessionnel = emailProfessionnel;
        this.genre = genre;
    }

    public Contact(int id, String nom, String telephone1, String telephone2) {
        this.id = id;
        this.nom = nom;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
    }

    public Contact( String nom, String telephone1, String telephone2) {
        this.nom = nom;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmailPerso() {
        return emailPerso;
    }

    public void setEmailPerso(String emailPerso) {
        this.emailPerso = emailPerso;
    }

    public String getEmailProfessionnel() {
        return emailProfessionnel;
    }

    public void setEmailProfessionnel(String emailProfessionnel) {
        this.emailProfessionnel = emailProfessionnel;
    }

    public boolean isGenre() {
        return genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public static ContactBuilder builder() {
        return new ContactBuilder();
    }

    @Override
    public String toString() {
        if(genre)
            g="Male";
        else
            g="Female";

        return "Contact{" +
                " nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone1='" + telephone1 + '\'' +
                ", telephone2='" + telephone2 + '\'' +
                ", adresse='" + adresse + '\'' +
                ", emailPerso='" + emailPerso + '\'' +
                ", emailProfessionnel='" + emailProfessionnel + '\'' +
                ", genre=" + g +
                '}';
    }

    public String toString2() {

        return "Contact{" +
                " nom='" + nom + '\'' +
                ", telephone1='" + telephone1 + '\'' +
                ", telephone2='" + telephone2 + '\'' +
                '}';
    }
}
