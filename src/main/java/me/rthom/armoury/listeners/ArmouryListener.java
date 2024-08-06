package me.rthom.armoury.listeners;

import me.rthom.armoury.Armoury;
import me.rthom.armoury.buttons.Button;
import me.rthom.armoury.gui.ArmouryGUI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class ArmouryListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        PersistentDataContainer data = p.getPersistentDataContainer();

        // Check if armoury exists for player, if not create one
        if (!data.has(new NamespacedKey(Armoury.getInstance(), "Armoury"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(Armoury.getInstance(), "Armoury"), PersistentDataType.STRING, "");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        Inventory clicked = event.getClickedInventory();

        if (!(clicked == player.getOpenInventory().getTopInventory()))
            return;

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            event.setCancelled(true);
            return;
        }

        Map<Integer, Button> backgroundButtons = ArmouryGUI.getBackgroundMap();

        if (backgroundButtons.containsKey(event.getSlot())) {
            event.setCancelled(true);
            return;
        }

        Map<Integer, Button> buttons = ArmouryGUI.getButtonMap();

        if (buttons.containsKey(event.getSlot())) {
            event.setCancelled(true);
            buttons.get(event.getSlot()).onClick(player);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("ArmouryGUI"))
            return;

        Inventory clicked = event.getInventory();

        if (clicked == player.getOpenInventory().getTopInventory()){
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
}
