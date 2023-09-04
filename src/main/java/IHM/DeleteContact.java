package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import bll.AppManager;
import bll.BusinessLogicException;
import dao.DataBaseException;

public class DeleteContact extends JFrame {
    private JTextField numTextField;
    private JRadioButton personnelRadioButton;
    private JRadioButton professionnelRadioButton;

    public DeleteContact() {
        setTitle("Supprimer un contact");
        setUndecorated(true);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color titleBarColor = Color.decode("#2e0b43");
        JPanel titleBarPanel = new JPanel();
        titleBarPanel.setBackground(titleBarColor);
        titleBarPanel.setPreferredSize(new Dimension(getWidth(), 30));
        titleBarPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleBarPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String imagePath = "images/template.png";
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Create a panel for the input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(Color.decode("#2E0B43"));
        inputPanel.setForeground(Color.decode("#DAE7F4"));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create labels and text fields for num and type
        JLabel numLabel = new JLabel("Numéro:");
        numLabel.setForeground(Color.decode("#DAE7F4"));
        numTextField = new JTextField();
        numTextField.setBackground(Color.decode("#457FA7"));
        numTextField.setForeground(Color.decode("#DAE7F4"));
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setForeground(Color.decode("#DAE7F4"));
        personnelRadioButton = new JRadioButton("Personnel");
        personnelRadioButton.setBackground(Color.decode("#457FA7"));
        personnelRadioButton.setForeground(Color.decode("#DAE7F4"));
        professionnelRadioButton = new JRadioButton("Professionnel");
        professionnelRadioButton.setBackground(Color.decode("#457FA7"));
        professionnelRadioButton.setForeground(Color.decode("#DAE7F4"));

        // Create a button for deleting the contact
        JButton deleteButton = new JButton("Supprimer");

        // Add radio buttons to a group so that only one can be selected
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(personnelRadioButton);
        buttonGroup.add(professionnelRadioButton);

        // Add components to the input panel
        inputPanel.add(numLabel);
        inputPanel.add(numTextField);
        inputPanel.add(typeLabel);
        inputPanel.add(personnelRadioButton);
        inputPanel.add(new JLabel()); // Empty label for layout purposes
        inputPanel.add(professionnelRadioButton);

        // Add the input panel and delete button to the content panel
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(deleteButton, BorderLayout.SOUTH);


        add(titleBarPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the input fields
                String num = numTextField.getText();
                if(num.equals("")){
                    JOptionPane.showMessageDialog(null, "Remplir la barre de recherche !!");
                    return;
                }
                boolean type = personnelRadioButton.isSelected();
                boolean type2 = professionnelRadioButton.isSelected();
                if(type==false & type2==false){
                    JOptionPane.showMessageDialog(null, "Sélectionner un type de numéro");
                    return;
                }
                AppManager appManager = new AppManager();
                try {
                    // Call the supprimerContact method
                    appManager.supprimerContact(num, type);
                    JOptionPane.showMessageDialog(null, "Le contact a été supprimé avec succès");
                } catch (BusinessLogicException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du contact:\n" + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (DataBaseException ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la suppression du contact",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create the previous button
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
                previousBtn.setIcon(new ImageIcon(hoverIconPath));            }

            @Override
            public void mouseExited(MouseEvent e) {
                String iconPath = "images/prevBtn.png";
                previousBtn.setIcon(new ImageIcon(iconPath));
            }
        });

        // Add action listener to the previous button
        previousBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current frame and go back to the previous UI
                dispose();
                new UI();
            }
        });

        // Add the previous button to the title bar panel
        titleBarPanel.add(previousBtn, BorderLayout.WEST);

        setVisible(true);
    }

}
