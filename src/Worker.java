import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Helper.Security;
import Security.RSA.RSAUtil;
import Server.Whois;
import Server.getInfoIP;
import Server.Weather;
import Server.MD5;
import Server.APIchat;
import Server.PlayMusic;
import Server.ScanPort;
import Server.ChangeMoney;
import org.json.JSONObject;

public class Worker implements Runnable {
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in ;
    private ArrayList<String>arr ;
    PublicKey publicKey;
    PrivateKey privateKey;
    public Worker(Socket s,PublicKey publicKey,PrivateKey privateKey) throws IOException{
        this.socket = s;
        this.publicKey= publicKey;
        this.privateKey = privateKey;
        in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        arr= new ArrayList<>();
        arr.add("whois");
        arr.add("weather");
        arr.add("ip");
        arr.add("enc");
        arr.add("dec");
        arr.add("change");
        arr.add("scan");
        arr.add("play");
        arr.add("stop");
    }

    @Override
    public void run() {
        System.out.println("client " + socket.toString() + " accepted");
        //Send public key to client
        Security.sendPublicKey(publicKey,out);
//        GET AES key
        String aesKeyEncrypt = Security.getAESKeyFromCLient(in);
        String aesKey="";

        try {
            aesKey = new RSAUtil().Decrypt(aesKeyEncrypt,privateKey);
            System.out.println("AES mã hóa: "+aesKeyEncrypt);
            System.out.println("AES key giải mã:"+ aesKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {

            while (true) {
                String input ="";
                try{
                    input = Security.receivedMessage(in,aesKey);
                }catch (Exception e){
                }
                if (input.equals("bye")) break;

                StringTokenizer str = new StringTokenizer(input,"-");
                JSONObject req = new JSONObject();
                if(str.hasMoreTokens()){
                    String s= str.nextToken().trim();
                    if(!arr.contains(s)){
                        req.put("key","chatbot");
                        req.put("mess",s);
                    }else {
                        try {
                            req.put("key",s);
                            req.put("mess",str.nextToken().trim());
                        }catch (Exception e){
                            req.put("mess","");
                        }
                    }

                }


                System.out.println("Server received " + req + " from " + socket.toString());
                String key = req.getString("key");
                switch (key) {
                    case "whois" -> {
                        String mess = req.getString("mess");
                        String res = new Whois().result(mess);
                        System.out.println(res);
                        out.write(res);
                        out.newLine();
                        out.flush();
                    }
                    case "ip" -> {
                        String domain = req.getString("mess");
                            JSONObject res = new getInfoIP().result(domain);
                            System.out.println(res);
                            out.write(res.toString());
                        out.newLine();
                        out.flush();
                    }
                    case "enc" ->{
                        String mess = req.getString("mess");
                        JSONObject res = new MD5().Hash(mess);
                        out.write(res.toString());
                        out.newLine();
                        out.flush();
                    }
                    case "dec"->{
                        String mess = req.getString("mess");
                        JSONObject res = new MD5().Decrypted(mess);
                        out.write(res.toString());
                        out.newLine();
                        out.flush();
                    }
                    case "weather" -> {
                        String domain = req.getString("mess");
                        if (domain.equals("")) {
                            out.write("Nhập vào tỉnh muốn xem thời tiết: ");
                        } else {
                            JSONObject data = new Weather().result(domain);
                            JSONObject res = new JSONObject();
                            res.put("data", data);
                            res.put("result", "weather");
                            out.write(res.toString());
                        }
                        out.newLine();
                        out.flush();
                    }
                    case "change"->{
                        String mess = req.getString("mess");
                        System.out.println(mess);
                        if(mess.equals("")){
                            out.write(String.valueOf(new ChangeMoney().result(null,null, Long.parseLong("0"))));
                        }else{
                            try {
                                StringTokenizer stringTokenizer = new StringTokenizer(mess,";");
                                String money = stringTokenizer.nextToken();
                                String from = stringTokenizer.nextToken();
                                String to = stringTokenizer.nextToken();
                                System.out.println(new ChangeMoney().result(from,to, Long.parseLong(money)));
                                out.write(new ChangeMoney().result(from,to, Long.parseLong(money)).toString());
                            }catch (Exception e){
                                out.write(String.valueOf(new ChangeMoney().result(null,null, Long.parseLong("0"))));
                            }

                        }
                        out.newLine();
                        out.flush();
                    }
                    case "scan"->{
                        String mess = req.getString("mess");
                        new ScanPort().runPortScan(mess,out);
                    }
                    case "play"->{
                        String song = req.getString("mess");
                        new PlayMusic().play(song);
                    }
                    case "stop"->//                        String song = req.getString("mess");
                            new PlayMusic().stop();
                    default -> {
                        JSONObject res = new JSONObject();
                        String data = new APIchat().result(req.getString("mess"));
                        res.put("data", data);
                        res.put("result", "chatbot");
                        System.out.println(res);
                        out.write(res.toString());
                        out.newLine();
                        out.flush();
                    }
                }

            }
            System.out.println("Closed socket for client " + socket.toString());
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
