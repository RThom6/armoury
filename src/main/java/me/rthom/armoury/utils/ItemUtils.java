package me.rthom.armoury.utils;

import me.rthom.armoury.Keys;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

    public static ItemStack createCloseButton() {
        ItemStack closeMenu = createNamedItem(Material.BARRIER, "Close", ChatColor.RED);

        setLore(closeMenu, "Click to leave menu");

        ItemMeta meta = closeMenu.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(Keys.CLOSE_MENU, PersistentDataType.BOOLEAN, true);
            closeMenu.setItemMeta(meta);
        }

        return closeMenu;
    }

    public static ItemStack createUnusableSlot() {
        ItemStack closeMenu = createNamedItem(Material.RED_STAINED_GLASS_PANE, "Unusable", ChatColor.RED);

        ItemUtils.setLore(closeMenu, "Cannot store item here");

        ItemMeta meta = closeMenu.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(Keys.UNCLICKABLE, PersistentDataType.BOOLEAN, true);
            closeMenu.setItemMeta(meta);
        }

        return closeMenu;
    }
}
