package me.rthom.armoury.utils;

import me.rthom.armoury.Armoury;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class ArmouryUtils {

    // Save armouries data to file, connects each set of data to player UUID
    public static void saveArmouries() {
        for (Map.Entry<String, Map<?, ?>> entry : Armoury.getInstance().armouriesData.entrySet()) {
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
                    ConfigurationSection section = armouryConfig.getConfigurationSection(key);
                    if (section != null) {
                        Map<String, Object> data = section.getValues(false);
                        Armoury.armouriesData.put(key, data);
                    }
                });
    }
}