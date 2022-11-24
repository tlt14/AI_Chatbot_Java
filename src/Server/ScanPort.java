package Server;


import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScanPort {
    ExecutorService executorService;
//    public static void main(String[] args) throws InterruptedException, IOException {
//        new ScanPort().runPortScan("8.8.4.4;0;1000",new BufferedWriter(new OutputStreamWriter(System.out)));
//
//    }

    public void runPortScan(String mess,BufferedWriter out) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","scan");
        try{
            StringTokenizer stringTokenizer = new StringTokenizer(mess,";");
            String ip = stringTokenizer.nextToken();
            int x= Integer.parseInt(stringTokenizer.nextToken());
            int y= Integer.parseInt(stringTokenizer.nextToken());
            ConcurrentLinkedQueue openPorts = new ConcurrentLinkedQueue<>();
            executorService = Executors.newFixedThreadPool(15);
            AtomicInteger port = new AtomicInteger(x);
            while (port.get() <= y) {
                final int currentPort = port.getAndIncrement();
//                System.out.println(currentPort);
                executorService.submit(() -> {
                    try {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress(ip, currentPort),200);
                        socket.close();
                        jsonObject.put("data",String.valueOf(currentPort));
                        jsonObject.put("error","");
                        if(currentPort==y){
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("data","finish");
                            jsonObject1.put("error","");
                            jsonObject1.put("result","scan");
                            out.write(jsonObject.toString());
                            out.newLine();
                            out.flush();
                            try {
                                out.write(jsonObject1.toString());
                                out.newLine();
                                out.flush();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }else{
                            out.write(jsonObject.toString());
                            out.newLine();
                            out.flush();
                        }
                    } catch (IOException e) {
                        if(currentPort==y){
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("data","finish");
                            jsonObject1.put("error","");
                            jsonObject1.put("result","scan");
                            try {
                                out.write(jsonObject1.toString());
                                out.newLine();
                                out.flush();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });
            }
            executorService.shutdown();
        }catch (Exception e){
            jsonObject.put("error","Bạn nhập sai cú pháp");
            out.write(jsonObject.toString());
        }
    }

}