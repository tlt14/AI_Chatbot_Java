# AI_Chatbot_java
Chat với bot 

Cú pháp 

ip-"địa chỉ ip"->tra thông tin ip
Ví dụ : ip-8.8.8.8

whois-"domain"
Ví dụ: whois-sgu.edu.vn

md5-"chuỗi cần mã hóa"
Ví dụ: md5-hello

decrypt-"chuỗi md5 cần giải mã"

weather-"địa chỉ muốn xem thời tiết dạng không dấu"
Ví dụ: weather-Ho Chi Minh
{"result":"infoip","data":{"continent":"North America","zip":"20149","country":"United States","city":"Ashburn","org":"Google Public DNS","timezone":"America/New_York","regionName":"Virginia","isp":"Google LLC","query":"8.8.8.8","lon":-77.5,"as":"AS15169 Google LLC","countryCode":"US","region":"VA","lat":39.03,"status":"success"},"error":""}
{"result":"infoip","data":{"query":"8.8","message":"invalid query","status":"fail"},"error":"Không tìm thấy thông tin địa chỉ IP"}
{"result":"md5","func":"hash","data":"202cb962ac59075b964b07152d234b70","error":""}
{"result":"md5","func":"decrypted","data":"123","error":""}
