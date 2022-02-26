package de.ximanton.bingo.listener;

import de.ximanton.bingo.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BingoNotRunningListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!Main.bingoGame.running) {
            Location from = event.getFrom();
            Player player = event.getPlayer();
            if (player.getGameMode() == GameMode.CREATIVE | player.getGameMode() == GameMode.SPECTATOR) {
                return;
            }
            if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                player.teleport(event.getFrom());
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            if (!Main.bingoGame.running | !Main.bingoGame.settings.isGrantDamage()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!Main.bingoGame.running & event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!Main.bingoGame.running & event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}