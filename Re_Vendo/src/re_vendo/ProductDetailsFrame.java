package re_vendo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProductDetailsFrame extends JFrame {
    public ProductDetailsFrame(int id, String name, String cat, double price, String loc, String owner, String status, String buyer) {
        setTitle("Product Details - " + name);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel pnl = new JPanel();
        pnl.setBackground(new Color(46, 125, 50));
        pnl.setBounds(0, 0, 400, 60);
        add(pnl);
        JLabel lblTitle = new JLabel("Product Information");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnl.add(lblTitle);

       
        int startY = 80;
        addInfo("Product Name:", name, startY);
        addInfo("Category:", cat, startY + 40);
        addInfo("Price:", "BDT " + price, startY + 80);
        addInfo("Location:", loc, startY + 120);
        addInfo("Owner:", owner, startY + 160);
        addInfo("Status:", status, startY + 200);

        
        JButton btnBuy = new JButton("BUY NOW");
        btnBuy.setBounds(50, 350, 300, 45);
        btnBuy.setBackground(new Color(34, 139, 34));
        btnBuy.setForeground(Color.WHITE);
        btnBuy.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(btnBuy);

        JButton btnBack = new JButton("Back to Home");
        btnBack.setBounds(50, 410, 300, 30);
        add(btnBack);

        
        btnBuy.addActionListener(e -> {
            if (owner.equals(buyer)) {
                JOptionPane.showMessageDialog(this, "You cannot buy your own product!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to buy this?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = javaconnet.connectDB()) {
                    
                    String sql = "UPDATE product SET status = 'Sold' WHERE id = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, id);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Purchase Successful! Contact owner: " + owner);
                    new HomeFrame(buyer).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnBack.addActionListener(e -> {
            new HomeFrame(buyer).setVisible(true);
            dispose();
        });
    }

    private void addInfo(String key, String val, int y) {
        JLabel k = new JLabel(key);
        k.setBounds(50, y, 120, 20);
        k.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(k);

        JLabel v = new JLabel(val);
        v.setBounds(180, y, 200, 20);
        add(v);
    }
}
