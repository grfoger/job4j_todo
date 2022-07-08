package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created;
    private boolean done;

    public Item() {
    }

    public Item(int id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = false;
    }

    public Item(String description) {
        this.description = description;
        this.created = LocalDateTime.now();
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Item{"
                + "description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
