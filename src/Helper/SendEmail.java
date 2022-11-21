package Helper;

import GUI.ConfigURL;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SendEmail {
    public JSONObject result(String email, String otp){
        Document doc = null;
        try {
            doc = Jsoup.connect(ConfigURL.API_MAIL)
                    .data("email",email)
                    .data("otp",otp).ignoreContentType(true).post();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new JSONObject(doc.body().ownText());
    }
}