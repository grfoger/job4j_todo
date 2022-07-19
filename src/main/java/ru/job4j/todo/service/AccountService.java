package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.persistence.AccountStore;

@Service
public class AccountService {

    private final AccountStore accStore;


    public AccountService(AccountStore accStore) {
        this.accStore = accStore;
    }
}
