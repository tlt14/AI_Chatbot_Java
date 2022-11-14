package GUI;


import Helper.Security;
import Helper.SendEmail;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginForm extends JFrame{
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel LoginPanel;
    private JButton Register;

    public LoginForm(JFrame parent){
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(300,200));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        loginButton.addActionListener(e -> {
            String email = textField1.getText();
            String password = new String(passwordField1.getPassword());
            Document doc = null ;
            try {
                doc = Jsoup.connect("http://localhost:4000/api/auth/login")
                        .ignoreHttpErrors(true)
                        .ignoreContentType(true)
                        .data("email",email)
                        .data("password",password)
                        .post();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JSONObject res = new JSONObject(doc.body().ownText());
            if(res.getString("error").equals("false")){
//                sendOTP(email,parent);
                new OTPForm(this,email);
            }else{
                JOptionPane.showMessageDialog(parent,res.getString("error"),"Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        Register.addActionListener(e -> {
            new RegisterForm(null);
            dispose();
        });
    }

    public static void main(String[] args) {
        new LoginForm(null);
    }
}
