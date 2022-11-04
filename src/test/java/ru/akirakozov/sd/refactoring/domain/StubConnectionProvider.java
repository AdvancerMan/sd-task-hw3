package ru.akirakozov.sd.refactoring.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StubConnectionProvider implements ConnectionProvider {
    private final String url;

    public StubConnectionProvider() throws IOException {
        final String path = Files.createTempFile("test", ".db").toAbsolutePath().toString();
        this.url = "jdbc:sqlite:" + path;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
