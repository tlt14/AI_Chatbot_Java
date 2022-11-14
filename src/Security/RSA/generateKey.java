package Security.RSA;

import java.security.*;

public class generateKey {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    public generateKey(){
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

        setPublicKey(pair.getPublic());


        setPrivateKey(pair.getPrivate());

    }
}
