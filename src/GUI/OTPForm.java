package GUI;

import Helper.Security;
import Helper.SendEmail;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OTPForm extends JFrame{
    private JTextField textField1;
    private JButton submitButton;
    private JPanel OTPpanel;
    private JButton sendOTPButton;

    private String otp;
    public boolean check=false;
    public OTPForm(JFrame parent,String email){
        setTitle("OTP");
        setContentPane(OTPpanel);
        setMinimumSize(new Dimension(300,200));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        sendOTP(email);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField1.getText().trim();
                checkOTP(email,input,parent);
            }
        });
        sendOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendOTP(email);
            }
        });
    }


    private void checkOTP(String email,String input,JFrame parent) {
        if (input.equals(otp)) {
            check=true;
            JOptionPane.showMessageDialog(parent, "Đăng nhập thành công");
            dispose();
            parent.dispose();
            new ClientForm(null, email);
        } else {
            JOptionPane.showMessageDialog(null, "Mã otp sai rồi");
        }
    }
    public void sendOTP(String email){
        long start, end;
        otp = String.valueOf(Security.OTP(6));
        JSONObject res = new SendEmail().result(email, otp);
        start = System.currentTimeMillis();
        if (res.getBoolean("success")) {
            JOptionPane.showMessageDialog(null, "mã OTP được gửi tới gmail của  ");
        }

        end = System.currentTimeMillis();
        if (end - start > 90000) {
            otp = String.valueOf(Security.OTP(6));
        }
    }
}
