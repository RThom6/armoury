package me.rthom.armoury.gui;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.Keys;
import me.rthom.armoury.buttons.Button;
import me.rthom.armoury.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class ArmouryGUI {
    private Map<Integer, Button> buttonMap = new HashMap<>();
    private Map<Integer, Button> backgroundButtonMap = new HashMap<>();

    public void createArmouryGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9 * 6, "Armoury Menu");

        Map<String, ItemStack[]> armouries = Armoury.getInstance().armouries;

        if (armouries.containsKey(player.getUniqueId().toString())) {
            inv.setContents(armouries.get(player.getUniqueId().toString()));
            restoreButtonsFromInventory(inv);
        } else {
            createArmouryInventory(inv, player);
            armouries.put(player.getUniqueId().toString(), inv.getContents());
        }
        player.setMetadata("ArmouryGUI", new FixedMetadataValue(Armoury.getInstance(), this));
        player.openInventory(inv);
    }

    public void createArmouryInventory(Inventory inv, Player player) {
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

    private void restoreButtonsFromInventory(Inventory inv) {
        ItemStack[] items = inv.getContents();
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getPersistentDataContainer().has(Keys.UNCLICKABLE, PersistentDataType.BOOLEAN)) {
                    backgroundButtonMap.put(i, createBackgroundButton(i));
                } else if (meta != null && meta.getPersistentDataContainer().has(Keys.CLOSE_MENU, PersistentDataType.BOOLEAN)) {
                    Bukkit.getLogger().info("Added close button!");
                    buttonMap.put(i, createCloseButton(i));
                } else {
                    Bukkit.getLogger().info("Failed to add" + meta.getDisplayName());
                }
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

        ItemMeta meta = button.getItemMeta();

        if (meta != null) { // Isn't actually persistent
            meta.getPersistentDataContainer().set(Keys.CLOSE_MENU, PersistentDataType.BOOLEAN, true);
            button.setItemMeta(meta);
        }

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

        ItemMeta meta = button.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(Keys.UNCLICKABLE, PersistentDataType.BOOLEAN, true);
            button.setItemMeta(meta);
        }

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

    public Map<Integer, Button> getButtonMap() {
        return buttonMap;
    }
}
