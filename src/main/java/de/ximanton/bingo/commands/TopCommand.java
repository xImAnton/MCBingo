package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player & Main.bingoGame.running & Main.bingoGame.settings.isEnableTopCommand()) {
            Player player = (Player) sender;
            Location playerLocation = player.getLocation();
            World world = player.getWorld();
            boolean foundLocation = false;
            for (playerLocation.setY(playerLocation.getY()+1);playerLocation.getY() < 256;playerLocation.setY(playerLocation.getY()+1)) {
                Block block = world.getBlockAt(playerLocation);
                if ((block.getType() == Material.AIR || block.getType() == Material.WATER) & (block.getRelative(BlockFace.UP).getType() == Material.AIR || block.getRelative(BlockFace.UP).getType() == Material.WATER) & playerLocation.getY() >= 63) {
                    playerLocation.setY(playerLocation.getY()-1);
                    player.teleport(playerLocation);
                    foundLocation = true;
                    break;
                }
            }
            if (!foundLocation) {
                sender.sendMessage(Main.getPrefix() + "Es wurde keine geeignete Position gefunden");
            }
        }
        return false;
    }
}
