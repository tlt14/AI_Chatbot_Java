package GUI;

import Helper.HashPass;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegisterForm extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JPanel RegisterPanel;
    private JPasswordField passwordField2;
    private JButton goToLoginFormButton;

    public RegisterForm(JFrame parent){
        setTitle("REGISTER");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(450,474));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
        signUpButton.addActionListener(e->{
            String email = textField1.getText().trim();
                String password = new String(passwordField2.getPassword());
                Document doc = null ;
                try {
                    doc = Jsoup.connect("http://localhost:4000/api/auth/register")
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
                    if(new OTPForm(this,email).check==false){
                        try {
                            Jsoup.connect("http://localhost/api/auth/delete")
                                    .ignoreHttpErrors(true)
                                    .ignoreContentType(true)
                                    .data("email",email)
                                    .post();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(parent,res.getString("error"),"Error",JOptionPane.ERROR_MESSAGE);
                }
        });
        goToLoginFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm(null);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new RegisterForm(null);
    }
}
