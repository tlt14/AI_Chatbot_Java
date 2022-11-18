package GUI;

import Helper.Handle;
import Helper.Security;
import Security.RSA.RSAUtil;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    private String AESKey ;

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
        AESKey = String.valueOf(new Date().getTime());
        try {
            Socket socket = new Socket(getIpServer(), 5000);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            RSAUtil rsaUtil =new RSAUtil();
            PublicKey publicKeyFromServer = rsaUtil.convertStringToPublicKey(in.readLine());
            Security.sendAESKeyToServer(out,rsaUtil.Encrypt(AESKey,publicKeyFromServer));

            Send send = new Send(socket, out,sendButton,textArea1,textField1,AESKey,sb);
            Receive recv = new Receive(socket, in,textArea1,sb);
            ExecutorService excutor = Executors.newFixedThreadPool(2);
            excutor.execute(send);
            excutor.execute(recv);

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
    }

    public String getIpServer() throws IOException {
        String api = "https://api-generator.retool.com/ZvkfeX/data/1"; // Ghi vào dòng 1 trong DB
        Document doc = Jsoup.connect(api)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type", "application/json")
                .method(Connection.Method.GET).execute().parse();
        JSONObject jsonObject = new JSONObject(doc.text());
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("ip"));
        return (jsonObject.get("ip").toString());
    }
    public static void main(String[] args) {
        new ClientForm(null,"tlt14@gmail.com");
    }

}
class Send implements Runnable{

    /**
     *
     */
    private Socket socket;
    private BufferedWriter out;
    JButton btn;
    JTextArea textArea;
    JTextField jTextField;
    String AESKey;
    JScrollBar sb;
    public Send(Socket s, BufferedWriter o){
        this.socket = s;
        this.out = o;
    }

    public Send(Socket socket, BufferedWriter out, JButton btn, JTextArea textArea, JTextField jTextField, String AESKey, JScrollBar sb) {
        this.socket = socket;
        this.out = out;
        this.btn = btn;
        this.textArea = textArea;
        this.jTextField = jTextField;
        this.AESKey = AESKey;
        this.sb = sb;
    }

    public void run (){
        try{
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String mess = jTextField.getText();
                    System.out.println(mess);
                    textArea.append("Me: "+mess+"\n");
                    sb.setValue(sb.getMaximum());
                    jTextField.setText("");
                    if(!mess.equals("")){
                        try {
                            Security.sendMesssage(out,mess,AESKey);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }catch(Exception e){
            try {
                this.socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
    }
}
class Receive implements Runnable{
    private Socket socket;
    private BufferedReader in;
    JTextArea textArea1;
JScrollBar sb;
    public Receive(Socket socket, BufferedReader in, JTextArea textArea1,JScrollBar sb) {
        this.socket = socket;
        this.in = in;
        this.textArea1 = textArea1;
        this.sb =sb;
    }

    public void run(){
        try {
            while(true){
                Handle handle = new Handle();
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
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}