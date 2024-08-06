package me.rthom.armoury.utils;

import me.rthom.armoury.Armoury;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ArmouryUtils {

    // Save armouries data to file, connects each set of data to player UUID
    public static void saveArmouries() {
        for (Map.Entry<String, ItemStack[]> entry : Armoury.getInstance().armouries.entrySet()) {
            Armoury.getInstance().getConfig().set("armoury_data." + entry.getKey(), entry.getValue());
        }
        Armoury.getInstance().saveConfig();
    }

    public static void restoreArmouries() {
        ConfigurationSection armouryConfig = Armoury.getInstance().getConfig().getConfigurationSection("armoury_data");
        if (armouryConfig == null) {
            return;
        }
        armouryConfig.getKeys(false)
                .forEach(key -> {
                    ItemStack[] content = ((List<ItemStack[]>) Armoury.getInstance().getConfig().get("armoury_data." + key)).toArray(new ItemStack[0]);
                    Armoury.getInstance().armouries.put(key, content);
                });
    }
}