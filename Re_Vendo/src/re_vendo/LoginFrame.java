package re_vendo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginBtn, goToSignupBtn;

    public LoginFrame() {
        setTitle("ReVendo");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);


        JPanel greenPanel = new JPanel();
        greenPanel.setBackground(new Color(46, 125, 50));
        greenPanel.setBounds(0, 0, 400, 100);
        greenPanel.setLayout(null);
        add(greenPanel);

        JLabel title = new JLabel("ReVendo Login");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(100, 35, 200, 30);
        greenPanel.add(title);

        
        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setBounds(50, 150, 300, 25);
        add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(50, 180, 300, 40);
        add(emailField);

      
        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(50, 230, 300, 25);
        add(lblPass);

        passField = new JPasswordField();
        passField.setBounds(50, 260, 300, 40);
        add(passField);

        
        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(50, 330, 300, 45);
        loginBtn.setBackground(new Color(46, 125, 50));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(loginBtn);

        
        goToSignupBtn = new JButton("Don't have an account? Sign Up");
        goToSignupBtn.setBounds(50, 390, 300, 30);
        goToSignupBtn.setContentAreaFilled(false);
        goToSignupBtn.setBorderPainted(false);
        goToSignupBtn.setForeground(new Color(46, 125, 50));
        add(goToSignupBtn);

      
        
        loginBtn.addActionListener(e -> performLogin());

        goToSignupBtn.addActionListener(e -> {
            new SignupFrame().setVisible(true);
            dispose();
        });
    }

    private void performLogin() {
        String email = emailField.getText();
        String pass = new String(passField.getPassword());

        try (Connection conn = javaconnet.connectDB()) {
            
            String sql = "SELECT * FROM credential WHERE email=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
              
                String name = rs.getString("name");
                
                JOptionPane.showMessageDialog(this, "Welcome, " + name);
                
                
                //new HomeFrame(name).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    
    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}