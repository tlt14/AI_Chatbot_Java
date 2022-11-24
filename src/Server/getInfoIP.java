package Server;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class getInfoIP {
    public JSONObject JsonInfoIP(String addressIP){
        JSONObject result ;
        try {
            String url_request = "http://ip-api.com/json/"+addressIP+"?fields=status,message,continent,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query";
            Document doc =  Jsoup.connect(url_request).ignoreContentType(true).get();
            result = new JSONObject(doc.body().ownText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(addressIP.equals("")){
            return new JSONObject("{\"query\":\"8.8\",\"message\":\"invalid query\",\"status\":\"fail\"}");
        }
        return result;
    }
    public JSONObject result(String addr){
        String result = "";
        JSONObject data = JsonInfoIP(addr);
        JSONObject kq = new JSONObject();
        if(data.get("status").equals("success")){
            result ="Thông tin địa chỉ ip: "+data.get("query")+
                    "\nChâu lục: " + data.get("continent")+
                    "\nQuốc gia: "+data.get("country")+
                    "\nThành phố: "+data.get("regionName")+
                    "\nNhà cung cấp: "+data.get("isp")+
                    "\nVĩ độ: "+data.get("lat")+
                    "\nKinh độ: "+data.get("lon");

            kq.put("data",data);
            kq.put("result","infoip");
            kq.put("error","");
        }else{
            kq.put("error","Không tìm thấy thông tin địa chỉ IP");
            kq.put("result","infoip");
            kq.put("data",data);
        }
        System.out.println(result);

        return kq;
    }
}
