package de.ximanton.bingo;

import de.ximanton.bingo.command.*;
import de.ximanton.bingo.gui.BingoSettingsListener;
import de.ximanton.bingo.listener.*;
import de.ximanton.bingo.preset.PresetGUIListener;
import de.ximanton.bingo.preset.PresetManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JavaPlugin {

    private static Main INSTANCE;
    public static Main getInstance() {
        return INSTANCE;
    }
    public static BingoGame bingoGame;

    public Main() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        bingoGame = new BingoGame();
        PresetManager.getInstance().reload();

        registerCommands();
        registerListener();
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new InventoryProtector(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new BingoNotRunningListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new BingoSettingsListener(), this);
        pluginManager.registerEvents(new SettingsListener(), this);
        pluginManager.registerEvents(new PresetGUIListener(), this);
    }

    private void registerCommands() {
        getCommand("bingo").setExecutor(new BingoCommand());
        getCommand("teams").setExecutor(new TeamCommand());
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("bp").setExecutor(new BackpackCommand());
        getCommand("random").setExecutor(new RandomCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("top").setExecutor(new TopCommand());
        getCommand("teamtp").setExecutor(new TeamTpCommand());
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.BLUE + "Bingo" + ChatColor.GRAY + "] " + ChatColor.GRAY;
    }

    public static String getNiceName(Material mat) {
        String oldName = mat.toString().replace("_", " ").toLowerCase();
        StringBuilder newName = new StringBuilder();
        Scanner lineScan = new Scanner(oldName);
        while (lineScan.hasNext()) {
            String word = lineScan.next();
            newName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return newName.toString();
    }

}
