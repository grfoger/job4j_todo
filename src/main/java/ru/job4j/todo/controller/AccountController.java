package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.service.AccountService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final ItemService itemService;

    public AccountController(AccountService accountService, ItemService itemService) {
        this.accountService = accountService;
        this.itemService = itemService;
    }


    private Account getAccount(HttpSession session) {
        Account user = (Account) session.getAttribute("acc");
        if (user == null) {
            user = new Account();
            user.setName("Гость");
        }
        return user;
    }

}
