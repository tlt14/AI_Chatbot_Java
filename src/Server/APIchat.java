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
//            System.out.println("https://simsimi.info/api/?text="+message+"&lc=vn");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document;
    }
    public String result(String message){
        JSONObject jsonObject = new JSONObject(getData(message).body().ownText());
        String result ;
        try{
             result = jsonObject.getString("message");
        }catch (Exception e){
            result =  "Không có câu trả lời";
        }
        return result;
    }


}
