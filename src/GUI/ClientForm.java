package GUI;

import DTO.User;
import Helper.Handle;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;


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
    public ClientForm(JFrame frame, @NotNull User user){
        JScrollBar sb = scrollPane.getVerticalScrollBar();
        Username.setText(user.getUsername());
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        sendButton.addActionListener(e -> {
            try {
                String mess = textField1.getText();
                textField1.setText("");
                textArea1.append("\nMe: "+mess+"\n");
                if(!mess.equals("")){
                    out.write(mess);
                    out.newLine();
                    out.flush();
                }
                JSONObject res ;
                try{
                    res = new JSONObject(in.readLine());
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
                }else{
                    handle.ChatBOT(textArea1,res);
                }
                //set scroll panel bottom
                sb.setValue( sb.getMaximum());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {
        new ClientForm(null,new User("thailamtruong","123"));
    }
}
