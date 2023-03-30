package dev.hepno.gemsplugin.placeholder;

import dev.hepno.gemsplugin.GemsPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GemsPlaceholder extends PlaceholderExpansion {

    private final GemsPlugin plugin;

    public GemsPlaceholder(GemsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return Objects.requireNonNull(plugin.getConfig().getString("placeholder-name"));
    }

    @Override
    public @NotNull String getAuthor() {
        return "Hepno";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return null;
        }
        return plugin.getDatabaseManager().getGems(player.getUniqueId().toString()) + "";
    }
}
