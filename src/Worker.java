import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
    public Worker(Socket s) throws IOException{
        this.socket = s;
        in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        arr= new ArrayList<>();
        arr.add("whois");
        arr.add("weather");
        arr.add("ip");
        arr.add("md5");
        arr.add("decrypted");
        arr.add("change");
    }

    @Override
    public void run() {
        System.out.println("client " + socket.toString() + " accepted");
        try {

            String input ;
            while (true) {
                input = in.readLine();
                if (input.equals("bye")) break;
                System.out.println(input);
                StringTokenizer str = new StringTokenizer(input,"-");
                JSONObject req = new JSONObject();
                if(str.hasMoreTokens()){
                    String s= str.nextToken().trim();
                    if(arr.indexOf(s)==-1){
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
                    case "md5" ->{
                        String mess = req.getString("mess");
                        JSONObject res = new MD5().Hash(mess);
                        out.write(res.toString());
                    }
                    case "decrypted"->{
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
