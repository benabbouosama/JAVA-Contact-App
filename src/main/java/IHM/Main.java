package IHM;


import bll.AppManager;
import bll.BusinessLogicException;
import bo.Contact;
import bo.Group;
import dao.DBInstaller;
import dao.DataBaseException;
import dnl.utils.text.table.TextTable;
import org.apache.log4j.Logger;
import utils.ConsoleColors;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;



public class Main {
    private static Logger LOGGER = Logger.getLogger(Main.class);

    public static void printMenu() {
        /**
         * Affiche le menu de l'application
         */

        System.out.println(ConsoleColors.PURPLE+"1-	Créer un nouveau contact ");
        System.out.println("2-	Afficher la liste des contacts par ordre alphabétique ");
        System.out.println("3-	Supprimer un contact ");
        System.out.println("4-	Modifier un contact ");
        System.out.println("5-  Rechercher un contact par nom ");
        System.out.println("6-  Rechercher un contact par numéro  ");
        System.out.println("7-  Créer un groupe ");
        System.out.println("8- Ajouter un contact au groupe ");
        System.out.println("9- Supprimer un groupe ");
        System.out.println("10- Rechercher un groupe ");
        System.out.println("0-	Sortir "+ConsoleColors.RESET);
    }

    public static void main(String[] args) throws BusinessLogicException, DataBaseException {
        String logo =
                "\n" + ConsoleColors.PURPLE_BOLD_BRIGHT+
                        " ▄████▄   ▒█████   ███▄    █ ▄▄▄█████▓ ▄▄▄       ▄████▄  ▄▄▄█████▓\n" +
                        "▒██▀ ▀█  ▒██▒  ██▒ ██ ▀█   █ ▓  ██▒ ▓▒▒████▄    ▒██▀ ▀█  ▓  ██▒ ▓▒\n" +
                        "▒▓█    ▄ ▒██░  ██▒▓██  ▀█ ██▒▒ ▓██░ ▒░▒██  ▀█▄  ▒▓█    ▄ ▒ ▓██░ ▒░\n" +
                        "▒▓▓▄ ▄██▒▒██   ██░▓██▒  ▐▌██▒░ ▓██▓ ░ ░██▄▄▄▄██ ▒▓▓▄ ▄██▒░ ▓██▓ ░ \n" +
                        "▒ ▓███▀ ░░ ████▓▒░▒██░   ▓██░  ▒██▒ ░  ▓█   ▓██▒▒ ▓███▀ ░  ▒██▒ ░ \n" +
                        "░ ░▒ ▒  ░░ ▒░▒░▒░ ░ ▒░   ▒ ▒   ▒ ░░    ▒▒   ▓▒█░░ ░▒ ▒  ░  ▒ ░░   \n" +
                        "  ░  ▒     ░ ▒ ▒░ ░ ░░   ░ ▒░    ░      ▒   ▒▒ ░  ░  ▒       ░    \n" +
                        "░        ░ ░ ░ ▒     ░   ░ ░   ░        ░   ▒   ░          ░      \n" +
                        "░ ░          ░ ░           ░                ░  ░░ ░               \n" +
                        "░                                               ░                 \n"+ConsoleColors.RESET;

        System.out.println(logo);
        Properties config = new Properties();
        boolean g;
        int type;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("mode.properties")) {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String interfaceMode = config.getProperty("interface.mode");
        if (interfaceMode != null) {
            if (interfaceMode.equals("console")) {
                // Enable console functionality
                type = 0;
                System.out.println("Using console interface");
            } else if (interfaceMode.equals("GUI")) {
                try {
                    //On vérifie que la base de données n'est pas encore crée
                    if (!DBInstaller.checkIfAlreadyInstalled()) {
                        //Créer la base de données
                        DBInstaller.createDataBaseTables();
                        LOGGER.info("La base de données est crée correctement");
                    }
                } catch (Exception ex) {
                    //Dans le cas d'une erreur dans la création des tables on affiche un message d'erreur
                    System.err.println("Erreur lors de la création de la base de données, consultez le fichier log.txt pour plus de détails");
                    //On arrete l'application ici avec un code d'erreur
                    System.exit(-1);
                }
                // Enable UI functionality
                AppManager appManager = new AppManager();
                appManager.creerGroupesParContactsNoms();
                type = 1;
                System.out.println("Using GUI ");
            } else {
                type = -1;

            }
        } else {
            type = -2;
        }

        if (type == -1) {
            System.err.println("Invalid interface mode specified");
        } else if (type == -2) {
            System.err.println("Interface mode not specified in the configuration file");
        } else if (type == 1) {
            new UI();
        } else if (type == 0) {

            //création d'une instance de la classe qui gère la logique métier
            AppManager appManager = new AppManager();
            try {
                //On vérifie que la base de données n'est pas encore crée
                if (!DBInstaller.checkIfAlreadyInstalled()) {
                    //Créer la base de données
                    DBInstaller.createDataBaseTables();
                    LOGGER.info("La base de données est crée correctement");
                }
            } catch (Exception ex) {
                //Dans le cas d'une erreur dans la création des tables on affiche un message d'erreur
                System.err.println("Erreur lors de la création de la base de données, consultez le fichier log.txt pour plus de détails");
                //On arrete l'application ici avec un code d'erreur
                System.exit(-1);
            }

            //Pour lire les données au clavier
            Scanner sc = new Scanner(System.in);
            while (true) {
                //afficher le menu
                printMenu();
                //lire le choix
                System.out.println(ConsoleColors.PURPLE+"Saisir le numéro de votre choix: ");
                int choix = sc.nextInt();
                sc.nextLine();  //évite de sauter par nextInt
//    public Contact(String nom, String prenom, String telephone1, String telephone2, String adresse, String emailPerso, String emailProfessionnel, boolean genre) {
                appManager.creerGroupesParContactsNoms();
                switch (choix) {
                    case 1:
                        System.out.println("Saisir le nom de l'étudiant : ");
                        String nom = sc.nextLine();
                        System.out.println("Saisir le prénom de l'étudiant : ");
                        String prenom = sc.nextLine();
                        System.out.println("Saisir le numéro personnel : ");
                        String num1 = sc.nextLine();
                        System.out.println("Saisir le numéro professionnel : ");
                        String num2 = sc.nextLine();
                        System.out.println("Saisir l'adresse : ");
                        String adrs = sc.nextLine();
                        System.out.println("Saisir l'email personnel : ");
                        String emlPer = sc.nextLine();
                        System.out.println("Saisir l'email professionnel : ");
                        String emlPro = sc.nextLine();
                        System.out.println("Genre , saisir <M> pour homme et <F> pour femme( Homme par défault ) : ");
                        String genre = sc.nextLine();
                        if (genre == "F")
                            g = false;
                        else
                            g = true;

                        //Construire l'objet theme et puis le sauvegarder en base de données
                        try {
                            appManager.ajouterContact(new Contact(nom, prenom, num1, num2, adrs, emlPer, emlPro, g));
                        } catch (DataBaseException e) {
                            throw new RuntimeException(e);
                        } catch (BusinessLogicException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Contact ajouté avec succès.");
                        break;

                    case 2:

                        String[] columnNames = {
                                "First Name",
                                "Last Name",
                                "Telephone 1",
                                "Telephone 2",
                                "Address",
                                "Personal Email",
                                "Professional Email",
                                "Gender"
                        };

                        System.out.println("Voici la liste des contacts par ordre alphabétiques : ");
                        List<Contact> c = appManager.listeAlpha();

                        Object[][] data = new Object[c.size()][columnNames.length];

                        int rowIndex = 0;
                        for (Contact i : c) {
                            String contactOutput = i.toString();
                            // Parse the contact's toString output and extract the values
                            String[] contactValues = contactOutput.split(", ");
                            String contactNom = contactValues[0].substring(contactValues[0].indexOf("'") + 1);
                            String contactPrenom = contactValues[1].substring(contactValues[1].indexOf("'") + 1);
                            String contactTelephone1 = contactValues[2].substring(contactValues[2].indexOf("'") + 1);
                            String contactTelephone2 = contactValues[3].substring(contactValues[3].indexOf("'") + 1);
                            String contactAdresse = contactValues[4].substring(contactValues[4].indexOf("'") + 1);
                            String contactEmailPerso = contactValues[5].substring(contactValues[5].indexOf("'") + 1);
                            String contactEmailProfessionnel = contactValues[6].substring(contactValues[6].indexOf("'") + 1);
                            String contactGenre = contactValues[7].substring(contactValues[7].indexOf("=") + 1, contactValues[7].length() - 1);

                            Object[] contactData = {
                                    contactPrenom,
                                    contactNom,
                                    contactTelephone1,
                                    contactTelephone2,
                                    contactAdresse,
                                    contactEmailPerso,
                                    contactEmailProfessionnel,
                                    contactGenre
                            };

                            data[rowIndex] = contactData;
                            rowIndex++;
                        }

                        TextTable tt = new TextTable(columnNames, data);
                        tt.setAddRowNumbering(true);
                        tt.setSort(0);
                        tt.printTable();


                        break;

                    case 3:

                        System.out.println("Par nom ou par numéro? 1 pour nom , sinon tapez n'importe quoi ");
                        String rep = sc.nextLine();
                        if (rep.equals("1")) {
                            System.out.println("Saisir le nom : ");
                            String nom1 = sc.nextLine();
                            List<Contact> c1 = appManager.rechercherContactParNom(nom1);
                            for (Contact i : c1) {
                                System.out.println(i);
                                System.out.println("=====================================");
                            }
                            System.out.println("Saisir le numéro : ");
                            String num11 = sc.nextLine();
                            System.out.println("ce numéro , est-il personnel? , tapez 1 si oui , sinon tapez n'importe quoi ");
                            String num12 = sc.nextLine();
                            if (num12.equals("1")) {
                                appManager.supprimerContact(num11, true);
                            } else {
                                appManager.supprimerContact(num11, false);
                            }
                        } else {
                            System.out.println("Saisir le numéro : ");
                            String num11 = sc.nextLine();
                            System.out.println("ce numéro , est-il personnel? , tapez 1 si oui , sinon tapez n'importe quoi ");
                            String num12 = sc.nextLine();
                            if (num12.equals("1")) {
                                appManager.supprimerContact(num11, true);
                            } else {
                                appManager.supprimerContact(num11, false);
                            }
                        }
                        System.out.println("Le contact a été supprimé avec succès. ");
                        break;

                    case 4:
                        System.out.println("Saisir le nom que vous voulez modifier : ");
                        String nom1 = sc.nextLine();
                        List<Contact> c1 = appManager.rechercherContactParNom(nom1);
                        if(c1.isEmpty()){
                            System.out.println("Aucun contact ne contient ce nom !!");
                            break;
                        }
                        System.out.println("Voici la liste des contact qui ont ce nom : ");
                        for (Contact i : c1) {
                            System.out.println(i);
                            System.out.println("=====================================");
                        }
                        Contact cntc;
                        System.out.println("Saisir le numéro du contact que vous voulez modifier : ");
                        String num11 = sc.nextLine();
                        System.out.println("ce numéro , est-il personnel? , tapez 1 si oui , sinon tapez n'importe quoi ");
                        String num12 = sc.nextLine();
                        if (num12.equals("1")) {
                            cntc = appManager.rechercherContactParNum(num11, true);
                        } else {
                            cntc = appManager.rechercherContactParNum(num11, false);
                        }
                        System.out.println("Saisir le nouveau nom de l'étudiant(Tapez 0 si vous ne voulez pas le modifier )  : ");
                        String name = sc.nextLine();
                        if (name.equals("0"))
                            name = cntc.getNom();
                        System.out.println("Saisir le nouveau prénom de l'étudiant (Tapez 0 si vous ne voulez pas le modifier )  : ");
                        String prenam = sc.nextLine();
                        if (prenam.equals("0"))
                            prenam = cntc.getPrenom();
                        System.out.println("Saisir le nouveau numéro personnel (Tapez 0 si vous ne voulez pas le modifier )  : ");
                        String newNum = sc.nextLine();
                        String newNumProf = cntc.getTelephone2();
                        if (newNum.equals("0")) {
                            newNum = cntc.getTelephone1();
                            System.out.println("Saisir le nouveau numéro professionnel (Tapez 0 si vous ne voulez pas le modifier )  : ");
                            newNumProf = sc.nextLine();
                            if (newNumProf.equals("0"))
                                newNumProf = cntc.getTelephone2();
                        }
                        System.out.println("Saisir la nouvelle adresse (Tapez 0 si vous ne voulez pas le modifier )  : ");
                        String newAdrs = sc.nextLine();
                        if (newAdrs.equals("0"))
                            newAdrs = cntc.getAdresse();
                        System.out.println("Saisir le nouveau email personnel (Tapez 0 si vous ne voulez pas le modifier )  : ");
                        String newEmlPer = sc.nextLine();
                        if (newEmlPer.equals("0"))
                            newEmlPer = cntc.getEmailPerso();
                        System.out.println("Saisir le nouveau email professionnel (Tapez 0 si vous ne voulez pas le modifier )  : ");
                        String newEmlPro = sc.nextLine();
                        if (newEmlPro.equals("0"))
                            newEmlPro = cntc.getEmailProfessionnel();
                        Boolean Sexe = cntc.isGenre();
                        int idC= cntc.getId();
                        Contact modContact = new Contact(idC,name, prenam, newNum, newNumProf, newAdrs, newEmlPer, newEmlPro, Sexe);
                        appManager.modifierContact(modContact);
                        System.out.println("Votre contact a été modifié avec succès :) <3");
                        break;
                    case 5 :
                        System.out.println("Saisir le nom que vous voulez rechercher : ");
                        String nomR = sc.nextLine();
                        List<Contact> cR = appManager.rechercherContactParNom(nomR);
                        if(cR.isEmpty()){
                            System.out.println("Aucun contact ne contient ce nom !!");
                            break;
                        }
                        System.out.println("Voici la liste des contact qui ont ce nom : ");
                        for (Contact i : cR) {
                            System.out.println(i);
                            System.out.println("=====================================");
                        }
                        break;
                    case 6:
                        Contact cntcR;
                        System.out.println("Saisir le numéro du contact que vous voulez rechercher : ");
                        String numRe = sc.nextLine();
                        System.out.println("ce numéro , est-il personnel? , tapez 1 si oui , sinon tapez n'importe quoi ");
                        String numR = sc.nextLine();
                        if (numR.equals("1")) {
                            cntcR = appManager.rechercherContactParNum(numRe, true);
                        } else {
                            cntcR = appManager.rechercherContactParNum(numRe, false);
                        }
                        System.out.println(cntcR);
                        break;
                    case 7:
                        System.out.println("Choisir un nom pour le groupe  : ");
                        String nomGrp = sc.nextLine();
                        try {
                            appManager.ajouterGroupe(nomGrp);
                        } catch (DataBaseException e) {
                            throw new RuntimeException(e);
                        } catch (BusinessLogicException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Groupe créé avec succès. ");
                        break;
                    case 8:
                        boolean aa=true;
                        while(aa){
                            System.out.println("Saisir le nom de ce groupe (sasir 0 pour quitter ) :");
                            String nGrp = sc.nextLine();
                            System.out.println("saisir le numéro du contact (soit personnel soit professionnel)");
                            String nmCnt = sc.nextLine();
                            if (nGrp.equals("0")) {
                                aa = false;
                                break;
                            }
                            Contact cc = appManager.rechercherContactParNum(nmCnt,true);
                            Contact cc1 = appManager.rechercherContactParNum(nmCnt,false);
                            if(cc==null){
                                appManager.ajoutContactAuGrp(nGrp,cc1);
                            }else{
                                appManager.ajoutContactAuGrp(nGrp,cc);
                            }
                        }
                        break;
                    case 9:
                        System.out.println("Saisir le nom du groupe que vous voulez supprimer : ");
                        String nameGrp = sc.nextLine();
                        try {
                            appManager.supprimerGroupe(nameGrp);
                        } catch (DataBaseException e) {
                            throw new RuntimeException(e);
                        } catch (BusinessLogicException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Groupe supprimé avec succès. ");
                        break;
                    case 10:
                        System.out.println("Saisir le nom du groupe à rechercher : ");
                        String nomGroup = sc.nextLine();
                        Group myGrp = null;
                        try {
                            myGrp= appManager.rechercherGroupe(nomGroup);
                        } catch (DataBaseException e) {
                            throw new RuntimeException(e);
                        } catch (BusinessLogicException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Voici les informations de ce groupe : ");
                        System.out.println("Nom du groupe : "+myGrp.getNom()); // à développer plus tard
                        System.out.println("Les contacts : ");
                        for (Contact i : appManager.afficherContactsGrp(myGrp.getNom())){
                            System.out.println(i.toString2());
                        }
                        break;
                    case 0:
                        System.out.println("Bye!");
                        System.exit(0);

                }
            }


        }

    }
}