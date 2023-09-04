package IHM;

import bll.AppManager;
import bll.BusinessLogicException;
import bo.Contact;
import dao.DataBaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AjoutContactAuGroupe extends JFrame {
    private JTextField groupNameField;
    private JTextField contactNumberField;
    private JRadioButton personalRadioButton;
    private JRadioButton professionalRadioButton;
    private JButton addButton;
    private JButton previousButton;

    public AjoutContactAuGroupe() {
        setTitle("Add Contact to Group");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(400, 200);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#2e0b43"));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.decode("#2e0b43")); // Set panel background color

        // Group Name label and field
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameLabel.setForeground(Color.decode("#BDA69E"));
        groupNameField = new JTextField();
        groupNameField.setForeground(Color.decode("#2E0B43"));
        groupNameField.setBackground(Color.decode("#BDA69E"));
        inputPanel.add(groupNameLabel);
        inputPanel.add(groupNameField);
        //inputPanel.setForeground(Color.decode("#2E0B43"));

        // Contact Number label and field
        JLabel contactNumberLabel = new JLabel("Contact Number:");
        contactNumberLabel.setForeground(Color.decode("#BDA69E"));
        contactNumberField = new JTextField();
        contactNumberField.setForeground(Color.decode("#2E0B43"));
        contactNumberField.setBackground(Color.decode("#BDA69E"));
        inputPanel.add(contactNumberLabel);
        inputPanel.add(contactNumberField);

        // Contact Type radio buttons
        JLabel contactTypeLabel = new JLabel("Contact Type:");
        contactTypeLabel.setForeground(Color.decode("#BDA69E"));
        personalRadioButton = new JRadioButton("Personal");
        personalRadioButton.setBackground(Color.decode("#BDA69E"));
        personalRadioButton.setForeground(Color.decode("#2E0B43"));
        professionalRadioButton = new JRadioButton("Professional");
        professionalRadioButton.setBackground(Color.decode("#BDA69E"));
        professionalRadioButton.setForeground(Color.decode("#2E0B43"));

        ButtonGroup contactTypeGroup = new ButtonGroup();
        contactTypeGroup.add(personalRadioButton);
        contactTypeGroup.add(professionalRadioButton);

        JPanel contactTypePanel = new JPanel(new GridLayout(1, 2));
        contactTypePanel.add(personalRadioButton);
        contactTypePanel.add(professionalRadioButton);

        inputPanel.add(contactTypeLabel);
        inputPanel.add(contactTypePanel);

        // Add Button
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AppManager appManager = new AppManager();
                    String groupName = groupNameField.getText();
                    String contactNumber = contactNumberField.getText();
                    boolean isPersonal = personalRadioButton.isSelected();
                    boolean isProfessional = professionalRadioButton.isSelected();
                    if(groupName.equals("") | contactNumber.equals("")){
                        JOptionPane.showMessageDialog(null, "Empty fields","Erreur",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(isPersonal==false & isProfessional==false){
                        JOptionPane.showMessageDialog(null, "Choisir un type de numéro","Erreur",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Contact c = appManager.rechercherContactParNum(contactNumber, isPersonal);
                    appManager.ajoutContactAuGrp(groupName,c);

                } catch (DataBaseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);

                } catch (BusinessLogicException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        previousButton = new JButton();
        String iconPath = "images/prevBtn.png";
        previousButton.setIcon(new ImageIcon(iconPath));
        previousButton.setBackground(Color.decode("#2e0b43"));
        previousButton.setFocusable(false);
        previousButton.setBorderPainted(false);
        previousButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set hover icon
        previousButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                String iconPath = "images/hoverPrevBtn.png";
                previousButton.setIcon(new ImageIcon(iconPath));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                String iconPath = "images/prevBtn.png";
                previousButton.setIcon(new ImageIcon(iconPath));
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new UI(); // Open the previous UI window
            }
        });

        headerPanel.add(previousButton);

        add(headerPanel, BorderLayout.NORTH);
        setLocationRelativeTo(null);

        // Add components to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

}
