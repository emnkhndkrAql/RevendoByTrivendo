package re_vendo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddProductFrame extends JFrame {
    private JTextField txtPName, txtPrice, txtLocation, txtOwner;
    private JComboBox<String> comboCategory;
    private JButton btnSave, btnBack;
    private String currentUserName;

    
    public AddProductFrame(String userName) {
        this.currentUserName = userName;

        setTitle("ReVendo - Add Product");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 255, 240)); // Light Green

        
        JPanel headPanel = new JPanel();
        headPanel.setBackground(new Color(46, 125, 50));
        headPanel.setBounds(0, 0, 450, 70);
        headPanel.setLayout(null);
        add(headPanel);

        JLabel headLabel = new JLabel("SELL YOUR PRODUCT");
        headLabel.setForeground(Color.WHITE);
        headLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headLabel.setBounds(110, 20, 250, 30);
        headPanel.add(headLabel);

        
        addLabel("Product Name:", 90);
        txtPName = addTextField(120);

        
        addLabel("Category:", 175);
        String[] categories = {"Electronics", "Furniture", "Books", "Vehicles", "Clothing", "Others"};
        comboCategory = new JComboBox<>(categories);
        comboCategory.setBounds(50, 205, 350, 35);
        comboCategory.setBackground(Color.WHITE);
        add(comboCategory);

        
        addLabel("Price (BDT):", 260);
        txtPrice = addTextField(290);

        
        addLabel("Your Location:", 345);
        txtLocation = addTextField(375);

        
        addLabel("Owner:", 430);
        txtOwner = new JTextField(currentUserName);
        txtOwner.setBounds(50, 460, 350, 35);
        txtOwner.setEditable(false); 
        txtOwner.setBackground(new Color(220, 220, 220));
        add(txtOwner);

        
        btnSave = new JButton("POST ADVERTISEMENT");
        btnSave.setBounds(50, 515, 220, 40);
        btnSave.setBackground(new Color(46, 125, 50));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(btnSave);

        
        btnBack = new JButton("CANCEL");
        btnBack.setBounds(280, 515, 120, 40);
        btnBack.setBackground(Color.WHITE);
        add(btnBack);

       

        btnSave.addActionListener(e -> saveToDatabase());

        btnBack.addActionListener(e -> {
            new HomeFrame(currentUserName).setVisible(true);
            dispose();
        });
    }

    private void saveToDatabase() {
        String name = txtPName.getText();
        String cat = comboCategory.getSelectedItem().toString();
        String priceStr = txtPrice.getText();
        String loc = txtLocation.getText();
        String owner = txtOwner.getText();

       
        if (name.isEmpty() || priceStr.isEmpty() || loc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection conn = javaconnet.connectDB()) {
            String sql = "INSERT INTO product (product_name, category, price, owner, location, status) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, cat);
            pst.setDouble(3, Double.parseDouble(priceStr));
            pst.setString(4, owner);
            pst.setString(5, loc);
            pst.setString(6, "Available"); 

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product Posted Successfully!");
            
     
            new HomeFrame(currentUserName).setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price (Number only)!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

   
    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 200, 25);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(label);
    }

    private JTextField addTextField(int y) {
        JTextField field = new JTextField();
        field.setBounds(50, y, 350, 35);
        add(field);
        return field;
    }
}
