package me.rthom.armoury.helpers;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.buttons.Button;
import me.rthom.armoury.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class ArmouryHelper {
    public static void createArmouryInventory(Inventory inv, Player player) {
        int[] slotArray = {
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 2, 2, 2, 0, 0, 0,
                0, 0, 0, 0, 4, 0, 0, 0, 0,
        };

        HashMap<Integer, Button> buttons = new HashMap<>();
        HashMap<Integer, Button> backgroundButtons = new HashMap<>();

        for (int i = 0; i < slotArray.length; i++) {
            if (slotArray[i] == 1) {
                Button backgroundButton = ItemUtils.createBackgroundButton(i);
                backgroundButtons.put(i, backgroundButton);
                inv.setItem(i, backgroundButton.getItem());
            }
            if (slotArray[i] == 4) {
                Button closeButton = ItemUtils.createCloseButton(i);
                inv.setItem(i, closeButton.getItem());
                buttons.put(i, closeButton);
            }
        }

        Map<String, HashMap<Integer, Button>> armouryButtons = (Map<String, HashMap<Integer, Button>>) Armoury.getData("buttons");
        if (armouryButtons == null) {
            armouryButtons = new HashMap<>(new HashMap<>());
            Armoury.armouriesData.put("buttons", armouryButtons);
        }
        armouryButtons.put(player.getUniqueId().toString(), buttons);

        Map<String, HashMap<Integer, Button>> armouryBackgrounds = (Map<String, HashMap<Integer, Button>>) Armoury.getData("backgroundButtons");
        if (armouryBackgrounds == null) {
            armouryBackgrounds = new HashMap<>(new HashMap<>());
            Armoury.armouriesData.put("backgroundButtons", armouryBackgrounds);
        }
        armouryBackgrounds.put(player.getUniqueId().toString(), backgroundButtons);
    }


}
