package Server;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Weather {
    public JSONObject getInfoWether(String address){
        JSONObject result = null;
        try {
            String url_request = "https://api.weatherapi.com/v1/forecast.json?key=823dff21e5ca42df845114013222411&q="+address+"&days=3&lang=vi";
            Document doc =  Jsoup.connect(url_request).ignoreContentType(true).ignoreHttpErrors(true).get();

            result = new JSONObject(doc.body().ownText());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public JSONObject result (String addr){
            return getInfoWether(addr);
    }
}
