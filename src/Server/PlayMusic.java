package Server;
import javazoom.jl.player.Player;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RequestMusic extends Thread{
    Player player = null;
    URL url;

    public RequestMusic(URL url){
        this.url = url;
    }
    @Override
    public void run() {
        BufferedInputStream bis = null;
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream stream =urlConnection.getInputStream();
            bis = new BufferedInputStream(stream);

            player = new Player(bis);
            player.play();
        } catch (Exception e) {
            System.out.println("mess"+e.getMessage());
        }
    }
}

public class PlayMusic{
    static String search;
    static ExecutorService executor;
    static RequestMusic client;
    public PlayMusic()  {
//        this.search = s;
    }
    public static URL getURL(String s) throws IOException {
        Document doc = Jsoup.connect("http://localhost:4000/searchSong/"+s)
                .ignoreContentType(true)
                .get();
        return new URL("http://localhost:4000/uploads/"+new JSONObject(doc.body().ownText()).getString("data"));
    }

    public static void play(String s) throws IOException {
        executor = Executors.newFixedThreadPool(1);
        client = new RequestMusic(getURL(s));
        executor.execute(client);

    }
    public static void stop(){
        client.player.close();
        executor.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập tên bài hát");
        String song  = sc.nextLine();
        play(song);
        while (true){
            System.out.println("nhap");
            String a ;
            a= sc.nextLine();
            StringTokenizer str=new StringTokenizer(a,";");
            if(a.equals("stop")){
                stop();
            }else if(str.nextToken().equals("play")){
                stop();
                play(str.nextToken());
            } else if (a.equals("bye")) {
                break;
            }
        }

    }
}