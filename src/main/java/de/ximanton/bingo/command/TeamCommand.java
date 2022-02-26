package de.ximanton.bingo.command;

import de.ximanton.bingo.BingoTeam;
import de.ximanton.bingo.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Main.bingoGame.settings.isRandomiseTeams()) {
            sender.sendMessage(Main.getPrefix() + "Für dieses Spiel sind zufällige Teams aktiviert. Du wirst bei Spielstart einem Team zugewiesen.");
            return false;
        } else {
            sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Bingo Teams");
        }
        for (BingoTeam team : Main.bingoGame.teams) {
            String playerNameString = team.getMemberString();
            String teamString = ChatColor.GRAY + "| Team " + ChatColor.YELLOW + "#" + team.id + ": " + ChatColor.GREEN + playerNameString;
            if (!team.offlinePlayers.isEmpty()) {
                teamString += " " + ChatColor.GRAY + "Offline: " + String.join(", ", team.offlinePlayers);
            }
            sender.sendMessage(teamString);
        }
        return false;
    }

}
