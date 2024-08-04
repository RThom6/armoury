package me.rthom.armoury.helpers;

import me.rthom.armoury.utils.ItemUtils;
import org.bukkit.inventory.Inventory;

public class ArmouryHelper {
    public static void setArmouryInventory(Inventory inv) {
        int[] slotArray = {
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 2, 2, 2, 0, 0, 0,
                0, 0, 0, 0, 4, 0, 0, 0, 0,
        };

        for (int i = 0; i < slotArray.length; i++) {
            if (slotArray[i] == 1)
                inv.setItem(i, ItemUtils.createUnusableSlot());
            if (slotArray[i] == 4)
                inv.setItem(i, ItemUtils.createCloseButton());
        }
    }


}
