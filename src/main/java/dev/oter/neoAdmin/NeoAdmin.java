package dev.oter.neoAdmin;

import org.bukkit.plugin.java.JavaPlugin;

public class NeoAdmin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Регистрация команд
        AdminCommand adminCmd = new AdminCommand();
        getCommand("admin").setExecutor(adminCmd);
        getCommand("a").setExecutor(adminCmd);

        getLogger().info("NeoAdmin by oter42 успешно запущен на Arch Linux!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NeoAdmin выключен.");
    }
}