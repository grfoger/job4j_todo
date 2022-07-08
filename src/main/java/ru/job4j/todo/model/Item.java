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
    private Timestamp created;
    private boolean done;

    public Item() {
    }

    public Item(int id, String description, Timestamp created) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = false;
    }

    public Item(String description) {
        this.description = description;
        this.created = Timestamp.valueOf(LocalDateTime.now());
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

    public String getCreated() {
        return created.toLocalDateTime().format(DateTimeFormatter.ofPattern("MM-dd-yy HH:mm"));
    }

    public void setCreated(Timestamp created) {
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
