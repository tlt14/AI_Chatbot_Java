package GUI;

import BLL.UserBLL;
import DTO.User;
import Helper.HashPass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame{
    private JButton loginButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel LoginPanel;

    public LoginForm(JFrame parent){
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(300,200));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = new String(passwordField1.getPassword());
                User user = new User(username, HashPass.Hash(password));
                if(new UserBLL().Login(user)){
                    JOptionPane.showMessageDialog(parent,"Đăng nhập thành công");
                    dispose();
                    new ClientForm(null,user);

                }else {
                    JOptionPane.showMessageDialog(parent,"Sai tên đăng nhập hoặc mật khẩu","Login Failed",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    public static void main(String[] args) {
        new LoginForm(null);
    }
}
