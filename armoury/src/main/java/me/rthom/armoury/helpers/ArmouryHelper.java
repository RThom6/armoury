package me.rthom.armoury.helpers;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmouryHelper {
    public static void setArmouryInventory(Inventory inv) {
        int[] slotArray = {
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                2, 2, 2, 3, 3, 3, 2, 2, 2,
                0, 0, 0, 0, 4, 0, 0, 0, 0,
        };

        for(int i = 0; i < slotArray.length; i++) {
            if (slotArray[i] == 1)
                inv.setItem(i, new ItemStack(Material.REDSTONE));
        }
    }
}
