package Server;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Whois {
    public Document getData(String domain){
        Document doc =null;
        System.out.println("https://123host.vn/domain/whois?domain="+domain);
        try {
            doc = Jsoup.connect("https://123host.vn/domain/whois?domain="+domain)
                    .ignoreContentType(true)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    public String result(String domain){
        String kq= "{\"data\":{";
        Document doc = getData(domain);
        if(doc.select("table").isEmpty()){
            kq+="},\"error\": Tên miền "+domain+" chưa được đăng ký,"+"\"result\":\"whois\"}";
        }else{
            Element table = doc.select("table").first();
            Elements rows = (Elements) table.select("tr");
            for (Element row :rows){
                String col1=row.select("td").get(0).text();
                String col2=row.select("td").get(1).text();;
                kq  +="\""+col1+"\""+":\""+col2 +"\",";
            }
            kq+="},\"result\":\"whois\",\"error\":\"\"}";
        }
        return kq;
    }
//    public static void main(String[] args) {
//        Whois w = new Whois();
//        JSONObject object= new JSONObject(w.result("sgu.edu.vn"));
//        System.out.println(object);
//    }
}
