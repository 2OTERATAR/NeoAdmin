package me.neo.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChat implements CommandExecutor {
    private final NeoAdmin plugin;

    public StaffChat(NeoAdmin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("neoadmin.staffchat")) return true;
        if (args.length == 0) return false;

        String message = String.join(" ", args);
        String format = plugin.getConfig().getString("settings.staff-format", "<gold>STAFF</gold> <gray>| <yellow>%player%</yellow> <dark_gray>»</dark_gray> <gold>%message%</gold>");

        var component = plugin.mm().deserialize(format
                .replace("%player%", sender.getName())
                .replace("%message%", message)
                .replace("%rank%", "Admin") // Здесь потом можно прикрутить систему рангов
        );

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("neoadmin.staffchat")) {
                p.sendMessage(component);
            }
        }
        return true;
    }
}