package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.domain.User;
import org.example.exceptions.DatabaseUnavailableException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {

    public void addUser(User user) throws DatabaseUnavailableException {
        Transaction transaction = null;
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseUnavailableException("Database is unavailable", e);
        }
    }

    public void deleteAllUsers() throws DatabaseUnavailableException {
        Transaction transaction = null;
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseUnavailableException("Database is unavailable", e);
        }
    }

    public void printAllUsers() throws DatabaseUnavailableException {
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            session.createQuery("FROM User", User.class).list()
                    .forEach(user -> System.out.println(user.getUserName()));
        } catch (Exception e) {
            throw new DatabaseUnavailableException("Database is unavailable", e);
        }
    }
}
