package com.save.mangrove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.Key;
import java.util.List;

@Service
public class MangrovesImpl implements Mangrove {

    public static int userCount = 0;
    public static String USER = "user";

    @Autowired
    KeyGenerator store;

    @Autowired
    DatabaseRepository repository;

    public Object createKeys() throws Exception {
        store.applyAlgorithm();
        store.writeKeys("KeyPair/privateKey" + userCount, store.getPrivateKey().getEncoded(), USER + userCount);
        Key key = store.readKey(USER + userCount);
        userCount++;
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println("---BEGIN PRIVATE KEY---AS CREATED");
        System.out.println(encoder.encode(store.getPrivateKey().getEncoded()));
        System.out.println("---END OF PRIVATE KEY-----------");
        System.out.println();
        System.out.println("---BEGIN PUBLIC KEY---AS CREATED");
        System.out.println(encoder.encode(store.getPublicKey().getEncoded()));
        System.out.println();
        System.out.println("---BEGIN PRIVATE KEY---FROM KEYSTORE");
        String encoded = encoder.encode(key.getEncoded());
        System.out.println(encoded);
        System.out.println("---END PRIVATE KEY---");

        repository.updateUserData(encoder.encode(store.getPublicKey().getEncoded()),"2");

        return "Keys Added Successfully";
    }

    public List<UserData> listUsers() {
        return repository.fetchUsers();
    }

    public List<Transactions> listTxns() {
        return repository.fetchTxn();
    }

}
