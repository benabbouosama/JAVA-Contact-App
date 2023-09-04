package IHM;

import dao.DataBaseException;
import dao.GroupDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AjoutGroupe extends JFrame {
    private JTextField nameField;
    private JButton createButton;
    private JButton previousButton;
    private GroupDao groupDao;

    public AjoutGroupe() {
        groupDao = new GroupDao();
        setUndecorated(true);
        setTitle("Create Group");
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

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField();
        createButton = new JButton("Create Group");
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
                try {
                    groupDao.createGrp(name);
                    JOptionPane.showMessageDialog(null, "Group created successfully");
                    nameField.setText(""); // Clear the text field after successful creation
                } catch (DataBaseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

