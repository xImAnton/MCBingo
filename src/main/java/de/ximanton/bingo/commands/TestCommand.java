package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import de.ximanton.bingo.interfaces.PresetGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    Main plugin;

    public TestCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PresetGUI gui = new PresetGUI(plugin, "easy");
        ((Player) sender).openInventory(gui.getInv());
        return false;
    }
}
