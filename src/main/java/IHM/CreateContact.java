package IHM;

import bll.BusinessLogicException;
import bo.Contact;
import dao.ContactDao;
import dao.DataBaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateContact extends JFrame {
    private JLabel nomLabel;
    private JTextField nomField;
    private JLabel prenomLabel;
    private JTextField prenomField;
    private JLabel telephone1Label;
    private JTextField telephone1Field;
    private JLabel telephone2Label;
    private JTextField telephone2Field;
    private JLabel adresseLabel;
    private JTextField adresseField;
    private JLabel emailPersoLabel;
    private JTextField emailPersoField;
    private JLabel emailProLabel;
    private JTextField emailProField;
    private JLabel genreLabel;
    private JComboBox<String> genreComboBox;
    private JButton createButton;

    public CreateContact() {
        // Create the frame without the default title bar
        setUndecorated(true);
        setSize(800, 600);

        // Create the custom title bar panel with the specified color
        Color titleBarColor = Color.decode("#2e0b43");
        JPanel titleBarPanel = new JPanel();
        titleBarPanel.setBackground(titleBarColor);
        titleBarPanel.setPreferredSize(new Dimension(getWidth(), 30));
        titleBarPanel.setLayout(new BorderLayout());

        // Create the title label and add it to the title bar panel
        JLabel titleLabel = new JLabel();
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBarPanel.add(titleLabel, BorderLayout.CENTER);

        // Create the content panel with the background image
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String imagePath = "images/template.png";
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(titleBarPanel, BorderLayout.NORTH);

        // Add form components for creating a contact
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        nomLabel = new JLabel("Nom:");
        nomField = new JTextField(20);
        prenomLabel = new JLabel("Prénom:");
        prenomField = new JTextField(20);
        telephone1Label = new JLabel("Téléphone 1:");
        telephone1Field = new JTextField(20);

        telephone2Label = new JLabel("Téléphone 2:");
        telephone2Field = new JTextField(20);
        adresseLabel = new JLabel("Adresse:");
        adresseField = new JTextField(20);
        emailPersoLabel = new JLabel("Email Personnel:");
        emailPersoField = new JTextField(20);
        emailProLabel = new JLabel("Email Professionnel:");
        emailProField = new JTextField(20);
        genreLabel = new JLabel("Genre:");
        String[] genreOptions = {"Male", "Female"};
        genreComboBox = new JComboBox<>(genreOptions);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(prenomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(telephone1Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(telephone1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(telephone2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(telephone2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(adresseLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(adresseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(emailPersoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(emailPersoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(emailProLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(emailProField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(genreLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(genreComboBox, gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);

        JButton previousBtn = new JButton();
        String iconPath = "images/prevBtn.png";
        previousBtn.setIcon(new ImageIcon(iconPath));
        previousBtn.setBackground(titleBarColor);
        previousBtn.setFocusable(false);
        previousBtn.setBorderPainted(false);
        previousBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// Set hover icon
        String hoverIconPath = "images/hoverPrevBtn.png";
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                previousBtn.setIcon(new ImageIcon(hoverIconPath));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                String iconPath = "images/previousBtn.png";
                previousBtn.setIcon(new ImageIcon(iconPath));
            }
        });

        titleBarPanel.add(previousBtn, BorderLayout.WEST); // to update for a better position

        // Create the create button
        createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String telephone1 = telephone1Field.getText();
                    String telephone2 = telephone2Field.getText();
                    String adresse = adresseField.getText();
                    String emailPerso = emailPersoField.getText();
                    String emailPro = emailProField.getText();
                    boolean genre = genreComboBox.getSelectedItem().equals("Male");
                    String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                    if(!emailPerso.equals("")){
                        boolean isMatch = emailPerso.matches(pattern);
                        if( !isMatch){
                            JOptionPane.showMessageDialog(null, "L'adresse e-mail personnelle est invalide ! ","Erreur",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if(!emailPro.equals("")){
                        boolean isMatch = emailPro.matches(pattern);
                        if( !isMatch){
                            JOptionPane.showMessageDialog(null, "L'adresse e-mail professionnelle est invalide ! " ,"Erreur",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Create a Contact object with the entered data
                    Contact contact = new Contact(nom, prenom, telephone1, telephone2, adresse, emailPerso, emailPro, genre);

                    // Call the ContactDao's create method to save the contact
                    ContactDao contactDao = new ContactDao();
                    try {
                        contactDao.create(contact);
                        JOptionPane.showMessageDialog(null, "Contact created successfully");
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Error creating contact: " + ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        previousBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();

                // Close all windows except the new one
                for (Window window : windows) {
                    if (window != null && window.isVisible() && !(window instanceof UI)) {
                        window.dispose();
                    }
                }
                new UI();

            }
        });

        // Add the create button to the content panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(createButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Change the background and foreground color of the createButton
        createButton.setBackground(Color.decode("#BDA69E"));
        createButton.setForeground(Color.decode("#2E0B43"));

// Change the background and foreground color of the text fields
        // Change the background and foreground colors of the text fields
        nomField.setBackground(Color.decode("#BDA69E"));
        nomField.setForeground(Color.decode("#430400"));

        prenomField.setBackground(Color.decode("#BDA69E"));
        prenomField.setForeground(Color.decode("#430400"));

        telephone1Field.setBackground(Color.decode("#BDA69E"));
        telephone1Field.setForeground(Color.decode("#430400"));

        telephone2Field.setBackground(Color.decode("#BDA69E"));
        telephone2Field.setForeground(Color.decode("#430400"));

        adresseField.setBackground(Color.decode("#BDA69E"));
        adresseField.setForeground(Color.decode("#430400"));

        emailPersoField.setBackground(Color.decode("#BDA69E"));
        emailPersoField.setForeground(Color.decode("#430400"));

        emailProField.setBackground(Color.decode("#BDA69E"));
        emailProField.setForeground(Color.decode("#430400"));

        // Change the background and foreground colors of the combo box
        genreComboBox.setBackground(Color.decode("#BDA69E"));
        genreComboBox.setForeground(Color.decode("#430400"));


// Change the background and foreground color of the genreCheckBox
        // Change the color and font style of the field labels
        nomLabel.setForeground(Color.decode("#B3A7B8"));
        nomLabel.setFont(nomLabel.getFont().deriveFont(Font.BOLD));

        prenomLabel.setForeground(Color.decode("#B3A7B8"));
        prenomLabel.setFont(prenomLabel.getFont().deriveFont(Font.BOLD));

        telephone1Label.setForeground(Color.decode("#B3A7B8"));
        telephone1Label.setFont(telephone1Label.getFont().deriveFont(Font.BOLD));

        telephone2Label.setForeground(Color.decode("#B3A7B8"));
        telephone2Label.setFont(telephone2Label.getFont().deriveFont(Font.BOLD));

        adresseLabel.setForeground(Color.decode("#B3A7B8"));
        adresseLabel.setFont(adresseLabel.getFont().deriveFont(Font.BOLD));

        emailPersoLabel.setForeground(Color.decode("#B3A7B8"));
        emailPersoLabel.setFont(emailPersoLabel.getFont().deriveFont(Font.BOLD));

        emailProLabel.setForeground(Color.decode("#B3A7B8"));
        emailProLabel.setFont(emailProLabel.getFont().deriveFont(Font.BOLD));

        genreLabel.setForeground(Color.decode("#B3A7B8"));
        genreLabel.setFont(genreLabel.getFont().deriveFont(Font.BOLD));


    }
    private boolean validateFields() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || telephone1Field.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a value for Nom, Prénom, and Téléphone 1","Erreur",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}


