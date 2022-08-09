package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemStore;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Service
public class ItemService {

    private final ItemStore itemStore;


    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public Collection<Item> readAll() {
        return itemStore.readAll();
    }

    public Item create(Item item) {
        return itemStore.create(item);
    }

    public Collection<Item> readAllDone() {
        return itemStore.readAll().stream().filter(x -> x.isDone()).toList();
    }

    public Collection<Item> readAllNew() {
        return itemStore.readAll().stream().filter(x -> x.getCreated()
                .after(localDateTimeToDate(LocalDateTime.now().minusDays(1))))
                .toList();
    }

    public Item findById(int id) {
        return itemStore.readById(id);
    }

    public Item doneTask(int id) {
        Item item = itemStore.readById(id);
        item.setDone(true);
        return itemStore.update(id, item);
    }

    public boolean deleteTask(int id) {
        return itemStore.delete(id);
    }

    public Item update(Item item) {
        return itemStore.update(item.getId(), item);
    }

    public Collection<Category> getCategories() {
        return itemStore.getCategories();
    }

    public Category getCategoryById(String x) {
        int catId = Integer.parseInt(x);
        return itemStore.getCategoryById(catId);
    }

    private Date localDateTimeToDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
