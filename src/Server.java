import Security.RSA.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;


public class Server {
    public static int port =5000;

    public static ServerSocket server = null;

    public static void main(String[] args) throws IOException {
        Socket socket1 =new Socket("8.8.8.8",443);
        System.out.println(socket1.getLocalAddress().toString().substring(1));
        String localIP=socket1.getLocalAddress().toString().substring(1);
        String api = "https://api-generator.retool.com/ZvkfeX/data/1"; // Ghi vào dòng 1 trong DB
        String jsonData = "{\"ip\":\"" + localIP + "\"}";
        Jsoup.connect(api)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type", "application/json")
                .requestBody(jsonData)
                .method(Connection.Method.PUT).execute();
        ExecutorService executorService = newFixedThreadPool(200);
        try {
            server =new ServerSocket(port);
            System.out.println("server  start port :"+port);
            System.out.println("Waiting .....");

            generateKey RSA =new generateKey();
            while (true){
                Socket socket = server.accept();
                executorService.execute(new Worker(socket,RSA.getPublicKey(), RSA.getPrivateKey()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
