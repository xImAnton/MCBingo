package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackpackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (!Main.bingoGame.running) {
            sender.sendMessage(Main.getPrefix() + "Du kannst das Team-Backpack erst benutzen, wenn das Spiel begonnen hat");
            return false;
        }
        if (Main.bingoGame.settings.getTeamBackpackSize() != 0) {
            BingoTeam team = Main.bingoGame.getTeam((Player) sender);
            if (team != null) {
                ((Player) sender).openInventory(team.getBackpack());
            } else {
                sender.sendMessage(Main.getPrefix() + "Du musst in einem Team sein, um das Team-Backpack benutzen zu können");
                return false;
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "Backpacks sind für dieses Spiel nicht aktiviert");
        }
        return false;
    }
}
