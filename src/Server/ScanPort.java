package Server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.concurrent.*;

public class ScanPort
{
    public void result(){
        Socket s;
        for (int i = 40; i <443 ; i++) {
            try {
                s=new Socket();
                s.connect(new InetSocketAddress( "8.8.4.4", i ),200);
                System.out.println(i);
            } catch (Exception e) {

            }
        }


    }
    public static void main(String[] args) {
        new ScanPort().result();
    }
}