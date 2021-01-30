package de.ximanton.bingo.listener;

import de.ximanton.bingo.Main;
import de.ximanton.bingo.commands.BingoTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (Bukkit.getOnlinePlayers().size() > Main.bingoGame.settings.getServerSlots()) {
            event.setQuitMessage(null);
            return;
        }
        event.setQuitMessage(ChatColor.RED + "<< " + ChatColor.GRAY + event.getPlayer().getDisplayName());
        Player player = event.getPlayer();
        BingoTeam team = Main.bingoGame.getTeam(player);
        if (team != null) {
            team.leavePlayer(player);
            team.offlinePlayers.add(player.getDisplayName());
        }
    }
}
