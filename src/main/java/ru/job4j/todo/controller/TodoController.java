package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {

    private final ItemService itemService;

    public TodoController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", itemService.readAll());
        return "all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("items", itemService.readAll());
        return "all";
    }

    @GetMapping("/done")
    public String done(Model model) {
        model.addAttribute("items", itemService.readAllDone());
        return "done";
    }

    @GetMapping("/new")
    public String newTasks(Model model) {
        model.addAttribute("items", itemService.readAllNew());
        return "new";
    }

    @GetMapping("/addTask")
    public String addTask() {
        return "addTask";
    }

    @PostMapping("/addTask")
    public String newTask(@ModelAttribute Item item) {
        item.setCreated(LocalDateTime.now());
        itemService.create(item);
        return "redirect:/index";
    }
}
