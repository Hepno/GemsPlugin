package dev.hepno.gemsplugin.manager;

import dev.hepno.gemsplugin.GemsPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private GemsPlugin plugin;

    public DatabaseManager(GemsPlugin plugin) {
        this.plugin = plugin;
    }

    // Database Credentials
    private final String HOST = plugin.getConfig().getString("database.host");
    private final String PORT = plugin.getConfig().getString("database.port");
    private final String DATABASE = plugin.getConfig().getString("database.database");
    private final String USERNAME = plugin.getConfig().getString("database.username");
    private final String PASSWORD = plugin.getConfig().getString("database.password");

    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() { return connection; }

    public boolean isConnected() { return connection != null; }

}
