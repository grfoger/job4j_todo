package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item create(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public Item readById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();

        Item item = session.get(Item.class, id);

        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> readAll() {
        Session session = sf.openSession();
        session.beginTransaction();

        List list = session.createQuery("from Item").list();

        session.getTransaction().commit();
        session.close();
        return list;
    }

    public Item update(int id, Item item) {
        Item oldItem = readById(id);
        Session session = sf.openSession();
        session.beginTransaction();
        /*
        Query query = session.createQuery("update Item i set "
                + "i.description = :description, "
                + "i.created = :created, "
                + "i.done = :done "
                + "where i.id = :id")
                .setParameter("description", item.getDescription())
                .setParameter("created", item.getCreated())
                .setParameter("done", item.isDone())
                .setParameter("id", id);
        */
        item.setId(id);
        session.update(item);

        session.getTransaction().commit();
        session.close();
        return oldItem;
    }

    private boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("delete from Item where id = :id")
                .setParameter("id", id);
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();
        return result != 0;
    }
}