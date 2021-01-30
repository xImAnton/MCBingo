package de.ximanton.bingo.listener;

import de.ximanton.bingo.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SettingsListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!Main.bingoGame.settings.isPvP()) {
            if (e.getDamager().getType() == EntityType.PLAYER & e.getEntityType() == EntityType.PLAYER) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (!Main.bingoGame.settings.isHunger() & e.getEntity() instanceof Player) {
            ((Player) e.getEntity()).setSaturation(20f);
            e.setCancelled(true);
        }
    }
}
