package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamTpCommand implements CommandExecutor {
    public static final int cooldown = 120;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player & Main.bingoGame.running & Main.bingoGame.settings.isTeamTeleport() & Main.bingoGame.settings.getTeamSize() > 1) {
            Player player = (Player) sender;
            BingoTeam team = Main.bingoGame.getTeam(player);
            if (team == null) { return false; }
            if (team.getLastTpUse() + (cooldown*1000) > System.currentTimeMillis()) {
                long nextUse = cooldown*1000 - (System.currentTimeMillis() - (team.getLastTpUse()));
                sender.sendMessage(Main.getPrefix() + "Diese funktion hat einen Cooldown. Nächste Benutzung in " + nextUse/1000 + " Sekunden");
                return false;
            }
            int index = team.members.indexOf(player);
            if (index == team.members.size()-1) {
                index--;
            } else {
                index++;
            }
            Player playerToTeleport = team.members.get(index);
            player.teleport(playerToTeleport);
            team.setLastTpUse();
            sender.sendMessage(Main.getPrefix() + "Du wurdest zu " + ChatColor.GREEN + playerToTeleport.getDisplayName() + ChatColor.GRAY + " teleportiert");
            team.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "Team-TP" + ChatColor.GRAY + "] " + ChatColor.GREEN + player.getDisplayName() + ChatColor.GRAY + " hat sich zu " + ChatColor.GREEN + playerToTeleport.getDisplayName() + ChatColor.GRAY + " teleportiert", player);
        }
        return false;
    }
}
