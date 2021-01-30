package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class BingoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Main.bingoGame.running) {
            sender.sendMessage(Main.getPrefix() + "Dieser Command funktioniert nur, wenn ein Bingo-Spiel läuft.");
            return false;
        }
        if (!(sender instanceof Player)) {
            return false;
        }
        BingoTeam team = Main.bingoGame.getTeam((Player) sender);
        if (team != null) {
            ((Player) sender).openInventory(team.getInventory());
        } else {
            sender.sendMessage(Main.getPrefix() + "Du musst in einem Team sein, um die Bingo-Karte sehen zu können!");
        }
        return false;
        }
}
