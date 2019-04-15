package com.save.mangrove;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;

@Component
public class GenerateKeys implements KeyGenerator {

    @Autowired
    KeyStoreManager keyStore;

    @Value("${KEYSTORE_ALGO}")
    private String ALGO;
    @Value("${KEYSTORE_INSTANCE}")
    private String INSTANCE;
    @Value("${KEYSTORE_PWD}")
    private String KSPWD;
    @Value("${ENTRY_PWD}")
    private String ENTRYPWD;
    @Value("${KEYSTORE_LOCATION}")
    private String KSLOCATION;
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    @Getter
    private PrivateKey privateKey;
    @Getter
    private PublicKey publicKey;
    private int keyLength = 1024;


    public void applyAlgorithm() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance(ALGO);
        this.keyGen.initialize(keyLength);
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void writeKeys(String path, byte[] key,String userId) throws Exception {
        keyStore.addToKeyStore(key, INSTANCE, ALGO, KSPWD, ENTRYPWD, KSLOCATION, userId);
        //addToKeyStore(byte[] priKey, String instance, String algo, String KeyStorePwd, String entryPwd, String keyStorePath)
        File f = new File(path);
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public SecretKey readKey(String userId) throws Exception {

        Key key = keyStore.retrieveKeyFromKeyStore(INSTANCE, KSPWD, ENTRYPWD, KSLOCATION, userId);

//        InputStream in = new FileInputStream(privateKeyFile);
//        ObjectInputStream oin =
//                new ObjectInputStream(new BufferedInputStream(in));
//        try {
//            BigInteger m = (BigInteger) oin.readObject();
//            BigInteger e = (BigInteger) oin.readObject();
//            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
//            KeyFactory fact = KeyFactory.getInstance("RSA");
//            PrivateKey priKey = fact.generatePrivate(keySpec);
//            return priKey;
//        } catch (Exception e) {
//            throw new Exception(e);
//        } finally {
//            oin.close();
//        }
//    }
        return (SecretKey) key;
    }
}