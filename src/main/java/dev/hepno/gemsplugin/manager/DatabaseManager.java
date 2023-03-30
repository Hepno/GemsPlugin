package dev.hepno.gemsplugin.manager;

import dev.hepno.gemsplugin.GemsPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

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

    // Getters & Setters

    public int getGems(String player) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT gems FROM gems WHERE uuid = ?");
            ps.setString(1, player);
            ps.executeQuery();
            if (ps.getResultSet().next()) return ps.getResultSet().getInt("gems");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setGems(UUID uuid, int gems) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE gems SET gems = ? WHERE uuid = ?");
            ps.setInt(1, gems);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGems(UUID uuid, int gems) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE gems SET gems = gems + ? WHERE uuid = ?");
            ps.setInt(1, gems);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void takeGems(UUID uuid, int gems) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE gems SET gems = gems - ? WHERE uuid = ?");
            ps.setInt(1, gems);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetGems(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE gems SET gems = ? WHERE uuid = ?");
            ps.setString(1, plugin.getConfig().getString("gems.starting-balance"));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO gems (uuid, gems) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, plugin.getConfig().getString("gems.starting-balance"));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
