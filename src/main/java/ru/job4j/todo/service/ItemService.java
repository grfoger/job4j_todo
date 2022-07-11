package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemStore;

import java.util.Collection;

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
}
