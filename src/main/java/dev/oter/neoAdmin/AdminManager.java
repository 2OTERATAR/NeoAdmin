package me.neo.admin;

import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public class AdminManager {
    private final Map<UUID, PlayerSnapshot> snapshots = new HashMap<>();
    private final Set<UUID> inAdminMode = new HashSet<>();

    public void toggleAdminMode(Player player) {
        if (!inAdminMode.contains(player.getUniqueId())) {
            // Сохраняем состояние
            snapshots.put(player.getUniqueId(), new PlayerSnapshot(player));
            inAdminMode.add(player.getUniqueId());

            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            player.setInvisible(true); // Vanish
            player.sendMessage(NeoAdmin.getInstance().mm().deserialize("<gold>Админ-режим включен. Вы скрыты из мира."));
        } else {
            // Восстанавливаем
            PlayerSnapshot s = snapshots.remove(player.getUniqueId());
            if (s != null) s.restore(player);
            inAdminMode.remove(player.getUniqueId());
            player.setInvisible(false);
            player.sendMessage(NeoAdmin.getInstance().mm().deserialize("<yellow>Админ-режим выключен. Данные восстановлены."));
        }
    }

    private static class PlayerSnapshot {
        ItemStack[] inv;
        double health;
        int food;
        GameMode gm;

        PlayerSnapshot(Player p) {
            this.inv = p.getInventory().getContents();
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