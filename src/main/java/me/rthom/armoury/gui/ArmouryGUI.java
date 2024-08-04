package me.rthom.armoury.gui;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.helpers.ArmouryHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import static me.rthom.armoury.Armoury.armouries;

public class ArmouryGUI {
    public static void createArmouryGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9 * 6, "Armoury Menu");

        if (armouries.containsKey(player.getUniqueId().toString())) {
            inv.setContents(armouries.get(player.getUniqueId().toString()));
        } else {
            ArmouryHelper.setArmouryInventory(inv);
            armouries.put(player.getUniqueId().toString(), inv.getContents());
            player.openInventory(inv);
        }
        player.setMetadata("ArmouryGUI", new FixedMetadataValue(Armoury.getInstance(), "Armoury Menu"));
    }
}
