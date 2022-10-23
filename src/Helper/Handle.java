package Helper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class Handle {
    public void Whois(JTextArea textArea1 ,@NotNull JSONObject res){
        JSONObject jsonObject1 = res.getJSONObject("data");
        if(res.get("error").equals("")){
            textArea1.append("\nTên miền:\t"+jsonObject1.getString("Tên miền"));
            textArea1.append("\nNgày đăng ký:\t"+jsonObject1.getString("Ngày đăng ký"));
            textArea1.append("\nNgày hết hạn:\t"+jsonObject1.getString("Ngày hết hạn"));
            if(jsonObject1.has("Chủ sở hữu")){
                textArea1.append("\nChủ sở hữu:\t"+jsonObject1.getString("Chủ sở hữu"));
            }
            textArea1.append("\nNhà đăng ký:\t"+jsonObject1.getString("Nhà đăng ký"));
            textArea1.append("\nNameservers:\t"+jsonObject1.getString("Nameservers"));
            textArea1.append("\nTrạng thái:\t"+jsonObject1.getString("Trạng thái"));
            textArea1.append("\nLoại tên miền:\t"+jsonObject1.getString("Loại tên miền"));
        }else{
            textArea1.append(res.getString("error"));
        }
    }
    public void InfoIP(JTextArea textArea1,@NotNull JSONObject res){
        System.out.println(res);
        JSONObject data = res.getJSONObject("data");
        if(res.get("error").equals("")){
            textArea1.append("\nThông tin địa chỉ ip: \t"+data.get("query")+
                    "\nChâu lục: \t\t" + data.get("continent")+
                    "\nQuốc gia: \t\t"+data.get("country")+
                    "\nThành phố: \t\t"+data.get("regionName")+
                    "\nNhà cung cấp: \t\t"+data.get("isp")+
                    "\nVĩ độ: \t\t\t"+data.get("lat")+
                    "\nKinh độ: \t\t"+data.get("lon"));
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
        textArea1.append("\n================\n");
        textArea1.append(location.get("name")+"\nThời tiết: \t"+text.get("text")+"  \nNhiệt độ: \t"+j.get("temp_c")+" độ C \nĐộ ẩm:\t"+j.get("humidity")+"\t");
        textArea1.append("\n================\n");
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
        textArea1.append("Rep:"+res.getString("data"));
    }
    public void Md5(JTextArea textArea1,JSONObject res){
        System.out.println(res);
        if(res.getString("error").equals("")){
            if(res.getString("func").equals("hash")){
                textArea1.append("Result: "+res.getString("data"));
            }else{
                textArea1.append("Result: "+res.getString("data"));
            }
        }else{
            textArea1.append("Error: Không thể xử lý chuỗi truyền vào");
        }
    }
    public void ChangeMoney(JTextArea textArea1,JSONObject res){
        if(res.getString("error").equals("")){
            JSONObject data;
            data= res.getJSONObject("data");
            textArea1.append("REP: "+data.get("money").toString()+" "+data.getString("from")+" = "+data.getString("change")+" "+data.getString("to"));
        }else{
            textArea1.append("Error: "+res.getString("error"));
        }
    }
}
