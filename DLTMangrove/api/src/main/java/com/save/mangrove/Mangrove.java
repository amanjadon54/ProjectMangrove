package com.save.mangrove;

import java.util.List;

public interface Mangrove {

    List<Transactions> listTxns();

    List<UserData> listUsers();

    Object createKeys() throws Exception;

}
