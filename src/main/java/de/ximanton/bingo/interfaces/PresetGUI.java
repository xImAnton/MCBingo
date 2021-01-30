package de.ximanton.bingo.interfaces;

import de.ximanton.bingo.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class PresetGUI implements Listener {
    private String presetName;
    Main plugin;
    Inventory inv;

    public PresetGUI(Main plugin, String name) {
        this.presetName = name;
        this.plugin = plugin;
        inv = getEmptyInventory(name);
        fillInventory();
    }

    private void fillInventory() {
        if (checkPresetName(presetName)) {
            for (String mat : plugin.getConfig().getStringList("presets." + presetName)) {
                try {
                    inv.addItem(new ItemStack(Material.valueOf(mat)));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

    private boolean checkPresetName(String name) {
        return plugin.getConfig().getConfigurationSection("presets").getKeys(false).contains(name);
    }

    public Inventory getEmptyInventory(String presetName) {
        Inventory emptyInv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Preset bearbeiten: " + ChatColor.YELLOW + presetName);
        emptyInv.setItem(45, createGuiItem(Material.ARROW, "←"));
        emptyInv.setItem(53, createGuiItem(Material.ARROW, "→"));
        emptyInv.setItem(48, createGuiItem(Material.RED_TERRACOTTA, ChatColor.RED + "Abbrechen"));
        emptyInv.setItem(50, createGuiItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Speichern"));
        ItemStack emptySlot = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ");
        emptyInv.setItem(46, emptySlot);
        emptyInv.setItem(47, emptySlot);
        emptyInv.setItem(49, emptySlot);
        emptyInv.setItem(51, emptySlot);
        emptyInv.setItem(52, emptySlot);
        return emptyInv;
    }

    private static ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInv() {
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().startsWith(ChatColor.BLUE + "Preset bearbeiten: " + ChatColor.YELLOW)) {
            int slot = e.getRawSlot();
            if (slot <= 54 & slot >= 45) {
                e.setCancelled(true);
            }
            if (slot == 50) {
                ArrayList<String> mats = new ArrayList<>();
                FileConfiguration config = plugin.getConfig();
                for (int i = 0;i<45;i++) {
                    ItemStack item = getInv().getItem(i);
                    if (item == null | mats.contains(item.getType().toString())) { continue; }
                    mats.add(item.getType().toString());
                }
                config.set("presets.easy", Arrays.asList(mats.toArray()));
                plugin.saveConfig();
            }
        }
    }
}
