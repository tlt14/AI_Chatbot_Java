package Helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Random;

public class Security {
    public Security(){}

    public static void sendPublicKey(PublicKey publicKey, BufferedWriter out){
        try {
            out.write(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getAESKeyFromCLient(BufferedReader in){
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendAESKeyToServer(BufferedWriter out,String AESKey){
        try {
            out.write(AESKey);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendMesssage(BufferedWriter out,String mess,String AESKey) throws IOException {
        String messageDecrypt = new AES().encrypt(mess,AESKey);
        System.out.println("client send "+messageDecrypt);
        out.write(messageDecrypt);
        out.newLine();
        out.flush();
    }
    public static String receivedMessage(BufferedReader in,String AESkey){
        try {
            String mess = in.readLine();
            System.out.println("Mess mã hóa  "+mess);
            return new AES().decrypt(mess,AESkey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static char[] OTP(int len)
    {
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] =
                    numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }
}
