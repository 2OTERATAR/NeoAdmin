package dev.oter.neoAdmin; // ИСПРАВЛЕНО: Пакет должен быть dev.oter.neoAdmin

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public class AdminManager {
    private final Map<UUID, PlayerSnapshot> snapshots = new HashMap<>();
    private final Set<UUID> inAdminMode = new HashSet<>();
    private final MiniMessage mm = MiniMessage.miniMessage(); // Используем напрямую

    public void toggleAdminMode(Player player) {
        UUID uuid = player.getUniqueId();

        if (!inAdminMode.contains(uuid)) {
            // Включаем Админ-режим
            snapshots.put(uuid, new PlayerSnapshot(player));
            inAdminMode.add(uuid);

            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            player.setInvisible(true); // Ваниш (визуальный)

            // Строка 21 ИСПРАВЛЕНА:
            player.sendMessage(mm.deserialize("<gold>Админ-режим включен. Вы скрыты из мира."));
        } else {
            // Выключаем и восстанавливаем состояние
            PlayerSnapshot s = snapshots.remove(uuid);
            if (s != null) {
                s.restore(player);
            }
            inAdminMode.remove(uuid);
            player.setInvisible(false);

            // Строка 28 ИСПРАВЛЕНА:
            player.sendMessage(mm.deserialize("<yellow>Админ-режим выключен. Данные восстановлены."));
        }
    }

    // Внутренний класс для хранения данных игрока
    private static class PlayerSnapshot {
        private final ItemStack[] inv;
        private final double health;
        private final int food;
        private final GameMode gm;

        PlayerSnapshot(Player p) {
            this.inv = p.getInventory().getContents().clone(); // Клонируем массив для защиты данных
            this.health = p.getHealth();
            this.food = p.getFoodLevel();
            this.gm = p.getGameMode();
        }

        void restore(Player p) {
            p.getInventory().setContents(inv);
            p.setHealth(health);
            p.setFoodLevel(food);
            p.setGameMode(gm);
        }
    }
}