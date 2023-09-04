package IHM;

import bll.BusinessLogicException;
import bo.Group;
import dao.DataBaseException;
import dao.GroupDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SuppressionGroupe extends JFrame {
    private JTextField nameField;
    private JButton deleteButton;
    private JButton previousButton;
    private GroupDao groupDao;

    public SuppressionGroupe() {
        groupDao = new GroupDao();
        setUndecorated(true);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#2e0b43"));

        Color titleBarColor = Color.decode("#2e0b43");
        JPanel titleBarPanel = new JPanel();
        titleBarPanel.setBackground(titleBarColor);
        titleBarPanel.setPreferredSize(new Dimension(getWidth(), 30));
        titleBarPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        deleteButton = new JButton();
        deleteButton.setText("Delete");
        deleteButton.setBackground(Color.decode("#2e0b43"));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set hover effect
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setBackground(Color.decode("#BDA69E"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setBackground(Color.decode("#2e0b43"));
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                try {
                    supprimerGroupe(name); // Call the delete group method
                    JOptionPane.showMessageDialog(null, "Group deleted successfully");
                    nameField.setText(""); // Clear the text field after successful deletion
                } catch (BusinessLogicException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (DataBaseException ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred while deleting the group", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        headerPanel.add(deleteButton, BorderLayout.EAST);

        JButton previousBtn = new JButton();
        String iconPath = "images/prevBtn.png";
        previousBtn.setIcon(new ImageIcon(iconPath));
        previousBtn.setBackground(titleBarColor);
        previousBtn.setFocusable(false);
        previousBtn.setBorderPainted(false);
        previousBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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

        add(titleBarPanel, BorderLayout.NORTH);
        add(headerPanel, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void supprimerGroupe(String name) throws BusinessLogicException, DataBaseException {
        Group grp = groupDao.findGrp(name);
        if (grp == null)
            throw new BusinessLogicException("There is no group with this name!");
        groupDao.deleteGrp(grp);
    }



}
