package de.ximanton.bingo.listener;

import de.ximanton.bingo.Main;
import de.ximanton.bingo.commands.BingoTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            checkItem(event.getItem().getItemStack(), (Player) event.getEntity());
        }
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent event) {
        checkItem(event.getRecipe().getResult(), (Player) event.getWhoClicked());
    }

    @EventHandler
    public void onFurnaceTake(FurnaceExtractEvent event) {
        checkItem(new ItemStack(event.getItemType()), event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {return;}
        if (!event.getView().getTitle().startsWith(ChatColor.BLUE + "Bingo-Karte für ") & event.getClickedInventory().getType() == InventoryType.PLAYER & !event.getView().getTitle().equals(Main.bingoGame.settings.getTitle())) {
            checkItem(event.getCurrentItem(), (Player) event.getWhoClicked());
        }
    }

    private void checkItem(ItemStack item, Player player) {
        if (!Main.bingoGame.running) {
            return;
        }
        Material material = item.getType();
        if (Main.bingoGame.itemsToFind.contains(material)) {
            BingoTeam team = Main.bingoGame.getTeam(player);
            if (!team.aquiredMaterials.contains(material)) {
                Main.bingoGame.getTeam(player).aquiredMaterials.add(material);
                String itemString = Main.getNiceName(material);
                Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + player.getDisplayName() + ChatColor.GRAY + " (" + ChatColor.YELLOW + "#" + team.id + ChatColor.GRAY + ") " + "hat das Item " + ChatColor.BLUE + itemString + ChatColor.GRAY +  "gefunden! " + "[" + ChatColor.RED + team.aquiredMaterials.size() + "/" + Main.bingoGame.settings.getItemAmountToFind() + ChatColor.GRAY + "]");
                Main.timer.update();
                Main.timer.updateScoreboard();
                for (Player teamMember : team.members) {
                    teamMember.playSound(teamMember.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3F, 1F);
                }
                if (team.aquiredMaterials.size() == Main.bingoGame.settings.getItemAmountToFind()) {
                    Main.bingoGame.stop(team);
                }
            }
        }
    }
}
