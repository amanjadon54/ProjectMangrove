package com.save.mangrove;

import java.util.List;

public interface DatabaseRepository {

    List<UserData> fetchUsers();

    List<Transactions> fetchTxn();

    void updateUserData(String pubKey, String id);
}
