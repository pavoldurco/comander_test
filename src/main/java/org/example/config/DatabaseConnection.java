package org.example.config;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();
}