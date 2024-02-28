package com.summit.claw.claw;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class ClawGUI implements Listener {
    private static Inventory ClawGUI;
    private static Inventory inventory = Bukkit.createInventory(null, 9, "C.L.A.W. GUI");


    public void initializeItems() {
        // Consider slots like 4 for "Generate" and 8 for "Exit"
        inventory.setItem(4, createGuiItem(Material.PAPER, "§l§2GENERATE", "§aFirst line of the lore", "§bSecond line of the lore"));
        inventory.setItem(8, createGuiItem(Material.BARRIER, "§l§2Exit"));
    }

    public ClawGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        InventoryType type = InventoryType.BARREL;

        // Assign the new inventory to the this.inventory field
        inventory = Bukkit.createInventory(null, 9, "C.L.A.W. GUI");

        // Put the items into the inventory
        initializeItems();
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public static void openInventory(final @NotNull HumanEntity ent) {
        ent.openInventory(inventory);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final @NotNull InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player player = (Player) e.getWhoClicked();

        // Check the display name of the clicked item to determine action
        if (clickedItem.hasItemMeta() && Objects.requireNonNull(clickedItem.getItemMeta()).hasDisplayName()) {
            String displayName = clickedItem.getItemMeta().getDisplayName();

            if (displayName.equals("§l§2GENERATE")) {
                // Here you would run the generate command logic instead of sending a message
                String challenge = OpenAIRequestHandler.generateChallenge();
                player.sendMessage("Generated Challenge: " + challenge);
                // Optionally close the inventory after generating
                player.closeInventory();
            } else if (displayName.equals("§l§2Exit C.L.A.W.")) {
                player.closeInventory(); // Close the GUI
            }
        }
    }
    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final @NotNull InventoryDragEvent e) {
        if (e.getInventory().equals(inventory)) {
            e.setCancelled(true);
        }
    }
}