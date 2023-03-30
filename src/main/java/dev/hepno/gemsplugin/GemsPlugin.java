package dev.hepno.gemsplugin;

import dev.hepno.gemsplugin.manager.DatabaseManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class GemsPlugin extends JavaPlugin {

    private static GemsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        DatabaseManager databaseManager = new DatabaseManager(this);

        // Database
        try {
            databaseManager.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // PlaceholderAPI

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GemsPlugin getInstance() {
        return instance;
    }
}
