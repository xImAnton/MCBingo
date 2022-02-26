package de.ximanton.bingo.listener;

import de.ximanton.bingo.Main;
import de.ximanton.bingo.BingoTeam;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        BingoTeam team = Main.bingoGame.getTeam(event.getPlayer());
        if (event.getMessage().startsWith("@a ") || team == null || !Main.bingoGame.running || Main.bingoGame.settings.getTeamSize() == 1) {
            String pref = "";
            if (team != null) {
                pref += ChatColor.YELLOW + "(#" + team.id + ") " + ChatColor.GRAY;
            }
            pref += ChatColor.GRAY + event.getPlayer().getDisplayName() + ": ";
            if (event.getMessage().startsWith("@a ")) {
                event.setMessage(event.getMessage().substring(3));
            }
            event.setFormat(pref + event.getMessage());
        } else {
            event.setCancelled(true);
            team.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "Teamchat" + ChatColor.GRAY + "] " + event.getPlayer().getDisplayName() + ": " + event.getMessage(), null);
        }
    }

}
