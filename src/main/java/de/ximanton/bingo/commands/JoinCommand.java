package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JoinCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (Main.bingoGame.settings.isRandomiseTeams()) {
                sender.sendMessage(Main.getPrefix() + "Für dieses Spiel sind zufällige Teams aktiviert. Du wirst bei Spielstart einem Team zugewiesen.");
                return false;
            }
            if (Main.bingoGame.running) {
                sender.sendMessage(Main.getPrefix() + "Du kannst dein Team während des Spiels nicht ändern!");
                return false;
            }
            if (args.length > 0) {
                try {
                    int id = Integer.parseInt(args[0]);
                    if (id > Main.bingoGame.teams.size() | id < 1) {
                        sender.sendMessage(Main.getPrefix() + "Bitte gib eine gültige Team-ID an!");
                        return false;
                    }
                    BingoTeam team = Main.bingoGame.teams.get(id-1);
                    if (!team.joinPlayer((Player) sender)) {
                        return false;
                    }
                    sender.sendMessage(Main.getPrefix() + "Du bist " + ChatColor.YELLOW + "Team #" + id + ChatColor.GRAY + " beigetreten!");
                    for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                        if (otherPlayer != sender) {
                            otherPlayer.sendMessage(Main.getPrefix() + ChatColor.GREEN + ((Player) sender).getDisplayName() + ChatColor.GRAY + " ist " + ChatColor.YELLOW + "Team #" + id + ChatColor.GRAY + " beigetreten!");;
                        }
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(Main.getPrefix() + "Bitte gib eine gültige Team-ID an!");
                }
            } else {
                sender.sendMessage(Main.getPrefix() + "Bitte gib eine gültige Team-ID an!");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> teamIds = new ArrayList<>();
        for (BingoTeam team : Main.bingoGame.teams) {
            if (team.members.size() < Main.bingoGame.settings.getTeamSize()) {
                teamIds.add(Integer.toString(team.id));
            }
        }
        return teamIds;
    }
}
