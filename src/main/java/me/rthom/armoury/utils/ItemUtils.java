package me.rthom.armoury.utils;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.Keys;
import me.rthom.armoury.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ItemUtils {
    public static ItemStack createNamedItem(Material mat, String name, ChatColor colour) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(colour.toString() + ChatColor.BOLD.toString() + name);
            item.setItemMeta(meta);
        }

        return item;
    }

    public static ItemStack createNamedItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.BOLD + name);
            item.setItemMeta(meta);
        }

        return item;
    }

    public static void setLore(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null)
            meta.setLore(Arrays.asList(ChatColor.GRAY + lore));

        item.setItemMeta(meta);
    }

    public static Button createCloseButton(int slot) {
        ItemStack button = ItemUtils.createNamedItem(Material.BARRIER, "Close", ChatColor.RED);

        return new Button(slot, button) {
            @Override
            public ItemStack getItem() {
                return ItemUtils.createNamedItem(Material.BARRIER, "Close", ChatColor.RED);
            }

            @Override
            public void onClick(Player player) {
                Bukkit.getScheduler().runTaskLater(Armoury.getInstance(), player::closeInventory, 2);
            }
        };
    }

    public static Button createBackgroundButton(int slot) {
        ItemStack button = createNamedItem(Material.RED_STAINED_GLASS_PANE, "Unusable", ChatColor.RED);

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

        return backgroundButton;
    }
}
