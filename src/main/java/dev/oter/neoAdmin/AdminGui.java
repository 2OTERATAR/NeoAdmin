package dev.oter.neoAdmin; // Теперь совпадает с остальными файлами

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class AdminGui implements Listener {
    private final NeoAdmin plugin; // Теперь импорт не нужен, так как классы в одном пакете
    private final MiniMessage mm = MiniMessage.miniMessage();

    public AdminGui(NeoAdmin plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, mm.deserialize("<gold>NeoAdmin Control"));

        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(p);
                meta.displayName(mm.deserialize("<yellow>" + p.getName()));
                head.setItemMeta(meta);
            }
            inv.addItem(head);
        }
        admin.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        // Проверка заголовка
        String title = PlainTextComponentSerializer.plainText().serialize(e.getView().title());
        if (title.equals("NeoAdmin Control")) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null || e.getCurrentItem().getType() != Material.PLAYER_HEAD) return;

            Player admin = (Player) e.getWhoClicked();

            // Логика клика: Телепорт к игроку (ПКМ или ЛКМ - неважно)
            SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
            if (meta != null && meta.getOwningPlayer() != null) {
                Player target = Bukkit.getPlayer(meta.getOwningPlayer().getUniqueId());
                if (target != null) {
                    admin.teleport(target);
                    admin.sendMessage("§a[NeoAdmin] Вы телепортированы к " + target.getName());
                }
            }
        }
    }
}