package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.function.Function;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
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

    public Collection<Category> getCategories() {
        return (Collection<Category>) tx(session -> session.createQuery("from Category").list());
    }

    public Category getCategoryById(int id) {
        return (Category) tx(session -> session.get(Category.class, id));
    }
}