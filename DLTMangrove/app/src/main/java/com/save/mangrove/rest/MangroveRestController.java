package com.save.mangrove.rest;

import com.save.mangrove.Mangrove;
import com.save.mangrove.Transactions;
import com.save.mangrove.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MangroveRestController {

    @Autowired
    Mangrove mangrove;

    @GetMapping("/users")
    public List<UserData> fetchUsers() {
        return mangrove.listUsers();
    }

    @GetMapping("/txn")
    public List<Transactions> fetchTxns() {
        return mangrove.listTxns();
    }

    @GetMapping("/kyc")
    public Object performKyc() throws Exception {
        //Step1. To do the Kyc of the user
        //Step2. on successful verification, genereat pub/pri key and expose it to user.

        mangrove.createKeys();
        return null;

    }


}
