package Server;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
public class APIchat {
    public Document getData(String message){
        Document document ;
        try{
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
        JSONObject jsonObject = new JSONObject(getData(message).body().ownText());
        System.out.println(jsonObject.getString("message"));
        return jsonObject.getString("message");
    }

    public static void main(String[] args) {
        new APIchat().result("i[");
    }

}
