package de.ximanton.bingo.preset;

import de.ximanton.bingo.gui.BingoSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Comparator;
import java.util.List;

public class PresetGUI {

    public static final String GUI_TITLE = ChatColor.BLUE + "Preset Konfiguration";
    private static final byte[] BORDER_SLOTS = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 22, 31, 36, 40, 44, 45, 46, 47, 48, 50, 51, 52, 53};
    public static final int[] LEFT_SLOTS = new int[]{10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39};
    public static final int[] RIGHT_SLOTS = new int[]{14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43};

    private final Inventory gui;

    private int leftScroll;
    private int rightScroll;
    private Preset[] leftPresets;
    private Preset[] rightPresets;

    public PresetGUI() {
        this.gui = Bukkit.createInventory(null, 54, GUI_TITLE);
    }

    public void update() {
        gui.clear();

        for (byte slot : BORDER_SLOTS) {
            gui.setItem(slot, BingoSettings.borderItem());
        }

        gui.setItem(18, scrollItem(true));
        gui.setItem(26, scrollItem(true));
        gui.setItem(27, scrollItem(false));
        gui.setItem(35, scrollItem(false));
        gui.setItem(49, BingoSettings.buttonField(
                "Preset hinzufügen",
                new String[]{"Erstellt ein neues Preset"},
                "Klicken zum öffnen.",
                Material.FURNACE
        ));
        gui.setItem(45, BingoSettings.buttonField(
                "Zurück",
                new String[]{"Wechselt zurück in die", "generellen Einstellungen"},
                "Klicken zum zurückgehen.",
                Material.ARROW
        ));

        leftPresets = new Preset[12];
        rightPresets = new Preset[12];

        int slotIndex = 0;
        int presetIndex = leftScroll * 3;
        List<Preset> presets = PresetManager.getInstance().getInactivePresets().stream().sorted(Comparator.comparing(Preset::getName)).toList();

        while (slotIndex < LEFT_SLOTS.length && presetIndex < presets.size()) {
            gui.setItem(LEFT_SLOTS[slotIndex], presets.get(presetIndex).asItem(false));
            leftPresets[slotIndex] = presets.get(presetIndex);

            slotIndex++;
            presetIndex++;
        }

        slotIndex = 0;
        presetIndex = rightScroll * 3;
        presets = PresetManager.getInstance().getActivePresets().stream().sorted(Comparator.comparing(Preset::getName)).toList();

        while (slotIndex < RIGHT_SLOTS.length && presetIndex < presets.size()) {
            gui.setItem(RIGHT_SLOTS[slotIndex], presets.get(presetIndex).asItem(true));
            rightPresets[slotIndex] = presets.get(presetIndex);

            slotIndex++;
            presetIndex++;
        }
    }

    public static ItemStack scrollItem(boolean up) {
        ItemStack item = new ItemStack(
                up ? Material.LIGHT_BLUE_STAINED_GLASS_PANE : Material.BLUE_STAINED_GLASS_PANE,
                1
        );

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + (up ? "Nach oben scrollen" : "Nach unten scrollen"));
        item.setItemMeta(meta);

        return item;
    }

    public void updateLeftScroll(int update) {
        int newVal = leftScroll + update;
        int downBound = PresetManager.getInstance().getInactivePresets().size() / 3 - 3;
        if (0 <= newVal && (newVal < downBound || rightScroll > downBound)) {
            leftScroll = newVal;
        }
    }

    public void updateRightScroll(int update) {
        int newVal = rightScroll + update;
        int downBound = PresetManager.getInstance().getActivePresets().size() / 3 - 3;
        if (0 <= newVal && (newVal < downBound || rightScroll > downBound)) {
            rightScroll = newVal;
        }
    }

    public Preset[] getLeftPresets() {
        return leftPresets;
    }

    public Preset[] getRightPresets() {
        return rightPresets;
    }

    public Inventory getGUI() {
        return gui;
    }

}
