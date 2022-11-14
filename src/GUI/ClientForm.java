package GUI;

import Helper.Handle;
import Helper.Security;
import Security.RSA.RSAUtil;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


public class ClientForm extends JFrame{
    private final BufferedWriter out;
    private final BufferedReader in;

    private JTextArea textArea1;
    private JPanel header;
    private JTextField textField1;
    private JButton sendButton;
    private JPanel ClientPanel;
    private JLabel Username;
    private JScrollPane scrollPane;

    private String AESKey = "123456789";

    public ClientForm(JFrame frame, @NotNull String email){
        JScrollBar sb = scrollPane.getVerticalScrollBar();
        Username.setText(email);
        getRootPane().setDefaultButton(sendButton);
        setTitle("Client");
        setContentPane(ClientPanel);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        Handle handle = new Handle();

        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            RSAUtil rsaUtil =new RSAUtil();
            PublicKey publicKeyFromServer = rsaUtil.convertStringToPublicKey(in.readLine());
            Security.sendAESKeyToServer(out,rsaUtil.Encrypt(AESKey,publicKeyFromServer));


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        sendButton.addActionListener(e -> {
            try {
                String mess = textField1.getText();
                textArea1.append("Me: "+mess+"\n");
                textField1.setText("");
                if(!mess.equals("")){
                    Security.sendMesssage(out,mess,AESKey);
                }
                JSONObject res ;
                try{
                    res = new JSONObject(in.readLine());
//                    System.out.println(res);
                }catch (Exception exception){
                    res = new JSONObject();
                    res.put("result","");
                }
                if(res.getString("result").equals("whois")){
                    handle.Whois(textArea1,res);
                }else if(res.getString("result").equals("infoip")){
                    handle.InfoIP(textArea1,res);
                }else if(res.getString("result").equals("weather")){
                    handle.Weather(textArea1,res);
                }else if(res.getString("result").equals("md5")){
                    handle.Md5(textArea1,res);
                }else if(res.getString("result").trim().equals("change")) {
                    handle.ChangeMoney(textArea1, res);
                }else if(res.getString("result").equals("scan")){
                    do{
                        handle.Scan(textArea1,res);
                        try{
                            res = new JSONObject(in.readLine());
                            System.out.println(res);
                        }catch (Exception es){
                            System.out.println(in.readLine());
                        }
                    }while (!res.getString("data").equals("finish"));
                    handle.Scan(textArea1,res);

                }else{
                    handle.ChatBOT(textArea1,res);
                }
                //set scroll panel bottom
                sb.setValue(sb.getMaximum());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {
        new ClientForm(null,"tlt14@gmail.com");
    }
}
