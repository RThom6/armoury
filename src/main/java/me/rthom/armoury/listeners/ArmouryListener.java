package me.rthom.armoury.listeners;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.buttons.Button;
import me.rthom.armoury.gui.ArmouryGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class ArmouryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        if (!(event.getClickedInventory() == player.getOpenInventory().getTopInventory()))
            return;

        if (event.getCurrentItem() == null) {
            event.setCancelled(true);
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

    public void checkButtonPressed(InventoryClickEvent event) {
        Map<Integer, Button> backgroundButtons = ArmouryGUI.getBackgroundMap();

        if (backgroundButtons.containsKey(event.getSlot())) {
            event.setCancelled(true);
            return;
        }

        Map<Integer, Button> buttons = ArmouryGUI.getButtonMap();

        if (buttons.containsKey(event.getSlot())) {
            event.setCancelled(true);
            buttons.get(event.getSlot()).onClick((Player) event.getWhoClicked());
        }
    }
}
