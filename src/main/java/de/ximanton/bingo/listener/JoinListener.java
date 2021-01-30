package de.ximanton.bingo.listener;

import de.ximanton.bingo.Main;
import de.ximanton.bingo.commands.BingoTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Bukkit.getOnlinePlayers().size() > Main.bingoGame.settings.getServerSlots()) {
            event.getPlayer().kickPlayer(Main.getPrefix() + "Momentan sind nur " + Main.bingoGame.settings.getServerSlots() + " Spieler auf dem Server erlaubt.");
        }
        event.setJoinMessage(ChatColor.GREEN + ">> " + ChatColor.GRAY + event.getPlayer().getDisplayName());
        event.getPlayer().sendMessage(Main.getPrefix() + "Willkommen auf dem Bingo-Server von xImAnton_");
        Player player = event.getPlayer();
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        for (BingoTeam team : Main.bingoGame.teams) {
            if (team.offlinePlayers.contains(player.getDisplayName())) {
                team.joinPlayer(player);
                team.offlinePlayers.remove(player.getDisplayName());
                player.sendMessage(Main.getPrefix() + "Du wurdest deinem Team wieder hinzugefügt!");
            }
        }
    }
}
