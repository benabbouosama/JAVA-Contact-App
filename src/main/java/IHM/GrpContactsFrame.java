package IHM;
import bo.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GrpContactsFrame extends JFrame {
    private JTable table;

    public GrpContactsFrame(List<Contact> contactList) {
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

        JButton previousBtn = new JButton();
        String iconPath = "images/previousBtn.png";
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

        titleBarPanel.add(previousBtn, BorderLayout.WEST);

        // Create the table model
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // Add column names
        model.addColumn("Nom");
        model.addColumn("Téléphone 1");
        model.addColumn("Téléphone 2");

        // Add data rows
        for (Contact contact : contactList) {
            Object[] rowData = {
                    contact.getNom(),
                    contact.getTelephone1(),
                    contact.getTelephone2(),
            };
            model.addRow(rowData);
        }

        table.setBackground(Color.decode("#2e0b43"));
        table.getTableHeader().setBackground(Color.decode("#2e0b43"));
        table.getTableHeader().setForeground(Color.decode("#3B7DA8"));

        // Set table font and size
        java.awt.Color fontColor = java.awt.Color.decode("#D4E5F5");
        java.awt.Font font = new java.awt.Font("Arial", Font.BOLD, 14);
        table.setFont(font);
        table.setForeground(fontColor);

        // Set table cell alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.decode("#2e0b43"));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the content panel to the frame
        add(contentPanel);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}




