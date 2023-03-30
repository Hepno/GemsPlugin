package dev.hepno.gemsplugin;

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

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        instance = this;
        databaseManager = new DatabaseManager();

        // Database
        try {
            databaseManager.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }

        // PlaceholderAPI
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GemsPlugin getInstance() { return instance; }
}
