package GUI;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
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
                Document doc;
            JSONObject res=null;
                    if(new OTPForm(this, email).check){
//                        try {
//                            Jsoup.connect("http://localhost/api/auth/delete")
//                                    .ignoreHttpErrors(true)
//                                    .ignoreContentType(true)
//                                    .data("email",email)
//                                    .post();
//                        } catch (IOException ex) {
//                            throw new RuntimeException(ex);
//                        }
                        try {
                            doc = Jsoup.connect(ConfigURL.API_REGISTER)
                                    .ignoreHttpErrors(true)
                                    .ignoreContentType(true)
                                    .data("email",email)
                                    .data("password",password)
                                    .post();
                            res = new JSONObject(doc.body().ownText());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                if(res.getString("error").equals("false")){

                    }else{
                        JOptionPane.showMessageDialog(parent,res.getString("error"),"Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
        });
        goToLoginFormButton.addActionListener(e -> {
            new LoginForm(null);
            dispose();
        });
    }

    public static void main(String[] args) {
        new RegisterForm(null);
    }
}
