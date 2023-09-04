package IHM;

import bll.AppManager;
import bll.BusinessLogicException;
import bo.Contact;
import bo.Group;
import dao.DataBaseException;
import dao.GroupDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GroupNom extends JFrame {
    private AppManager appManager = new AppManager();
    private JTextField nameField;
    private JButton createButton;
    private JButton previousButton;
    private GroupDao groupDao;

    public GroupNom() {
        groupDao = new GroupDao();
        setUndecorated(true);
        setTitle("Chercher un Groupe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#2e0b43"));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);

        previousButton = new JButton();
        String iconPath = "images/prevBtn.png";
        previousButton.setIcon(new ImageIcon(iconPath));
        previousButton.setBackground(Color.decode("#2e0b43"));
        previousButton.setFocusable(false);
        previousButton.setBorderPainted(false);
        previousButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set hover icon
        String hoverIconPath = "images/hoverPrevBtn.png";
        previousButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                previousButton.setIcon(new ImageIcon(hoverIconPath));            }

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

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField();
        createButton = new JButton("Chercher le groupe ");
        createButton.setBackground(Color.decode("#BDA69E"));
        createButton.setForeground(Color.decode("#2E0B43"));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (name.equals("")){
                    JOptionPane.showMessageDialog(null, "empty field","Erreur",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Group myGrp = null;
                try {
                    myGrp= appManager.rechercherGroupe(name);
                } catch (DataBaseException ex) {
                    throw new RuntimeException(ex);
                } catch (BusinessLogicException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    List<Contact> contactList= new ArrayList<>();
                    for (Contact i : appManager.afficherContactsGrp(myGrp.getNom())){
                        contactList.add(i);
                    }
                    if (!contactList.isEmpty()) {
                        Window[] windows = Window.getWindows();
                        for (Window window : windows) {
                            if (window != null && window.isVisible() && !(window instanceof ContactTableFrame)) {
                                window.dispose();
                            }
                        }
                    }
                    GrpContactsFrame grpContactsFrame = new GrpContactsFrame(contactList);
                    grpContactsFrame.setVisible(true);
                } catch (DataBaseException ex) {
                    ex.printStackTrace();
                } catch (BusinessLogicException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(new JLabel());
        formPanel.add(createButton);

        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}

