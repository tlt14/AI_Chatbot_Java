package Helper;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

//    public String Encrypt(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
//
//    }
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSAUtil(){
        KeyPairGenerator keyPairGenerator =
                null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecureRandom secureRandom = new SecureRandom();

        keyPairGenerator.initialize(2048,secureRandom);

        KeyPair pair = keyPairGenerator.generateKeyPair();

        publicKey = pair.getPublic();

        String publicKeyString =
                Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println("public key = "+ publicKeyString);

        privateKey = pair.getPrivate();

        String privateKeyString =
                Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("private key = "+ privateKeyString);

    }
    public String Encrypt(String mess,PublicKey publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        //Encrypt Hello world message
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,publicKey);
        String message = "Hello world";
        byte[] encryptedMessage =
                encryptionCipher.doFinal(message.getBytes());
        String encryption =
                Base64.getEncoder().encodeToString(encryptedMessage);
        System.out.println("encrypted message = "+encryption);
        return encryption;
    }
    public String Decrypt(String encryptedMessage,PrivateKey privateKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        //Decrypt Hello world message
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        decryptionCipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedMessage =
                decryptionCipher.doFinal(encryptedMessage.getBytes());
        String decryption = new String(decryptedMessage);
        System.out.println("decrypted message = "+decryption);
        return decryption;
    }
    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException {
//        RSAUtil rsaUtil = new RSAUtil();
//        rsaUtil.Encrypt("hello", rsaUtil.publicKey);
//        String e = "OTIXn5B2bkf9RkhxRSXa2payBGdrecu5sMcOtHzp9/jYe3XdXJDgXbVf7QzfqjLyW+pskYQ5kEjrYN8ZYrQnlS2IF9DfSc2dWzYNp03CWABzd4L/bxP2IE/wS/xfIkw9x6xqrUs1KzeHeiZM3pjFobpFcWZN9VpZp8tbZegbhQQilmMyYYTp7oYjUHSlWQ15aQ+XJb1MJnXMNiVbwGllCYfqNzUaFoBt+Daj2s05Odf9ErK7yJb9uJNdKUkQ1Cw1wqvrIH6UdBdEvsru6hT07SaW21WQn2X3XyttLB9SHl81JdBkktPpW0k5WY4gwTtVYBfD06nMjvCpiiqGV5Vyhg==";
//        rsaUtil.Decrypt(e,rsaUtil.publicKey);
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();

        keyPairGenerator.initialize(2048,secureRandom);

        KeyPair pair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = pair.getPublic();

        String publicKeyString =
                Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println("public key = "+ publicKeyString);

        PrivateKey privateKey = pair.getPrivate();

        String privateKeyString =
                Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("private key = "+ privateKeyString);

        //Encrypt Hello world message
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,publicKey);
        String message = "Hello world";
        byte[] encryptedMessage =
                encryptionCipher.doFinal(message.getBytes());
        String encryption =
                Base64.getEncoder().encodeToString(encryptedMessage);
        System.out.println("encrypted message = "+encryption);

        //Decrypt Hello world message
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        decryptionCipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedMessage =
                decryptionCipher.doFinal(encryptedMessage);
        String decryption = new String(decryptedMessage);
        System.out.println("decrypted message = "+decryption);
    }
}
