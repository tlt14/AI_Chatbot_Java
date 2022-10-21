package GUI;

import BLL.UserBLL;
import DTO.User;
import Helper.HashPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JPanel RegisterPanel;
    private JPasswordField passwordField2;

    public RegisterForm(JFrame parent){
        setTitle("REGISTER");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(450,474));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText().trim();
                String confirmPassword = new String(passwordField2.getPassword());
                User user = new User(username, HashPass.Hash(confirmPassword));
                if(new UserBLL().InsertUser(user)){
                    JOptionPane.showMessageDialog(null,"Đăng ký thành công");
                    dispose();
                    new LoginForm(null);
                }else{
                    JOptionPane.showMessageDialog(null,"Đăng ký thất bại","Erorr",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        new RegisterForm(null);
    }
}
