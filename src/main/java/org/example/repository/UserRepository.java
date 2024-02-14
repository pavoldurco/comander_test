package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.domain.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {

    public void addUser(User user) {
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    public void deleteAllUsers() {
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }
    }

    public void printAllUsers() {
        try (Session session = DatabaseConfig.getSessionFactory().openSession()) {
            session.createQuery("FROM User", User.class).list()
                    .forEach(user -> System.out.println(user.getUserName()));
        }
    }
}
