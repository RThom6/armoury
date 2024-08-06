package me.rthom.armoury.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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


}