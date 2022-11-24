package Security.RSA;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    public RSAUtil(){}
    public String Encrypt(String mess,PublicKey publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] encryptedMessage =
                encryptionCipher.doFinal(mess.getBytes());
        String encryption = Base64.getEncoder().encodeToString(encryptedMessage);
        return encryption;
    }
    public String Decrypt(String encryptedMessage,PrivateKey privateKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        decryptionCipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedMessage =
                decryptionCipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        String decryption = new String(decryptedMessage);
        return decryption;
    }
    public PublicKey convertStringToPublicKey(String public_key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PublicKey publicKey1;
        byte[]byte_pubkey  = Base64.getDecoder().decode(public_key);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        publicKey1 = factory.generatePublic(new X509EncodedKeySpec(byte_pubkey));
        return publicKey1;
    }

//    public static void main(String[] args) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//        generateKey g =new generateKey();
//        RSAUtil rsaUtil = new RSAUtil();
//        String mahoa = rsaUtil.Encrypt("hello", g.getPublicKey());
//        System.out.println(mahoa);
//
//
//        String giaima = rsaUtil.Decrypt(mahoa,g.getPrivateKey());
//        String da = rsaUtil.Encrypt(giaima,(PublicKey) g.getPrivateKey());
//        System.out.println(da);
//    }
}
