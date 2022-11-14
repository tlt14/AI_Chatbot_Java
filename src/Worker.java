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
            System.out.println(aesKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {

            String input ;
            while (true) {
                input = Security.receivedMessage(in,aesKey);
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
                    }
                    case "ip" -> {
                        String domain = req.getString("mess");
                            JSONObject res = new getInfoIP().result(domain);
                            System.out.println(res);
                            out.write(res.toString());
                    }
                    case "enc" ->{
                        String mess = req.getString("mess");
                        JSONObject res = new MD5().Hash(mess);
                        out.write(res.toString());
                    }
                    case "dec"->{
                        String mess = req.getString("mess");
                        JSONObject res = new MD5().Decrypted(mess);
                        out.write(res.toString());
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
                    }
                    case "scan"->{
                        String mess = req.getString("mess");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result","scan");
                        try{
                            StringTokenizer stringTokenizer = new StringTokenizer(mess,";");
                            String ip = stringTokenizer.nextToken();
                            int x= Integer.parseInt(stringTokenizer.nextToken());
                            int y= Integer.parseInt(stringTokenizer.nextToken());
                            for(int i=x;i<=y;i++) {
                                Socket s;
                                if(i==y){
                                    System.out.println(i);
                                    jsonObject.put("error","");
                                    jsonObject.put("data","finish");
                                    System.out.println(jsonObject);
                                    out.write(jsonObject.toString());
                                    break;
                                }
                                try {
                                    s=new Socket();
                                    s.connect(new InetSocketAddress( ip, i ),200);
                                    System.out.println("PORT OPen "+i);
                                    jsonObject.put("data",String.valueOf(i));
                                    jsonObject.put("error","");
                                    out.write(jsonObject.toString());
                                    out.newLine();
                                    out.flush();
                                } catch (Exception e) {
                                }
                            }
                        }catch (Exception e){
                            jsonObject.put("error","Bạn nhập sai cú pháp");
                            out.write(jsonObject.toString());
                        }
                    }
                    default -> {
                        JSONObject res = new JSONObject();
                        String data = new APIchat().result(req.getString("mess"));
                        res.put("data", data);
                        res.put("result", "chatbot");
                        out.write(res.toString());
                    }
                }
                out.newLine();
                out.flush();
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
