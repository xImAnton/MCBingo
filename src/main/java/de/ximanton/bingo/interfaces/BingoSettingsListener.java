package de.ximanton.bingo.interfaces;

import de.ximanton.bingo.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class BingoSettingsListener implements Listener {
    long lastClick;
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(Main.bingoGame.settings.getTitle())) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            int slot = e.getRawSlot();
            if (Main.bingoGame.running & slot != 49) {
                player.sendMessage(Main.getPrefix() + "Du kannst die Einstellungen nicht modifizieren, während das Spiel läuft!");
                return;
            }
            if (slot == -999) { return; }
            if (!player.hasPermission("bingo.modify")) {
                player.sendMessage(Main.getPrefix() + "Du hast keine Berechtigung, um die Einstellungen zu ändern.");
                return;
            }
            BingoSettings settings = Main.bingoGame.settings;
            Inventory inv = settings.getPage(1);
            if (lastClick + 200 > System.currentTimeMillis()) {
                return;
            }
            lastClick = System.currentTimeMillis();
            if (slot == 49) {
                player.closeInventory();
            }
            if (slot == 22) {
                settings.setShowTimer(!settings.isShowTimer());
                inv.setItem(slot, settings.toggleField("Timer", settings.isShowTimer()));
            }
            if (slot == 21) {
                settings.setRespawnPlayers(!settings.isRespawnPlayers());
                inv.setItem(slot, settings.toggleField("Keep Inventory", settings.isRespawnPlayers()));
            }
            if (slot == 23) {
                settings.setHunger(!settings.isHunger());
                inv.setItem(slot, settings.toggleField("Hunger", settings.isHunger()));
            }
            if (slot == 24) {
                settings.setPvP(!settings.isPvP());
                inv.setItem(slot, settings.toggleField("PvP", settings.isPvP()));
            }
            if (slot == 25) {
                settings.setAnnounceAdvancements(!settings.isAnnounceAdvancements());
                inv.setItem(slot, settings.toggleField("Announce Advancements", settings.isAnnounceAdvancements()));
            }
            if (slot == 30) {
                settings.setRandomiseTeams(!settings.isRandomiseTeams());
                inv.setItem(slot, settings.toggleField("Zufällige Teams", settings.isRandomiseTeams()));
            }
            if (slot == 31) {
                settings.setSpreadTeams(!settings.isSpreadTeams());
                inv.setItem(slot, settings.toggleField("Teams verteilen", settings.isSpreadTeams()));
            }
            if (slot == 32) {
                settings.setTeamTeleport(!settings.isTeamTeleport());
                inv.setItem(slot, settings.toggleField("Team-Teleport", settings.isTeamTeleport()));
            }
            if (slot == 33) {
                settings.setGrantDamage(!settings.isGrantDamage());
                inv.setItem(slot, settings.toggleField("Schaden", settings.isGrantDamage()));
            }
            if (slot == 34) {
                settings.setEnableTopCommand(!settings.isEnableTopCommand());
                inv.setItem(slot, settings.toggleField("/top Command", settings.isEnableTopCommand()));
            }
            if (slot == 19) {
                int newAmount = handleCounterComponent(e, BingoSettings.getTeamBackpackSizeValues(), "Team-Backpack Größe", BingoSettings.getTeamBackpackSizeValues().indexOf(settings.getTeamBackpackSize()));
                if (newAmount != -999) {
                    settings.setTeamBackpackSize(newAmount);
                }
            }
            if (slot == 20) {
                int newAmount = handleCounterComponent(e, BingoSettings.getTeamSizeValues(), "Team Größe", BingoSettings.getTeamSizeValues().indexOf(settings.getTeamSize()));
                if (newAmount != -999) {
                    settings.setTeamSize(newAmount);
                }
            }
            if (slot == 28) {
                int newAmount = handleCounterComponent(e, BingoSettings.getServerSlotValues(), "Server Slots", BingoSettings.getServerSlotValues().indexOf(settings.getServerSlots()));
                if (newAmount != -999) {
                    settings.setServerSlots(newAmount);
                }
            }
            if (slot == 29) {
                int newAmount = handleCounterComponent(e, BingoSettings.getBingoItemsValues(), "Bingo-Items", BingoSettings.getBingoItemsValues().indexOf(settings.getItemAmountToFind()));
                if (newAmount != -999) {
                    settings.setItemAmountToFind(newAmount);
                }
            }
            if (slot < 54 & slot > 50) {
                if (!player.hasPermission("bingo.start")) {
                    player.sendMessage(Main.getPrefix() + "Du hast keine Berechtigung, um das Spiel zu starten.");
                    return;
                }
                if (!Main.bingoGame.getItemsList().isEmpty() & !(Main.bingoGame.getItemsList().size() < Main.bingoGame.settings.getItemAmountToFind())) {
                    Main.bingoGame.startGame();
                    player.closeInventory();
                } else {
                    player.sendMessage(Main.getPrefix() + "Es muss mind. 1 Preset mit mind. " + Main.bingoGame.settings.getItemAmountToFind() + " Items ausgewählt sein.");
                }
            }
        }
    }

    private int handleCounterComponent(InventoryClickEvent e, ArrayList<Integer> values, String title, int currentIndex) {
        int newAmount = 0;
        BingoSettings settings = Main.bingoGame.settings;
        boolean actualised = false;
        if (e.getClick() == ClickType.LEFT) {
            if (currentIndex == values.size()-1) {
                return -999;
            }
            currentIndex++;
            actualised = true;
        }
        if (e.getClick() == ClickType.SHIFT_LEFT) {
            if (currentIndex < 1) {
                return -999;
            }
            currentIndex--;
            actualised = true;
        }
        if (actualised) {
            newAmount = values.get(currentIndex);
            settings.getPage(1).setItem(e.getRawSlot(), settings.counterField(title, newAmount));
        }
        return newAmount;
    }
}
