package me.rthom.armoury.listeners;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.Keys;
import me.rthom.armoury.buttons.Button;
import me.rthom.armoury.gui.ArmouryGUI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Map;

public class ArmouryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        if (!(event.getClickedInventory() == player.getOpenInventory().getTopInventory())){
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.setCancelled(true);
            }
            return;
        }

        if (event.getCurrentItem() == null) {
            event.setCancelled(true);
            return;
        }

        if (checkTrinket(event)) {
            return;
        }

        checkButtonPressed(event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        Inventory clicked = event.getInventory();

        if (clicked == player.getOpenInventory().getTopInventory()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        if (event.getView().getTitle().equalsIgnoreCase("Armoury Menu")) {
            Armoury.getInstance().armouries.put(event.getPlayer().getUniqueId().toString(), event.getInventory().getContents());
        } else {
            return;
        }

        player.removeMetadata("ArmouryGUI", Armoury.getInstance());
    }

    // Check if vice versa
    private boolean checkTrinket(InventoryClickEvent event) {
        if (!(event.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {
            return false;
        }

        ItemStack item = event.getCurrentItem();

        if (!itemHasKey(item, Keys.TRINKETS_SLOT)) {
            return false;
        }

        ItemStack heldItem = event.getCursor();

        if (!itemHasKey(heldItem, Keys.TRINKETS_WEAPON)) {
            return false;
        } else {
            Player player = (Player) event.getWhoClicked();
            removeItem(item, player);
        }

        return true;
    }

    private boolean itemHasKey(ItemStack item, NamespacedKey key) {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            throw new NullPointerException("Item meta is null");
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        return container.has(key);
    }

    // Irrelevant?
    private void removeItem(ItemStack item, Player player) {
        player.getInventory().remove(item);
    }

    public void checkButtonPressed(InventoryClickEvent event) {
        Map<Integer, Button> backgroundButtons = ArmouryGUI.getBackgroundMap();

        if (backgroundButtons.containsKey(event.getSlot())) {
            event.setCancelled(true);
            return;
        }

        Map<Integer, Button> buttons = ArmouryGUI.getButtonMap();

        if (buttons.containsKey(event.getSlot())) {
            buttons.get(event.getSlot()).onClick((Player) event.getWhoClicked());
        }

        event.setCancelled(true);
    }
}
