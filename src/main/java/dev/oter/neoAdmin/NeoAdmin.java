package me.neo.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {
    private final NeoAdmin plugin;

    public AdminCommand(NeoAdmin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            new AdminGui(plugin).openMenu(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) {
            if (!player.hasPermission("neoadmin.mode")) return true;
            plugin.getAdminManager().toggleAdminMode(player);
        }

        return true;
    }
}