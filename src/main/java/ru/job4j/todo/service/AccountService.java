package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.persistence.AccountStore;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountStore accStore;


    public AccountService(AccountStore accStore) {
        this.accStore = accStore;
    }

    public Optional<Account> add(Account user) {
        return accStore.create(user);
    }

    public Optional<Account> findUserByLoginAndPass(String login, String password) {
        return accStore.findUserByLoginAndPass(login, password);
    }
}
