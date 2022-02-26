package de.ximanton.bingo.preset;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Preset {

    public static Preset fromConfig(String name, ConfigurationSection c) {
        HashSet<Material> materials = new HashSet<>();
        Material icon = Material.valueOf(c.getString("icon").toUpperCase());

        for (String item : c.getStringList("items")) {
            try {
                materials.add(Material.valueOf(item.toUpperCase()));
            } catch (IllegalArgumentException ignored) {
                Bukkit.getLogger().warning("Couldn't load material for value: " + item);
            }
        }

        String cfgName = c.getString("name");
        if (cfgName != null) {
            name = cfgName;
        }

        return new Preset(name, icon, materials);
    }

    private final String name;
    private final Material icon;
    private final Set<Material> items;

    public Preset(String name, Material icon) {
        this(name, icon, new HashSet<>());
    }

    public Preset(String name, Material icon, Set<Material> items) {
        this.name = name;
        this.icon = icon;
        this.items = items;
    }

    public Set<Material> getItems() {
        return items;
    }

    public ItemStack asItem() {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Preset: " + ChatColor.GREEN + name);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack asItem(boolean active) {
        ItemStack item = asItem();
        ItemMeta meta = item.getItemMeta();

        meta.setLore(List.of("", ChatColor.YELLOW + "Klicken zum " + (active ? "deaktivieren." : "aktivieren.")));

        item.setItemMeta(meta);
        return item;
    }

    public String getName() {
        return name;
    }
}
