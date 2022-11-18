package Server;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class APIchat {
    public Document getData(String message){
        Document document ;
        try{

//            document = Jsoup.connect("https://chat.simsimi.info/post_sim.php")
//                    .data("hoi",message)
//                    .post();
            document = Jsoup.connect("https://simsimi.info/api/?text="+message+"&lc=vn")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document;
    }
    public String result(String message){
//        Document data= getData(message);
        String kq ;
//        if(data.select("font").isEmpty()){
//            kq=(getData(message).body().ownText());
//        }else {
//            kq = (Objects.requireNonNull(data.select("font").first()).ownText());
//        }
        JSONObject jsonObject = new JSONObject(getData(message).body().ownText());

        System.out.println(jsonObject.getString("message"));
        return jsonObject.getString("message");
    }

    public static void main(String[] args) {
        APIchat a= new APIchat();
        System.out.println(a.result("hello"));
    }
}
