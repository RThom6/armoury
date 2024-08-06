package me.rthom.armoury.gui;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.helpers.ArmouryHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class ArmouryGUI {

    public static void createArmouryGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9 * 6, "Armoury Menu");

        Map<String, ItemStack[]> playerArmouries = (Map<String, ItemStack[]>) Armoury.getData("Armouries");

        if (playerArmouries == null) {
            playerArmouries = new HashMap<>();
            Armoury.armouriesData.put("player_armouries", playerArmouries);
        }

        if (playerArmouries.containsKey(player.getUniqueId().toString())) {
            inv.setContents(playerArmouries.get(player.getUniqueId().toString()));
        } else {
            ArmouryHelper.createArmouryInventory(inv, player);
            playerArmouries.put(player.getUniqueId().toString(), inv.getContents());
            player.openInventory(inv);
        }
        player.setMetadata("ArmouryGUI", new FixedMetadataValue(Armoury.getInstance(), "Armoury Menu"));
    }
}
