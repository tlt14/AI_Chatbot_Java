import Security.RSA.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;


public class Server {
    public static int port =5000;

    public static ServerSocket server = null;

    public static void main(String[] args) {
        ExecutorService executorService = newFixedThreadPool(5);
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
