package Helper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class Handle {
    public void Whois(JTextArea textArea1 ,@NotNull JSONObject res){
        JSONObject jsonObject1 = res.getJSONObject("data");
        if(res.get("error").equals("")){
            textArea1.append("Tên miền:\t"+jsonObject1.getString("Tên miền")+"\n");
            textArea1.append("Ngày đăng ký:\t"+jsonObject1.getString("Ngày đăng ký")+"\n");
            textArea1.append("Ngày hết hạn:\t"+jsonObject1.getString("Ngày hết hạn")+"\n");
            if(jsonObject1.has("Chủ sở hữu")){
                textArea1.append("Chủ sở hữu:\t"+jsonObject1.getString("Chủ sở hữu")+"\n");
            }
            textArea1.append("Nhà đăng ký:\t"+jsonObject1.getString("Nhà đăng ký")+"\n");
            textArea1.append("Nameservers:\t"+jsonObject1.getString("Nameservers")+"\n");
            textArea1.append("Trạng thái:\t"+jsonObject1.getString("Trạng thái")+"\n");
            textArea1.append("Loại tên miền:\t"+jsonObject1.getString("Loại tên miền")+"\n");
        }else{
            textArea1.append(res.getString("error"));
        }
    }
    public void InfoIP(JTextArea textArea1,@NotNull JSONObject res){
        System.out.println(res);
        JSONObject data = res.getJSONObject("data");
        if(res.get("error").equals("")){
            textArea1.append("Thông tin địa chỉ ip: \t"+data.get("query")+"\n"+
                    "Châu lục: \t\t" + data.get("continent")+"\n"+
                    "Quốc gia: \t\t"+data.get("country")+"\n"+
                    "Thành phố: \t\t"+data.get("regionName")+"\n"+
                    "Nhà cung cấp: \t"+data.get("isp")+"\n"+
                    "Vĩ độ: \t\t"+data.get("lat")+"\n"+
                    "Kinh độ: \t\t"+data.get("lon")+"\n");
        }else {
            textArea1.append(res.getString("error"));
        }

    }
    public void Weather(JTextArea textArea1, JSONObject res){
        System.out.println(res.toString());
        JSONObject data = res.getJSONObject("data");
        JSONObject location = data.getJSONObject("location");
        JSONObject j= data.getJSONObject("current");
        JSONObject text =j.getJSONObject("condition");
        JSONObject forecast = data.getJSONObject("forecast");
        JSONArray forecastday = forecast.getJSONArray("forecastday");
        textArea1.append("================\n");
        textArea1.append(location.get("name")+"\nThời tiết: \t"+text.get("text")+"  \nNhiệt độ: \t"+j.get("temp_c")+" độ C \nĐộ ẩm:\t"+j.get("humidity")+"\t\n");
        textArea1.append("================\n");
        for(int i= 1;i<forecastday.length();i++){
            JSONObject dt = forecastday.getJSONObject(i);
            JSONObject day= dt.getJSONObject("day");
            JSONObject condition =day.getJSONObject("condition");

            textArea1.append(dt.get("date")+"\n" +
                    "Thời tiết: \t\t"+condition.get("text")+"  \n" +
                    "Nhiệt độ thấp nhất: \t"+day.get("mintemp_c")+" độ C \n" +
                    "Nhiệt độ cao nhất: \t"+day.get("maxtemp_c")+" độ C \n" +
                    "Độ ẩm thấp trung bình: \t"+day.get("avghumidity")+"\n"
            );
            textArea1.append("================\n");
        }
    }
    public void ChatBOT(JTextArea textArea1,JSONObject res){
        textArea1.append("Rep:"+res.getString("data")+"\n");
    }
    public void Md5(JTextArea textArea1,JSONObject res){
        System.out.println(res);
        if(res.getString("error").equals("")){
            if(res.getString("func").equals("hash")){
                textArea1.append("Result: "+res.getString("data")+"\n");
            }else{
                textArea1.append("Result: "+res.getString("data")+"\n");
            }
        }else{
            textArea1.append("Error: Không thể xử lý chuỗi truyền vào"+"\n");
        }
    }
    public void ChangeMoney(JTextArea textArea1,JSONObject res){
        if(res.getString("error").equals("")){
            JSONObject data;
            data= res.getJSONObject("data");
            textArea1.append("REP: "+data.get("money").toString()+" "+data.getString("from")+" = "+data.getString("change")+" "+data.getString("to")+"\n");
        }else{
            textArea1.append("Error: "+res.getString("error")+"\n");
        }
    }
    public void Scan(JTextArea jTextArea,JSONObject res){
        if(res.getString("error").equals("")){
            jTextArea.append("Port open: "+res.getString("data")+"\n");
        }else{
            jTextArea.append("Error: "+res.getString("error")+"\n");
        }
    }
}
