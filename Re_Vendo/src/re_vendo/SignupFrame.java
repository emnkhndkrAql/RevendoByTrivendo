package re_vendo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignupFrame extends JFrame {

    private JTextField txtName, txtEmail, txtAnswer, txtAddress;
    private JPasswordField txtPass;
    private JComboBox<String> comboSQ;
    private JButton btnSignup, btnBack;

    public SignupFrame() {
        setTitle("ReVendo - Sign Up");
        setSize(500, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 255, 240)); 

        
        JLabel head = new JLabel("CREATE ACCOUNT", JLabel.CENTER);
        head.setBounds(0, 20, 500, 40);
        head.setFont(new Font("Segoe UI", Font.BOLD, 26));
        head.setForeground(new Color(34, 139, 34));
        add(head);

        
        addLabel("Name:", 80);
        txtName = addTextField(110);

        
        addLabel("Email:", 165);
        txtEmail = addTextField(195);

        
        addLabel("Password:", 250);
        txtPass = new JPasswordField();
        txtPass.setBounds(50, 280, 400, 35);
        add(txtPass);

        
        addLabel("Security Question:", 335);
        String[] questions = {"Pet Name?", "Birth City?", "Favorite Teacher?", "Childhood Nickname?"};
        comboSQ = new JComboBox<>(questions);
        comboSQ.setBounds(50, 365, 400, 35);
        comboSQ.setBackground(Color.WHITE);
        add(comboSQ);

        
        addLabel("Security Answer:", 420);
        txtAnswer = addTextField(450);

        
        addLabel("Address:", 505);
        txtAddress = addTextField(535);

        
        btnSignup = new JButton("SIGN UP");
        btnSignup.setBounds(50, 600, 190, 45);
        btnSignup.setBackground(new Color(34, 139, 34));
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSignup.setFocusPainted(false);
        btnSignup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSignup);

        
        btnBack = new JButton("BACK");
        btnBack.setBounds(260, 600, 190, 45);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        add(btnBack);

        
        btnSignup.addActionListener(e -> {
            String name = txtName.getText();
            String email = txtEmail.getText();
            String password = new String(txtPass.getPassword());
            String question = comboSQ.getSelectedItem().toString();
            String answer = txtAnswer.getText();
            String address = txtAddress.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || answer.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
            } else {
                try (Connection conn = javaconnet.connectDB()) {
                    String sql = "INSERT INTO credential (name, email, password, security_question, answer, address) VALUES (?,?,?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setString(2, email);
                    pst.setString(3, password);
                    pst.setString(4, question);
                    pst.setString(5, answer);
                    pst.setString(6, address);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registration Successful!");
                    
                    new LoginFrame().setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
                }
            }
        });

        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    
    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 200, 25);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(label);
    }

    private JTextField addTextField(int y) {
        JTextField field = new JTextField();
        field.setBounds(50, y, 400, 35);
        add(field);
        return field;
    }
}