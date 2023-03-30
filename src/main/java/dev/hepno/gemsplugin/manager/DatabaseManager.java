package dev.hepno.gemsplugin.manager;

import dev.hepno.gemsplugin.GemsPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final GemsPlugin plugin = GemsPlugin.getInstance();

    // Database Credentials
    private final String HOST = plugin.getConfig().getString("mysql.host");
    private final String PORT = plugin.getConfig().getString("mysql.port");
    private final String DATABASE = plugin.getConfig().getString("mysql.database");
    private final String USERNAME = plugin.getConfig().getString("mysql.username");
    private final String PASSWORD = plugin.getConfig().getString("mysql.password");

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
