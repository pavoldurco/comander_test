package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.domain.User;
import org.example.exceptions.DatabaseUnavailableException;
import org.example.exceptions.InvalidUserException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;

public class UserRepository {

    private void executeInTransaction(Consumer<Session> operation) throws DatabaseUnavailableException {
        Transaction transaction = null;
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            operation.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseUnavailableException("Database is unavailable", e);
        }
    }

    public void addUser(User user) throws DatabaseUnavailableException, InvalidUserException {
        if (user.getUserName() == null || user.getUserName().isEmpty() ||
                user.getUserGuid() == null || user.getUserGuid().isEmpty()) {
            throw new InvalidUserException("User name or user guid is empty"); //If the access will be other than through the console
        }
        executeInTransaction(session -> session.save(user));
    }

    public void deleteAllUsers() throws DatabaseUnavailableException {
        executeInTransaction(session -> session.createQuery("DELETE FROM User").executeUpdate());
    }

    public void printAllUsers() throws DatabaseUnavailableException {
        executeInTransaction(session -> session.createQuery("FROM User", User.class).list()
                .forEach(user -> System.out.println(user.getUserName())));
    }
}