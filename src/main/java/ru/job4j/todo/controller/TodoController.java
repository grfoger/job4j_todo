package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.model.Item;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {

    @GetMapping("/index")
    public String index(Model model) {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Сделать дело"));
        model.addAttribute("items", items);
        return "all";
    }
}
