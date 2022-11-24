package GUI;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterForm extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JPanel RegisterPanel;
    private JPasswordField passwordField2;
    private JButton goToLoginFormButton;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public boolean checkPassword(String pass,String confirmPass){
        return pass.equals(confirmPass);
    }
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
            String confirmPass = new String(passwordField1.getPassword());
            Document doc;
            JSONObject res=null;
            if(validate(email)){
                if(checkPassword(password,confirmPass)){
                    if(new OTPForm(this, email).check){
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
                        if(!res.getString("error").equals("false")){
                            JOptionPane.showMessageDialog(parent,res.getString("error"),"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(parent,"Hai mật khẩu không trùng nhau ","Error",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(parent,"Vui lòng nhập email đúng","Error",JOptionPane.ERROR_MESSAGE);
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
