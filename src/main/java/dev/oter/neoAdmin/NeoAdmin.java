package me.neo.admin;

import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class NeoAdmin extends JavaPlugin {
    private static NeoAdmin instance;
    private AdminManager adminManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.adminManager = new AdminManager();

        getCommand("admin").setExecutor(new AdminCommand(this));
        getCommand("a").setExecutor(new StaffChat(this));

        getServer().getPluginManager().registerEvents(new AdminGui(this), this);
        getLogger().info("NeoAdmin: Система запущена на базе Paper 1.21.1");
    }

    public static NeoAdmin getInstance() { return instance; }
    public AdminManager getAdminManager() { return adminManager; }
    public MiniMessage mm() { return MiniMessage.miniMessage(); }
}