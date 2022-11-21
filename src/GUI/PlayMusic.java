package GUI;
import javazoom.jl.player.Player;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
        BufferedInputStream bis ;
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
    static ExecutorService executor;
    static RequestMusic client;
    public PlayMusic()  {
    }
    public static URL getURL(String s) throws IOException {
        Document doc = null;
            doc = Jsoup.connect(ConfigURL.API_MUSIC+s)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
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
//        executor.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        play("tong phu");
        stop();
        System.out.println("stop");
    }
}