package me.rthom.armoury.utils;

import me.rthom.armoury.Armoury;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static me.rthom.armoury.Armoury.armouries;

public class ArmouryUtils {

    public static void saveArmouries(Armoury armoury) {
        for (Map.Entry<String, ItemStack[]> entry : armouries.entrySet()) {
            armoury.getConfig().set("armoury_data." + entry.getKey(), entry.getValue());
        }
        armoury.saveConfig();
    }

    public static void restoreArmouries(Armoury armoury) {
        ConfigurationSection armouryConfig = armoury.getConfig().getConfigurationSection("armoury_data");
        if (armouryConfig == null) {
            return;
        }
        armouryConfig.getKeys(false)
                .forEach(key -> {
                    ItemStack[] content = ((List<ItemStack[]>) armoury.getConfig().get("armoury_data." + key)).toArray(new ItemStack[0]);
                    armouries.put(key, content);
                });
    }
}