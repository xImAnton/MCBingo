package de.ximanton.bingo;

import de.ximanton.bingo.command.TeamTpCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class BingoTeam {

    public ArrayList<Player> members = new ArrayList<>();
    public ArrayList<Material> aquiredMaterials = new ArrayList<>();
    public int id;
    public ArrayList<String> offlinePlayers = new ArrayList<>();
    public Inventory backpack;
    long lastTpUse = -(TeamTpCommand.COOLDOWN * 1000);

    public BingoTeam(int id) {
        this.id = id;
    }

    public boolean joinPlayer(Player player) {
        if (this.members.size() == Main.bingoGame.settings.getTeamSize()) {
            player.sendMessage(Main.getPrefix() + "Dieses Team ist bereits voll!");
            return false;
        }
        if (!this.members.contains(player)) {
            if (Main.bingoGame.getTeam(player) != null) {
                Main.bingoGame.getTeam(player).leavePlayer(player);
            }
            members.add(player);
        } else {
            player.sendMessage(Main.getPrefix() + "Du bist bereits in Team " + this.id);
            return false;
        }
        return true;
    }

    public void leavePlayer(Player player) {
        this.members.remove(player);
    }

    public boolean isMember(Player player) {
        return this.members.contains(player);
    }

    public Inventory getInventory() {
        Inventory inv;
        inv = Bukkit.createInventory(null, Main.bingoGame.settings.getItemAmountToFind(), ChatColor.BLUE + "Bingo-Karte für " + ChatColor.YELLOW + "Team #" + this.id);
        for (int counter = 0; counter < Main.bingoGame.itemsToFind.size(); counter++) {
            Material mat = Main.bingoGame.itemsToFind.get(counter);
            if (!this.aquiredMaterials.contains(mat)) {
                inv.addItem(createGuiItem(mat, false));
            } else {
                inv.addItem(createGuiItem(mat, true));
            }
        }
        return inv;
    }

    private ItemStack createGuiItem(Material material, boolean obtained) {
        ItemStack item;
        ItemMeta meta;
        String newName = Main.getNiceName(material);
        if (!obtained) {
            item = new ItemStack(material, 1);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + newName);
            meta.setLore(Collections.singletonList("Dieses Item muss noch gefunden werden!"));
        } else {
            item = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + newName);
            meta.setLore(Collections.singletonList("Dieses Item wurde bereits gefunden!"));
        }
        item.setItemMeta(meta);
        return item;
    }

    public String getMemberString() {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : members) {
            playerNames.add(player.getDisplayName());
        }
        String playerNameString = String.join(", ", playerNames);
        if (playerNameString.isEmpty()) {
            playerNameString = "Leer";
        }
        return playerNameString;
    }

    public Inventory getBackpack() {
        return backpack;
    }

    public void sendMessage(String message, Player exclude) {
        for (Player player : members) {
            if (player != exclude) {
                player.sendMessage(message);
            }
        }
    }

    public boolean isFull() {
        return members.size() >= Main.bingoGame.settings.getTeamSize();
    }

    public long getLastTpUse() {
        return lastTpUse;
    }

    public void setLastTpUse() {
        this.lastTpUse = System.currentTimeMillis();
    }

}
