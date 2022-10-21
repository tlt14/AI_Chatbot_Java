import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class APIchat {
    public Document getData(String message){
        Document document ;
        try{

            document = Jsoup.connect("https://chat.simsimi.info/post_sim.php")
                    .data("hoi",message)
                    .post();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document;
    }
    public String result(String message){
        Document data= getData(message);
        String kq ;
        if(data.select("font").isEmpty()){
            kq=(getData(message).body().ownText());
        }else {
            kq = (Objects.requireNonNull(data.select("font").first()).ownText());
        }
        System.out.println(kq);
        return kq;
    }

    public static void main(String[] args) {
        APIchat a= new APIchat();
        System.out.println(a.result("chó"));
    }
}
