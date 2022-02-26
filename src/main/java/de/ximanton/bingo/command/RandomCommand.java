package de.ximanton.bingo.command;

import de.ximanton.bingo.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RandomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        int randomPlayer = new Random().nextInt(Bukkit.getOnlinePlayers().size());
        Player random = (Player) Bukkit.getOnlinePlayers().toArray()[randomPlayer];
        Bukkit.broadcastMessage(Main.getPrefix() + "Random Spieler: " + random.getDisplayName());
        return false;
    }

}
