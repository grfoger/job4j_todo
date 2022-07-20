package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.service.AccountService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    @GetMapping("/reg")
    public String formRegistration(Model model, @RequestParam(name = "fail", required = false) Boolean fail, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("fail", fail != null);
        return "reg";
    }

    @PostMapping("/reg")
    public String registration(@ModelAttribute Account user, HttpSession session) {
        Optional<Account> regUser = accountService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/reg?fail=true";
        }
        session.setAttribute("acc", regUser.get());
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("items", itemService.readAll());
        return "all";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail, HttpSession session) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("user", getAccount(session));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account user, HttpSession session) {
        Optional<Account> userDb = accountService.findUserByLoginAndPass(
                user.getLogin(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/login?fail=true";
        }
        session.setAttribute("acc", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
