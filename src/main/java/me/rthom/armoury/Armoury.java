package me.rthom.armoury;

import me.rthom.armoury.commands.ArmouryCommand;
import me.rthom.armoury.listeners.ArmouryListener;
import me.rthom.armoury.utils.ArmouryUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Armoury extends JavaPlugin {
    public static Map<String, ItemStack[]> armouries = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ArmouryListener(), this);

        saveDefaultConfig();

        ArmouryUtils.restoreArmouries();

        getCommand("armoury").setExecutor(new ArmouryCommand());
    }

    @Override
    public void onDisable() {
        if (!armouries.isEmpty()) {
            ArmouryUtils.saveArmouries();
        }
    }

    public static Armoury getInstance() {
        return getPlugin(Armoury.class);
    }
}
