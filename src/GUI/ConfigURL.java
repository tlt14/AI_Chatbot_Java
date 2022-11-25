package GUI;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Socket;

public class ConfigURL {
    public static String IP_SERVER;
    public static String API_MAIL;
    public static String API_MUSIC;
    public static String API_LOGIN ;
    public static String API_REGISTER;
    public static String API_SONG;
    public ConfigURL() throws IOException {
        String api = "https://api-generator.retool.com/ZvkfeX/data/1"; // Ghi vào dòng 1 trong DB
        Document doc = Jsoup.connect(api)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type", "application/json")
                .method(Connection.Method.GET).execute().parse();
        JSONObject jsonObject = new JSONObject(doc.text());
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("ip"));
        IP_SERVER = jsonObject.getString("ip");
        API_LOGIN= "http://"+IP_SERVER+":4000/api/auth/login";
        API_MAIL = "http://"+IP_SERVER+":4000/mail";
        API_MUSIC = "http://"+IP_SERVER+":4000/searchSong/";
        API_REGISTER = "http://"+IP_SERVER+":4000/api/auth/register";
        API_SONG = "http://"+IP_SERVER+":4000/uploads/";
    }
}
