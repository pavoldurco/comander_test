package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.domain.User;
import org.example.exceptions.DatabaseUnavailableException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    private UserRepository userRepository;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        DatabaseConfig.setSessionFactory(sessionFactory);

        userRepository = new UserRepository();
    }

    @Nested
    class AddUserTests {

        @Test
        void addUser() throws DatabaseUnavailableException {
            User user = new User();
            user.setUserName("testUser");

            userRepository.addUser(user);

            verify(session, times(1)).save(user);
            verify(transaction, times(1)).commit();
        }

        @Test
        void addUser_whenDatabaseUnavailable() {
            User user = new User();
            user.setUserName("testUser");

            doThrow(RuntimeException.class).when(session).save(user);

            assertThrows(DatabaseUnavailableException.class, () -> userRepository.addUser(user));

            verify(session, times(1)).save(user);
            verify(transaction, never()).commit();
        }

        @Test
        void addUser_whenUserNameIsEmpty() {
            User user = new User();
            user.setUserName("");

            assertDoesNotThrow(() -> userRepository.addUser(user));

            verify(session, times(1)).save(user);
            verify(transaction, times(1)).commit();
        }
    }

    @Nested
    class DeleteAllUsersTests {

        @Test
        void deleteAllUsers() throws DatabaseUnavailableException {
            Query query = mock(Query.class);
            when(session.createQuery("DELETE FROM User")).thenReturn(query);

            userRepository.deleteAllUsers();

            verify(session, times(1)).createQuery("DELETE FROM User");
            verify(transaction, times(1)).commit();
        }

        @Test
        void deleteAllUsers_whenDatabaseUnavailable() {
            doThrow(RuntimeException.class).when(session).createQuery("DELETE FROM User");

            assertThrows(DatabaseUnavailableException.class, () -> userRepository.deleteAllUsers());

            verify(session, times(1)).createQuery("DELETE FROM User");
            verify(transaction, never()).commit();
        }
    }

    @Nested
    class PrintAllUsersTests {

        @Test
        void printAllUsers() throws DatabaseUnavailableException {
            Query query = mock(Query.class);
            when(session.createQuery("FROM User", User.class)).thenReturn(query);
            when(query.list()).thenReturn(new ArrayList<>());

            userRepository.printAllUsers();

            verify(session, times(1)).createQuery("FROM User", User.class);
        }

        @Test
        void printAllUsers_whenDatabaseUnavailable() {
            when(session.createQuery("FROM User", User.class)).thenThrow(RuntimeException.class);

            assertThrows(DatabaseUnavailableException.class, () -> userRepository.printAllUsers());

            verify(session, times(1)).createQuery("FROM User", User.class);
        }
    }
}