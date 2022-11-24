package Server;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MD5{
    public JSONObject Hash(String mess){
        JSONObject jsonObject = new JSONObject();
        Document doc = null;
        System.out.println("https://hashtoolkit.com/generate-hash/?text="+mess);
        try {
            doc = Jsoup.connect("https://hashtoolkit.com/generate-hash/?text="+mess).get();
            jsonObject.put("result","md5");
            jsonObject.put("error","");
            jsonObject.put("func","hash");
            String HashResult;
            if(!mess.equals("")){
                HashResult= doc.select("tbody tr:nth-child(1) td:nth-child(2) a").first().ownText();
            }else{
                HashResult ="Vui lòng nhập vào chuổi để mã hóa!!!";
            }
            jsonObject.put("data",HashResult);
        } catch (Exception e) {
            jsonObject.put("error","true");
        }
        return jsonObject;
    }
    public JSONObject Decrypted(String mess){
        JSONObject jsonObject = new JSONObject();
        Document doc = null;
        try {
            jsonObject.put("result","md5");
            jsonObject.put("error","");
            jsonObject.put("func","decrypted");
            doc = Jsoup.connect("https://hashtoolkit.com/decrypt-md5-hash/"+mess).get();
            String HashResult= doc.select("tbody tr:nth-child(1) td:nth-child(3) a").first().ownText();
            jsonObject.put("data",HashResult);
        } catch (Exception e) {
            jsonObject.put("error","true");
        }
        return jsonObject;
    }
}