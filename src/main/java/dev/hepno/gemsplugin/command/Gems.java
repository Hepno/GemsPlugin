package dev.hepno.gemsplugin.command;

import dev.hepno.gemsplugin.GemsPlugin;
import dev.hepno.gemsplugin.manager.Command;
import dev.hepno.gemsplugin.manager.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Gems extends Command {

    private final GemsPlugin plugin;

    public Gems(GemsPlugin plugin) {
        super(
                plugin.getConfig().getString("command-name"),
                "gems.use",
                new String[]{},
                "Main command for Gems");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return;
        }
        Player player = (Player) sender;
        DatabaseManager databaseManager = plugin.getDatabaseManager();

        if (args.length < 1) {
            if (databaseManager.playerExists(player.getUniqueId())) {
                player.sendMessage("You have " + databaseManager.getGems(String.valueOf(player.getUniqueId())) + " gems.");
            } else {
                databaseManager.createPlayer(player.getUniqueId());
                player.sendMessage("You have " + databaseManager.getGems(String.valueOf(player.getUniqueId())) + " gems.");
            }
            return;
        }

        if (args.length <= 2 && (!args[0].equalsIgnoreCase("get"))) {
            player.sendMessage("Usage: /gems <add|send|take|set|get> <player> [amount]");
            return;
        }

        if (args.length > 3) {
            player.sendMessage("Usage: /gems <add|send|take|set|get> <player> [amount]");
            return;
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("set"))) {
            if (!player.hasPermission("gems.set")) {
                player.sendMessage("You do not have permission to use this command!");
                return;
            }
            databaseManager.setGems(player.getUniqueId(), Integer.parseInt(args[2]));
            player.sendMessage(ChatColor.GREEN + "Set " + args[1] + "'s gems to " + args[2] + "!");
            return;
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("add"))) {
            if (!player.hasPermission("gems.add")) {
                player.sendMessage("You do not have permission to use this command!");
                return;
            }
            return;
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("take"))) {
            if (!player.hasPermission("gems.take")) {
                player.sendMessage("You do not have permission to use this command!");
                return;
            }
            return;
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("send"))) {
            if (!player.hasPermission("gems.send")) {
                player.sendMessage("You do not have permission to use this command!");
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
