package IHM;

import bll.AppManager;
import bll.BusinessLogicException;
import bo.Contact;
import bo.Group;
import dao.DataBaseException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class UI extends JFrame {
    private AppManager appManager;

    public UI() {
        appManager = new AppManager();

        setUndecorated(true);
        setSize(800, 600);

        Color titleBarColor = Color.decode("#2e0b43");
        JPanel titleBarPanel = new JPanel();
        titleBarPanel.setBackground(titleBarColor);
        titleBarPanel.setPreferredSize(new Dimension(getWidth(), 30));
        titleBarPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBarPanel.add(titleLabel, BorderLayout.CENTER);

        // Create the logo label and add it to the title bar panel
        JLabel logoLabel = new JLabel();
        String logoImagePath = "images/Logo.png";
        ImageIcon logoIcon = new ImageIcon(logoImagePath);
        logoLabel.setIcon(logoIcon);

        // Add margin to the logoLabel to move it slightly to the right
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // Add the logoLabel to the titleBarPanel using FlowLayout
        titleBarPanel.add(logoLabel, BorderLayout.WEST);

        // Create the close button and add it to the title bar panel
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.decode("#E2F3FF")); // Set the color of the close button
        closeButton.setBackground(titleBarColor);
        closeButton.setFocusable(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.decode("#E2F3FF"));
                closeButton.setForeground(Color.decode("#2e0b43"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(titleBarColor);
                closeButton.setForeground(Color.decode("#E2F3FF"));
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        titleBarPanel.add(closeButton, BorderLayout.EAST);
        JPanel contentPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String imagePath = "images/SearchTmp.png";
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                g.drawImage(image, 300, 170, 208,179, this);
            }
        };
        contentPanel.setBackground(Color.decode("#2E0B43"));
        contentPanel.setLayout(new BorderLayout());

        // Create the search contact panel
        JPanel searchContactPanel = new JPanel();
        searchContactPanel.setLayout(new BorderLayout());
        searchContactPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the search contact panel
        searchContactPanel.setBackground(Color.decode("#2E0B43"));
        searchContactPanel.setForeground(Color.decode("#B3A7B8"));

        JPanel titleBarPanel1 = new JPanel(new BorderLayout());
        titleBarPanel1.setBackground(Color.decode("#2E0B43"));
        titleBarPanel1.setForeground(Color.WHITE);

        JLabel titleLabel1 = new JLabel("Search Contact");
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the title label to the title bar panel
        titleBarPanel1.add(titleLabel, BorderLayout.CENTER);


        // Create the search contact components
        JTextField searchTextField = new JTextField();
        searchTextField.setBackground(Color.decode("#DDE6F0"));
        searchTextField.setForeground(Color.decode("#4B7FA4"));
        searchTextField.setFont(searchTextField.getFont().deriveFont(Font.BOLD));
        JComboBox<String> searchComboBox = new JComboBox<>(new String[]{"Personnel", "Professionnel"});
        searchComboBox.setBackground(Color.decode("#DDE6F0"));
        searchComboBox.setForeground(Color.decode("#4B7FA4"));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(Color.decode("#DDE6F0"));
        searchButton.setForeground(Color.decode("#4B7FA4"));
        JButton searchNButton = new JButton("Search");
        searchNButton.setBackground(Color.decode("#DDE6F0"));
        searchNButton.setForeground(Color.decode("#4B7FA4"));
        JComboBox<String> typeDeRecherche = new JComboBox<>(new String[]{"Numéro", "Nom"});
        typeDeRecherche.setBackground(Color.decode("#DDE6F0"));
        typeDeRecherche.setForeground(Color.decode("#4B7FA4"));


        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the values from searchTextField and searchComboBox
                String num = searchTextField.getText();
                boolean type = searchComboBox.getSelectedIndex() == 0; // true if "Personnel" selected, false if "Professionnel" selected
                if(num.equals("")) {
                    JOptionPane.showMessageDialog(null, "Remplir la barre de recherche !!");
                    return;
                }
                // Call the rechercherContactParNum function from the AppManager class with the obtained values
                try {
                    Contact cnt = appManager.rechercherContactParNum(num, type);
                    if (cnt != null) {
                        Window[] windows = Window.getWindows();
                        for (Window window : windows) {
                                if (window != null && window.isVisible() && !(window instanceof ContactTableFrame)) {
                                window.dispose();
                            }
                        }
                    }
                    List<Contact> cnts = new ArrayList<Contact>();
                    cnts.add(cnt);
                    ContactTableFrame contactTableFrame = new ContactTableFrame(cnts);
                    contactTableFrame.setVisible(true);
//

                } catch (DataBaseException ex) {
                    ex.printStackTrace();
                }
            }
        });
        searchNButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the values from searchTextField and searchComboBox
                String nom = searchTextField.getText();
                if(nom.equals("")) {
                    JOptionPane.showMessageDialog(null, "Remplir la barre de recherche !!");
                    return;
                }

                // Call the rechercherContactParNum function from the AppManager class with the obtained values
                try {
                    List <Contact> cnts = appManager.rechercherContactParNom(nom);
                    if (cnts != null) {
                        Window[] windows = Window.getWindows();
                        for (Window window : windows) {
                            if (window != null && window.isVisible() && !(window instanceof ContactTableFrame)) {
                                window.dispose();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Il n'existe aucun contact contenant ce nom !!","Erreur",JOptionPane.ERROR_MESSAGE);
                    }
                    ContactTableFrame c = new ContactTableFrame(cnts);
                    c.setVisible(true);

                } catch (DataBaseException ex) {
                    ex.printStackTrace();
                }
            }
        });


        // Add the search components to the search contact panel
        searchContactPanel.add(titleBarPanel, BorderLayout.NORTH);
        searchContactPanel.add(searchTextField, BorderLayout.NORTH);
        searchContactPanel.add(searchComboBox, BorderLayout.CENTER);
        searchContactPanel.add(searchButton, BorderLayout.LINE_END);
        searchContactPanel.add(typeDeRecherche,BorderLayout.AFTER_LAST_LINE);

        typeDeRecherche.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) typeDeRecherche.getSelectedItem();
                    if (selectedOption.equals("Nom")) {
                        searchContactPanel.remove(searchComboBox);
                        searchContactPanel.remove(searchButton);
                        searchContactPanel.add(searchNButton,BorderLayout.LINE_END);
                        searchContactPanel.revalidate();
                        searchContactPanel.repaint();
                    }
                }
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) typeDeRecherche.getSelectedItem();
                    if (selectedOption.equals("Numéro")) {
                        searchContactPanel.add(searchComboBox);
                        searchContactPanel.revalidate();
                        searchContactPanel.repaint();
                    }
                }
            }
        });

        // Position the search contact frame in the middle
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(177, 200, 130, 200);
        centerPanel.add(searchContactPanel, gbc);

        contentPanel.add(titleBarPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);


        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.decode("#2E0B43"));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Dimension panelSize = new Dimension(btnPanel.getPreferredSize().width, 80);
        btnPanel.setPreferredSize(panelSize);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#2E0B43"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create the "Afficher la liste des contacts" button
        JButton showContactListButton = new JButton("Afficher la liste des contacts");
        showContactListButton.setBackground(Color.decode("#FFFADE"));
        showContactListButton.setForeground(Color.decode("#342C2C"));
        showContactListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Contact> contactList = appManager.listeAlpha();
                    if (!contactList.isEmpty()) {
                        Window[] windows = Window.getWindows();
                        for (Window window : windows) {
                            if (window != null && window.isVisible() && !(window instanceof ContactTableFrame)) {
                                window.dispose();
                            }
                        }
                    }
                    ContactTableFrame contactListFrame = new ContactTableFrame(contactList);
                    contactListFrame.setVisible(true);
                } catch (DataBaseException ex) {
                    ex.printStackTrace();
                } catch (BusinessLogicException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton createGrp = new JButton("Créer un groupe ");
        createGrp.setBackground(Color.decode("#FFFADE"));
        createGrp.setForeground(Color.decode("#342C2C"));
        createGrp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof AjoutGroupe)) {
                        window.dispose();
                    }
                }
                AjoutGroupe ajoutGroupe = new AjoutGroupe();
                ajoutGroupe.setVisible(true);
            }
        });

        // Create the "Créer un contact" button
        JButton createContactButton = new JButton("Créer un contact");
        createContactButton.setBackground(Color.decode("#FFFADE"));
        createContactButton.setForeground(Color.decode("#342C2C"));
        createContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof CreateContact)) {
                        window.dispose();
                    }
                }
                CreateContact creerContact = new CreateContact();
                creerContact.setVisible(true);
            }
        });
        JButton deleteGrpButton = new JButton("Supprimer un groupe");
        deleteGrpButton.setBackground(Color.decode("#FFFADE"));
        deleteGrpButton.setForeground(Color.decode("#342C2C"));
        deleteGrpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof DeleteGroup)) {
                        window.dispose();
                    }
                }
                DeleteGroup supprimerGroupe = new DeleteGroup();
                supprimerGroupe.setVisible(true);
            }
        });
        btnPanel.add(deleteGrpButton);

        JButton AjoutCntGrpButton = new JButton("Ajouter un contact au groupe");
        AjoutCntGrpButton.setBackground(Color.decode("#FFFADE"));
        AjoutCntGrpButton.setForeground(Color.decode("#342C2C"));
        AjoutCntGrpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof AjoutContactAuGroupe)) {
                        window.dispose();
                    }
                }
                AjoutContactAuGroupe ajoutCntGgrp = new AjoutContactAuGroupe();
                ajoutCntGgrp.setVisible(true);
            }
        });
        btnPanel.add(AjoutCntGrpButton);

        JButton deleteContactButton = new JButton("Supprimer un contact");
        deleteContactButton.setBackground(Color.decode("#FFFADE"));
        deleteContactButton.setForeground(Color.decode("#342C2C"));
        deleteContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof DeleteContact)) {
                        window.dispose();
                    }
                }
                DeleteContact supprimerContact = new DeleteContact();
                supprimerContact.setVisible(true);
            }
        });
        btnPanel.add(deleteContactButton);

        // Add the "Afficher la liste des contacts" button to the button panel
        btnPanel.add(showContactListButton);

        // Add the "Créer un contact" button to the button panel
        btnPanel.add(createContactButton);

        btnPanel.add(createGrp);

        // Create a separate panel for the button panel
        JPanel buttonContainerPanel = new JPanel(new BorderLayout());

// Add some empty space above the button panel using an empty border
        buttonContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Create the button panel
        JPanel buttonPanelos = new JPanel();
        buttonPanelos.setBackground(Color.decode("#FFFADE"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));



        JButton modifierButton = new JButton("Modifier un contact");
        modifierButton.setBackground(Color.decode("#FFFADE"));
        modifierButton.setForeground(Color.decode("#342C2C"));
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof ModifyContact)) {
                        window.dispose();
                    }
                }
                ModifyContact modifyContact = new ModifyContact();
                modifyContact.setVisible(true);
            }
        });

        btnPanel.add(modifierButton);

        JButton afficherGrpButton = new JButton("Afficher un groupe ");
        afficherGrpButton.setBackground(Color.decode("#FFFADE"));
        afficherGrpButton.setForeground(Color.decode("#342C2C"));
        afficherGrpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof GroupNom)) {
                        window.dispose();
                    }
                }
                GroupNom groupNom = new GroupNom();
                groupNom.setVisible(true);
            }
        });

        btnPanel.add(afficherGrpButton);

        JButton aboutButton = new JButton();
        aboutButton.setBackground(Color.decode("#DDE6F0"));
        aboutButton.setForeground(Color.decode("#4B7FA4"));

        String iconPath = "images/about.png";
        aboutButton.setIcon(new ImageIcon(iconPath));
        aboutButton.setBorderPainted(false);
        aboutButton.setContentAreaFilled(false);
        aboutButton.setFocusPainted(false);
        aboutButton.setOpaque(false);
        JPanel parentPanel = new JPanel(new BorderLayout());
        JPanel buttonContaeinerPanel1 = new JPanel(new BorderLayout());
        aboutButton.addActionListener(new ActionListener() {
            boolean aboutButtonClicked = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a panel to hold the image labels
                JPanel imagePanel = new JPanel();
                // Remove all components from the btnPanel
                Component[] c1 = btnPanel.getComponents();
                btnPanel.removeAll();
                btnPanel.revalidate();
                btnPanel.repaint();
                Component[] c2 = searchContactPanel.getComponents();
                searchContactPanel.removeAll();
                searchContactPanel.revalidate();
                searchContactPanel.repaint();

                parentPanel.remove(contentPanel);
                parentPanel.revalidate();
                parentPanel.repaint();
                if (!aboutButtonClicked) {
                    String aboutButtonIconPath = "images/clickedAbout.png";
                    aboutButton.setIcon(new ImageIcon(aboutButtonIconPath));

                    String image1Path = "images/aboutContactApp.png";
                    ImageIcon image1 = new ImageIcon(image1Path);

                    // Create JLabel components to display the images
                    JLabel label1 = new JLabel(image1);
                    // Set the layout for the frame (assuming a BorderLayout)
                    parentPanel.setLayout(new BorderLayout());


                    // Add the image labels to the imagePanel
                    imagePanel.add(label1);

                    // Set the background color for the imagePanel
                    imagePanel.setBackground(Color.decode("#2E0B43"));

                    // Add the imagePanel to the frame
                    parentPanel.add(imagePanel, BorderLayout.CENTER);


                    aboutButtonClicked = true;
                } else {

                    String iconPath = "images/about.png";
                    aboutButton.setIcon(new ImageIcon(iconPath));
                    dispose();
                    new UI();
                    aboutButtonClicked = false;
                }

            }
            });



        buttonPanelos.add(aboutButton);

// Create the "Report" button
        JButton reportButton = new JButton();
        reportButton.setBackground(Color.decode("#DDE6F0"));
        reportButton.setForeground(Color.decode("#4B7FA4"));

        String iconPath1 = "images/report.png";
        reportButton.setIcon(new ImageIcon(iconPath1));
        reportButton.setBorderPainted(false);
        reportButton.setContentAreaFilled(false);
        reportButton.setFocusPainted(false);
        reportButton.setOpaque(false);
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String developerEmail = "benabbou.osama@gmail.com"; // Replace with the developer's email address

                Properties properties = new Properties();
                try (InputStream inputStream = getClass().getResourceAsStream("/report.properties")) {
                    properties.load(inputStream);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to read properties file: " + ex.getMessage());
                    return;
                }

                String senderEmail = properties.getProperty("senderEmail");
                String description = properties.getProperty("description");
                final String password = properties.getProperty("password");

                // Create properties for the mail server
                Properties mailProperties = new Properties();
                mailProperties.put("mail.smtp.auth", "true");
                mailProperties.put("mail.smtp.starttls.enable", "true");
                mailProperties.put("mail.smtp.host", "smtp.gmail.com");
                mailProperties.put("mail.smtp.port", "587");

                class EmailAuthenticator extends Authenticator {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, password);
                    }
                }


                Session session = Session.getInstance(mailProperties, new EmailAuthenticator());


                try {
                    // Create a new email message
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(developerEmail));
                    message.setSubject("Bug Report");
                    message.setText(description);

                    // Send the email
                    Transport.send(message);

                    JOptionPane.showMessageDialog(null, "Bug report sent successfully!");
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to send bug report: " + ex.getMessage());
                }
            }
        });
        buttonPanelos.add(reportButton);

// Create the "Contact us" button
        JButton rateButton = new JButton();
        rateButton.setBackground(Color.decode("#DDE6F0"));
        rateButton.setForeground(Color.decode("#4B7FA4"));
        String iconPath2 = "images/rating.png";
        rateButton.setIcon(new ImageIcon(iconPath2));
        rateButton.setBorderPainted(false);
        rateButton.setContentAreaFilled(false);
        rateButton.setFocusPainted(false);
        rateButton.setOpaque(false);
        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your code for handling the "Contact us" button click event
            }
        });
        buttonPanelos.add(rateButton);

        // Add the button panel to the button container panel
        buttonContainerPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPanel.add(btnPanel, BorderLayout.AFTER_LAST_LINE);

        // Create a parent panel to hold the content and button panels


        parentPanel.add(contentPanel, BorderLayout.CENTER);

        // Create a separate panel for the button panel
        JPanel buttonContaeinerPanel = new JPanel(new BorderLayout());

        // Add some empty space above the button panel using an empty border
        buttonContaeinerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        buttonContaeinerPanel.setBackground(Color.decode("#2E0B43"));


        // Add some empty space above the button panel using an empty border
        buttonContaeinerPanel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Add the button panel to the button container panel
        buttonContaeinerPanel1.add(buttonPanelos, BorderLayout.CENTER);

        buttonContaeinerPanel1.setBackground(Color.decode("#2E0B43"));

        // Add the button container panel to the parent panel at the bottom
        parentPanel.add(buttonContaeinerPanel1, BorderLayout.AFTER_LAST_LINE);

        // Add the parent panel to the frame
        add(parentPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


}
