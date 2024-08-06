package me.rthom.armoury.gui;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.buttons.Button;
import me.rthom.armoury.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class ArmouryGUI {
    private static Map<Integer, Button> buttonMap = new HashMap<>();
    private Map<Integer, Button> backgroundButtonMap = new HashMap<>();

    public void createArmouryGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9 * 6, "Armoury Menu");

        Map<String, ItemStack[]> armouries = Armoury.getInstance().armouries;

        if (armouries.containsKey(player.getUniqueId().toString())) {
            inv.setContents(armouries.get(player.getUniqueId().toString()));
            repopulateArmouryInventory(inv);
        } else {
            createNewArmouryInventory(inv);
            armouries.put(player.getUniqueId().toString(), inv.getContents());
        }
        player.setMetadata("ArmouryGUI", new FixedMetadataValue(Armoury.getInstance(), this));
        player.openInventory(inv);
    }

    private void createNewArmouryInventory(Inventory inv) {
        int[] slotArray = {
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 2, 2, 2, 0, 0, 0,
                0, 0, 0, 0, 4, 0, 0, 0, 0,
        };

        for (int i = 0; i < slotArray.length; i++) {
            if (slotArray[i] == 1) {
                inv.setItem(i, createBackgroundButton(i).getItem());
            }
            if (slotArray[i] == 4) {
                inv.setItem(i, createCloseButton(i).getItem());
            }
        }
    }

    private void repopulateArmouryInventory(Inventory inv) {
        int[] slotArray = {
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1,
                0, 0, 0, 2, 2, 2, 0, 0, 0,
                0, 0, 0, 0, 4, 0, 0, 0, 0,
        };

        for (int i = 0; i < slotArray.length; i++) {
            if (slotArray[i] == 1) {
                inv.setItem(i, createBackgroundButton(i).getItem());
                backgroundButtonMap.put(i, createBackgroundButton(i));
            }
            if (slotArray[i] == 4) {
                inv.setItem(i, createCloseButton(i).getItem());
                buttonMap.put(i, createCloseButton(i));
            }
        }
    }

    public Button createCloseButton(int slot) {
        ItemStack button = ItemUtils.createNamedItem(Material.BARRIER, "Close", ChatColor.RED);

        Button closeButton = new Button(slot, button) {
            @Override
            public ItemStack getItem() {
                return ItemUtils.createNamedItem(Material.BARRIER, "Close", ChatColor.RED);
            }

            @Override
            public void onClick(Player player) {
                Bukkit.getScheduler().runTaskLater(Armoury.getInstance(), player::closeInventory, 1);
            }
        };

        buttonMap.put(slot, closeButton);

        return closeButton;
    }

    public Button createBackgroundButton(int slot) {
        ItemStack button = ItemUtils.createNamedItem(Material.RED_STAINED_GLASS_PANE, "Unusable", ChatColor.RED);

        final Button backgroundButton = new Button(slot, button) {
            @Override
            public void onClick(Player player) {
                // Do nothing
            }
        };

        backgroundButtonMap.put(slot, backgroundButton);

        return backgroundButton;
    }

    public Button createArmourySlot(int slot, String armourPiece) {
        ItemStack button = ItemUtils.createNamedItem(Material.WHITE_STAINED_GLASS_PANE, "armourPiece", ChatColor.DARK_PURPLE);

        final Button armourSlot = new Button(slot, button) {
            @Override
            public void onClick(Player player) {

            }
        };

        return armourSlot;
    }

    public static Map<Integer, Button> getButtonMap() {
        return buttonMap;
    }
}
