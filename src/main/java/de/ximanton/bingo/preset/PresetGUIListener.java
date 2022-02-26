package de.ximanton.bingo.preset;

import de.ximanton.bingo.Main;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

public class PresetGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(PresetGUI.GUI_TITLE)) return;

        e.setCancelled(true);
        int slot = e.getRawSlot();
        Player player = (Player) e.getWhoClicked();
        if (slot == -999) {
            return;
        }
        if (!player.hasPermission("bingo.preset")) {
            player.sendMessage(Main.getPrefix() + "Du hast keine Berechtigung, um die Presets zu bearbeiten.");
            return;
        }

        PresetGUI gui = PresetManager.getInstance().getGUI();

        if (slot == 18) gui.updateLeftScroll(-1);
        else if (slot == 26) gui.updateRightScroll(-1);
        else if (slot == 27) gui.updateLeftScroll(1);
        else if (slot == 35) gui.updateRightScroll(1);
        else if (slot == 45) player.openInventory(Main.bingoGame.settings.getPage());
        else if (slot == 49) {
            // preset creation screen
        } else if (Arrays.stream(PresetGUI.LEFT_SLOTS).anyMatch(s -> s == slot)) {
            Preset clickedPreset = gui.getLeftPresets()[ArrayUtils.indexOf(PresetGUI.LEFT_SLOTS, slot)];

            if (clickedPreset != null) {
                PresetManager.getInstance().getActivePresets().add(clickedPreset);
            }
        } else if (Arrays.stream(PresetGUI.RIGHT_SLOTS).anyMatch(s -> s == slot)) {
            Preset clickedPreset = gui.getRightPresets()[ArrayUtils.indexOf(PresetGUI.RIGHT_SLOTS, slot)];

            if (clickedPreset != null) {
                PresetManager.getInstance().getActivePresets().remove(clickedPreset);
            }
        } else {
            return;
        }

        gui.update();
    }

}
