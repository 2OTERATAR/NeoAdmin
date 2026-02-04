package me.neo.admin;

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
    private final NeoAdmin plugin;

    public AdminGui(NeoAdmin plugin) { this.plugin = plugin; }

    public void openMenu(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.mm().deserialize("<gold>NeoAdmin Control"));
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(p);
            meta.displayName(plugin.mm().deserialize("<yellow>" + p.getName()));
            head.setItemMeta(meta);
            inv.addItem(head);
        }
        admin.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().title().equals(plugin.mm().deserialize("<gold>NeoAdmin Control"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            // Логика ПКМ (Телепорт) и ЛКМ (Меню наказаний)
        }
    }
}