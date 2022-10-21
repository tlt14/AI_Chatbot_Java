package Server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.concurrent.*;

public class ScanPort
{
    public static Future<Boolean> portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
        return es.submit(new Callable<Boolean>() {
            @Override public Boolean call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
    }
    public static void main(String[] args) {
        Socket s;
        for(int i=1;i<60000;i++){
            try {
                s=new Socket();
                s.connect(new InetSocketAddress( "127.0.0.1", i ),200);
                System.out.println("Port open: "+i);
            } catch (Exception e) {

            }
        }
    }
}