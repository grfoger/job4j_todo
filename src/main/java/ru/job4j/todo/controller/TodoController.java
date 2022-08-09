package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class TodoController {

    private final ItemService itemService;

    public TodoController(ItemService itemService) {
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

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("items", itemService.readAll());
        return "all";
    }

    @GetMapping("/all")
    public String all(Model model, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("items", itemService.readAll());
        return "all";
    }

    @GetMapping("/done")
    public String done(Model model, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("items", itemService.readAllDone());
        return "done";
    }

    @GetMapping("/new")
    public String newTasks(Model model, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("items", itemService.readAllNew());
        return "new";
    }

    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession session) {
        model.addAttribute("categories", itemService.getCategories());
        model.addAttribute("user", getAccount(session));
        return "addTask";
    }

    @PostMapping("/addTask")
    public String newTask(@ModelAttribute Item item, HttpSession session, @RequestParam(name = "catId", required = false) Set<String> catSet) {
        item.setCreated(new Date());
        item.setUser((Account) session.getAttribute("acc"));
        if (catSet != null) {
            catSet.forEach(x -> item.getCategories().add(itemService.getCategoryById(x)));
        }
        itemService.create(item);
        return "redirect:/all";
    }

    @GetMapping("/task/{itemId}")
    public String task(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("item", itemService.findById(id));
        return "task";
    }

    @PostMapping("/doneTask")
    public String doneTask(Model model, @ModelAttribute Item item) {
        model.addAttribute("items", itemService.readAllDone());
        itemService.update(item);
        return "redirect:/done";
    }

    @GetMapping("/updateTask/{itemId}")
    public String updateTask(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        model.addAttribute("item", itemService.findById(id));
        return "updateTask";
    }

    @GetMapping("/deleteTask/{itemId}")
    public String deleteTask(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("user", getAccount(session));
        itemService.deleteTask(id);
        model.addAttribute("items", itemService.readAll());
        return "all";
    }

    @PostMapping("/updateTask")
    public String updateTaskPost(Model model, @ModelAttribute Item item) {
        model.addAttribute("item", itemService.findById(item.getId()));
        itemService.update(item);
        return "redirect:/task/" + item.getId();
    }
}
