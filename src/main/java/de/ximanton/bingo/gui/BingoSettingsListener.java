package de.ximanton.bingo.gui;

import de.ximanton.bingo.Main;
import de.ximanton.bingo.preset.PresetManager;
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
            if (slot == -999) {
                return;
            }
            if (!player.hasPermission("bingo.modify")) {
                player.sendMessage(Main.getPrefix() + "Du hast keine Berechtigung, um die Einstellungen zu ändern.");
                return;
            }
            BingoSettings settings = Main.bingoGame.settings;
            Inventory inv = settings.getPage();
            if (lastClick + 150 > System.currentTimeMillis()) {
                return;
            }
            lastClick = System.currentTimeMillis();

            if (slot == 49) {
                player.closeInventory();
            } else if (slot == 22) {
                settings.setShowTimer(!settings.isShowTimer());
                inv.setItem(slot, BingoSettings.toggleField("Timer", settings.isShowTimer()));
            } else if (slot == 21) {
                settings.setRespawnPlayers(!settings.isRespawnPlayers());
                inv.setItem(slot, BingoSettings.toggleField("Keep Inventory", settings.isRespawnPlayers()));
            } else if (slot == 23) {
                settings.setHunger(!settings.isHunger());
                inv.setItem(slot, BingoSettings.toggleField("Hunger", settings.isHunger()));
            } else if (slot == 24) {
                settings.setPvP(!settings.isPvP());
                inv.setItem(slot, BingoSettings.toggleField("PvP", settings.isPvP()));
            } else if (slot == 25) {
                settings.setAnnounceAdvancements(!settings.isAnnounceAdvancements());
                inv.setItem(slot, BingoSettings.toggleField("Announce Advancements", settings.isAnnounceAdvancements()));
            } else if (slot == 30) {
                settings.setRandomiseTeams(!settings.isRandomiseTeams());
                inv.setItem(slot, BingoSettings.toggleField("Zufällige Teams", settings.isRandomiseTeams()));
            } else if (slot == 31) {
                settings.setSpreadTeams(!settings.isSpreadTeams());
                inv.setItem(slot, BingoSettings.toggleField("Teams verteilen", settings.isSpreadTeams()));
            } else if (slot == 32) {
                settings.setTeamTeleport(!settings.isTeamTeleport());
                inv.setItem(slot, BingoSettings.toggleField("Team-Teleport", settings.isTeamTeleport()));
            } else if (slot == 33) {
                settings.setGrantDamage(!settings.isGrantDamage());
                inv.setItem(slot, BingoSettings.toggleField("Schaden", settings.isGrantDamage()));
            } else if (slot == 34) {
                settings.setEnableTopCommand(!settings.isEnableTopCommand());
                inv.setItem(slot, BingoSettings.toggleField("/top Command", settings.isEnableTopCommand()));
            } else if (slot == 19) {
                int newAmount = handleCounterComponent(e, BingoSettings.getTeamBackpackSizeValues(), "Team-Backpack Größe", BingoSettings.getTeamBackpackSizeValues().indexOf(settings.getTeamBackpackSize()));
                if (newAmount != -999) {
                    settings.setTeamBackpackSize(newAmount);
                }
            } else if (slot == 20) {
                int newAmount = handleCounterComponent(e, BingoSettings.getTeamSizeValues(), "Team Größe", BingoSettings.getTeamSizeValues().indexOf(settings.getTeamSize()));
                if (newAmount != -999) {
                    settings.setTeamSize(newAmount);
                }
            } else if (slot == 28) {
                int newAmount = handleCounterComponent(e, BingoSettings.getServerSlotValues(), "Server Slots", BingoSettings.getServerSlotValues().indexOf(settings.getServerSlots()));
                if (newAmount != -999) {
                    settings.setServerSlots(newAmount);
                }
            } else if (slot == 29) {
                int newAmount = handleCounterComponent(e, BingoSettings.getBingoItemsValues(), "Bingo-Items", BingoSettings.getBingoItemsValues().indexOf(settings.getItemAmountToFind()));
                if (newAmount != -999) {
                    settings.setItemAmountToFind(newAmount);
                }
            } else if (slot < 54 && slot > 50) {
                if (!player.hasPermission("bingo.start")) {
                    player.sendMessage(Main.getPrefix() + "Du hast keine Berechtigung, um das Spiel zu starten.");
                    return;
                }
                if (PresetManager.getInstance().getUniqueItems().size() < Main.bingoGame.settings.getItemAmountToFind()) {
                    player.sendMessage(Main.getPrefix() + "Es muss mind. 1 Preset mit mind. " + Main.bingoGame.settings.getItemAmountToFind() + " Items ausgewählt sein.");
                    return;
                }

                Main.bingoGame.startGame();
                player.closeInventory();
            } else if (slot < 48 && slot > 44) {
                player.openInventory(PresetManager.getInstance().getGUI().getGUI());
            }
        }
    }

    private int handleCounterComponent(InventoryClickEvent e, ArrayList<Integer> values, String title, int currentIndex) {
        BingoSettings settings = Main.bingoGame.settings;
        boolean updated = false;
        if (e.getClick() == ClickType.LEFT) {
            // last item in list
            if (currentIndex == values.size() - 1) {
                return -999;
            }
            currentIndex++;
            updated = true;
        } else if (e.getClick() == ClickType.SHIFT_LEFT) {
            // first item
            if (currentIndex < 1) {
                return -999;
            }
            currentIndex--;
            updated = true;
        }
        int newAmount = values.get(currentIndex);
        if (updated) {
            settings.getPage().setItem(e.getRawSlot(), BingoSettings.counterField(title, newAmount));
        }
        return newAmount;
    }

}
