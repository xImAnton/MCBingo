package de.ximanton.bingo;

import de.ximanton.bingo.commands.*;
import de.ximanton.bingo.interfaces.BingoSettingsListener;
import de.ximanton.bingo.interfaces.PresetGUI;
import de.ximanton.bingo.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public final class Main extends JavaPlugin {
    public static BingoGame bingoGame;
    public static Timer timer;
    public static ArrayList<Material> selectedMaterials = new ArrayList<>();
    public static ArrayList<String> selectedPresets = new ArrayList<>();

    @Override
    public void onEnable() {
        if (!new File("./Bingo/config.yml").isFile()) { saveDefaultConfig(); }

        bingoGame = new BingoGame(this);
        timer = new Timer(0, this);

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
        pluginManager.registerEvents(new PresetGUI(this, ""), this);
    }

    private void registerCommands() {
        getCommand("bingo").setExecutor(new BingoCommand());
        getCommand("teams").setExecutor(new TeamCommand());
        getCommand("preset").setExecutor(new PresetCommand());
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("bp").setExecutor(new BackpackCommand());
        getCommand("random").setExecutor(new RandomCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("top").setExecutor(new TopCommand());
        getCommand("teamtp").setExecutor(new TeamTpCommand());
        getCommand("tmp").setExecutor(new TestCommand(this));
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
