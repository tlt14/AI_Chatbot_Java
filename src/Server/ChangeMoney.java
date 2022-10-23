package Server;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class ChangeMoney {

    public Document getData(String moneyFrom, String moneyTo, long money){
        String url ="https://"+moneyFrom+".fxexchangerate.com/"+moneyTo+"/"+money+"-currency-rates.html";
        Document doc;
        try {
            doc = Jsoup.connect(url).ignoreContentType(true).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }
    public JSONObject result (String moneyFrom, String moneyTo, long money){
        JSONObject data = new JSONObject();
        JSONObject result = new JSONObject();
        try {
            Document doc = getData(moneyFrom,moneyTo,money);
            Elements e = doc.select("div.fxtoday>p>b");
            data.put("from",moneyFrom);
            data.put("to",moneyTo);
            data.put("money",money);
            data.put("change", Objects.requireNonNull(e.get(2).select("#srate").first()).ownText()+" ");
            result.put("data",data);
            result.put("result","change");
            result.put("error","");
        }catch (Exception e){
            result.put("data","");
            result.put("result","change");
            result.put("error","Bạn nhập sai cú pháp rồi");
        }
        return result;
    }
    public static void main(String[] args) {
        ChangeMoney c= new ChangeMoney();
        String moneyFrom = "usd";
//        String moneyTo = ;
        long money = 15000000;
        System.out.println(c.result(moneyFrom,null,money));
    }
}
