package ru.job4j.todo.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Account;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class AccountStore {

    private final SessionFactory sf;

    public AccountStore(SessionFactory sf) {
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

    public Optional<Account> create(Account acc) {

        try {
            tx(session -> session.save(acc));
        } catch (HibernateException e) {
            return Optional.empty();
        }
        return Optional.of(acc);
    }

    public Account readById(int id) {
        return (Account) tx(session -> session.get(Account.class, id));
    }

    public Collection<Account> readAll() {
        return (Collection<Account>) tx(session -> session.createQuery("from Account").list());
    }

    public Account update(int id, Account acc) {
        Account oldAcc = readById(id);
        acc.setId(id);
        tx(session -> {
            session.update(acc);
            return null;
        });
        return oldAcc;
    }

    public boolean delete(int id) {
        int result = tx(session -> {
            Query query = session.createQuery("delete from Account where id = :id")
                    .setParameter("id", id);
            return query.executeUpdate();
        });
        return result != 0;
    }

    public Optional<Account> findUserByLoginAndPass(String login, String password) {
        return tx(session ->
                session.createQuery("from Account where login = :login AND password = :password")
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .uniqueResultOptional());
    }
}