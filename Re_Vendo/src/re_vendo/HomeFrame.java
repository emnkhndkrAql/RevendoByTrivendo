package re_vendo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HomeFrame extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private String loggedInUser;

    public HomeFrame(String userName) {
        this.loggedInUser = userName;
        setTitle("ReVendo - Home");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(46, 125, 50));
        topPanel.setPreferredSize(new Dimension(900, 80));
        topPanel.setLayout(null);

        JLabel logo = new JLabel("ReVendo Marketplace");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBounds(20, 20, 300, 30);
        topPanel.add(logo);

        JLabel userLabel = new JLabel("Welcome, " + loggedInUser);
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(700, 25, 150, 20);
        topPanel.add(userLabel);
        add(topPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"ID", "Product Name", "Category", "Price", "Location", "Owner", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        productTable = new JTable(tableModel);
        productTable.setRowHeight(35);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

       
        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = productTable.getSelectedRow();
                if (row != -1) {
                    
                    int id = (int) tableModel.getValueAt(row, 0);
                    String name = (String) tableModel.getValueAt(row, 1);
                    String cat = (String) tableModel.getValueAt(row, 2);
                    double price = (double) tableModel.getValueAt(row, 3);
                    String loc = (String) tableModel.getValueAt(row, 4);
                    String owner = (String) tableModel.getValueAt(row, 5);
                    String status = (String) tableModel.getValueAt(row, 6);

                    new ProductDetailsFrame(id, name, cat, price, loc, owner, status, loggedInUser).setVisible(true);
                    dispose(); 
                }
            }
        });

        
        JPanel bottomPanel = new JPanel();
        JButton btnAdd = new JButton("Post Advertisement");
        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        bottomPanel.add(btnAdd);

        btnAdd.addActionListener(e -> {
            new AddProductFrame(loggedInUser).setVisible(true);
            dispose();
        });

        add(bottomPanel, BorderLayout.SOUTH);
        loadProducts();
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        try (Connection conn = javaconnet.connectDB()) {
            String sql = "SELECT * FROM product WHERE status = 'Available'"; 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("product_name"), rs.getString("category"),
                    rs.getDouble("price"), rs.getString("location"), rs.getString("owner"), rs.getString("status")
                });
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}