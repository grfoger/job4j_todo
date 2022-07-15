package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        session.beginTransaction();
        T result = command.apply(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Item create(Item item) {
        tx(session -> session.save(item));
        return item;
    }

    public Item readById(int id) {
        return (Item) tx(session -> session.get(Item.class, id));
    }

    public Collection<Item> readAll() {
        return (Collection<Item>) tx(session -> session.createQuery("from Item").list());
    }

    public Item update(int id, Item item) {
        Item oldItem = readById(id);
        item.setId(id);
        tx(session -> {
            session.update(item);
            return null;
        });
        return oldItem;
    }

    public boolean delete(int id) {
        int result = tx(session -> {
            Query query = session.createQuery("delete from Item where id = :id")
                    .setParameter("id", id);
            return query.executeUpdate();
        });
        return result != 0;
    }
}