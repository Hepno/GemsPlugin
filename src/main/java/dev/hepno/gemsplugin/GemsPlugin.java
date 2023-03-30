package dev.hepno.gemsplugin;

import dev.hepno.gemsplugin.command.Gems;
import dev.hepno.gemsplugin.manager.DatabaseManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class GemsPlugin extends JavaPlugin {

    private static GemsPlugin instance;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        // Register classes
        registerCommands();

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        instance = this;
        databaseManager = new DatabaseManager();

        // Database
        try {
            databaseManager.connect();
            databaseManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS gems (uuid VARCHAR(36), gems INT)").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }

        // PlaceholderAPI
    }

    @Override
    public void onDisable() {
        try {
            databaseManager.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static GemsPlugin getInstance() { return instance; }

    public DatabaseManager getDatabaseManager() { return databaseManager; }

    public void registerCommands() {
        new Gems(this);
    }
}
