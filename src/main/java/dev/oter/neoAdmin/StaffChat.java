package dev.oter.neoAdmin; // ИСПРАВЛЕНО: Пакет должен быть таким же, как в главном классе

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChat implements CommandExecutor {
    private final NeoAdmin plugin;
    // Создаем MiniMessage прямо здесь для скорости и независимости
    private final MiniMessage mm = MiniMessage.miniMessage();

    public StaffChat(NeoAdmin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Проверка прав
        if (!sender.hasPermission("neoadmin.staffchat")) {
            sender.sendMessage("§cНет прав на использование админ-чата.");
            return true;
        }

        // Если админ просто ввел /a без текста
        if (args.length == 0) {
            sender.sendMessage("§cИспользование: /a <сообщение>");
            return true;
        }

        String message = String.join(" ", args);

        // Берем формат из конфига или ставим дефолт (защита от пустых конфигов)
        String format = plugin.getConfig().getString("settings.staff-format",
                "<gold>STAFF</gold> <gray>| <yellow>%player%</yellow> <dark_gray>»</dark_gray> <gold>%message%</gold>");

        // ИСПРАВЛЕНО: Используем mm.deserialize вместо plugin.mm()
        var component = mm.deserialize(format
                .replace("%player%", sender.getName())
                .replace("%message%", message)
                .replace("%rank%", "Admin")
        );

        // Рассылка персоналу и в консоль (для логов)
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("neoadmin.staffchat")) {
                p.sendMessage(component);
            }
        }
        Bukkit.getConsoleSender().sendMessage(component);

        return true;
    }
}