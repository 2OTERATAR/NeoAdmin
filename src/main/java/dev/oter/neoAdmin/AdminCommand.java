package dev.oter.neoAdmin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Обработка админ-чата (/a <сообщение>)
        if (label.equalsIgnoreCase("a")) {
            if (!sender.hasPermission("neoadmin.staffchat")) {
                sender.sendMessage("§cУ вас нет прав на админ-чат!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§cИспользуйте: /a <сообщение>");
                return true;
            }

            String message = String.join(" ", args);
            String format = "§8[§dStaffChat§8] §f" + sender.getName() + ": §d" + message;

            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission("neoadmin.staffchat"))
                    .forEach(p -> p.sendMessage(format));

            Bukkit.getConsoleSender().sendMessage(format);
            return true;
        }

        // Обработка основной команды (/admin)
        if (label.equalsIgnoreCase("admin")) {
            if (!sender.hasPermission("neoadmin.use")) {
                sender.sendMessage("§cНедостаточно прав!");
                return true;
            }

            if (args.length > 0 && args[0].equalsIgnoreCase("on")) {
                if (!sender.hasPermission("neoadmin.mode")) {
                    sender.sendMessage("§cНет прав на включение режима!");
                    return true;
                }
                sender.sendMessage("§a[NeoAdmin] Режим администратора включен.");
                // Тут можно добавить логику (гм 1, ваниш и т.д.)
                return true;
            }

            sender.sendMessage("§7Используйте: §f/admin on §7или §f/a <текст>");
        }

        return true;
    }
}