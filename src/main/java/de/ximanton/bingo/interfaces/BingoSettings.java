package de.ximanton.bingo.interfaces;

import de.ximanton.bingo.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BingoSettings {
    Main plugin;
    Inventory inv;
    String title;
    int teamBackpackSize = 9;
    int teamSize = 1;
    int serverSlots = 20;
    int itemAmountToFind = 9;
    boolean respawnPlayers = true;
    boolean showTimer = true;
    boolean hunger = false;
    boolean enablePvP = true;
    boolean announceAdvancements = true;
    boolean randomiseTeams = true;
    boolean spreadTeams = true;
    boolean teamTeleport = true;
    boolean grantDamage = true;
    boolean enableTopCommand = true;

    public BingoSettings(Main plugin) {
        this.plugin = plugin;
        title = ChatColor.BLUE + "Bingo-Settings" + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Seite " + ChatColor.GOLD + "1";
        inv = Bukkit.createInventory(null, 54, title);
        reloadConfig();
        setupGui();
    }

    private void setupGui() {
        for (int i = 0; i < 10; i++) { inv.setItem(i, emptyField()); }
        inv.addItem(descriptionField("Team-Backpack Größe", new String[]{"Die Slot-Anzahl eines Team-Backpacks."}, null, Material.ENDER_CHEST));
        inv.addItem(descriptionField("Team Größe", new String[]{"Gibt an, wie viele Spieler in", "einem Team sein können."}, null, Material.PISTON));
        inv.addItem(descriptionField("Keep Inventory", new String[]{"Spieler behalten ihre Items,", "wenn sie sterben."}, "Spieler verlieren ihre XP.", Material.CHEST));
        inv.addItem(descriptionField("Timer", new String[]{"Ein Timer wird für dieses Spiel angezeigt."}, null, Material.CLOCK));
        inv.addItem(descriptionField("Hunger", new String[]{"Spieler können Hunger bekommen."}, null, Material.COOKED_PORKCHOP));
        inv.addItem(descriptionField("PvP", new String[]{"Spieler können sich angreifen."}, null, Material.IRON_SWORD));
        inv.addItem(descriptionField("Announce Advancements", new String[]{"Advancements, die freigeschaltet werden,", "werden allen Spielern angezeigt."}, null, Material.GRASS_BLOCK));
        for (int i = 17; i < 19; i++) { inv.setItem(i, emptyField()); }
        inv.addItem(counterField("Team-Backpack Größe", teamBackpackSize));
        inv.addItem(counterField("Team Größe", teamSize));
        inv.addItem(toggleField("Keep Inventory", respawnPlayers));
        inv.addItem(toggleField("Timer", showTimer));
        inv.addItem(toggleField("Hunger", hunger));
        inv.addItem(toggleField("PvP", enablePvP));
        inv.addItem(toggleField("Announce Advancements", announceAdvancements));
        for (int i = 26; i < 28; i++) { inv.setItem(i, emptyField()); }
        inv.addItem(counterField("Server Slots", serverSlots));
        inv.addItem(counterField("Bingo-Items", itemAmountToFind));
        inv.addItem(toggleField("Zufällige Teams", randomiseTeams));
        inv.addItem(toggleField("Teams verteilen", spreadTeams));
        inv.addItem(toggleField("Team-Teleport", teamTeleport));
        inv.addItem(toggleField("Schaden", grantDamage));
        inv.addItem(toggleField("/top Command", enableTopCommand));
        for (int i = 35; i < 37; i++) { inv.setItem(i, emptyField()); }
        inv.addItem(descriptionField("Server Slots", new String[]{"Gibt an, wie viele Spieler den Server", "gleichzeitig betreten können."}, null, Material.COMMAND_BLOCK));
        inv.addItem(descriptionField("Bingo-Items", new String[]{"Gibt an, wie viele Items gefunden", "werden müssen."}, null, Material.HOPPER));
        inv.addItem(descriptionField("Zufällige Teams", new String[]{"Die Teams werden beim Spielstart", "zufallig verteilt."}, "Spieler können Teams nicht manuell beitreten.", Material.ENDER_EYE));
        inv.addItem(descriptionField("Teams verteilen", new String[]{"Die Teams werden am Anfang des", "Spiels auseinander teleportiert."}, null, Material.ELYTRA));
        inv.addItem(descriptionField("Team-Teleport", new String[]{"Spieler können sich mit /teamtp zu", "einem Teammate teleportieren."}, "Cooldown: 5 Minuten pro Team", Material.ENDER_PEARL));
        inv.addItem(descriptionField("Schaden", new String[]{"Spieler können Schaden bekommen."}, null, Material.GOLDEN_APPLE));
        inv.addItem(descriptionField("/top Command", new String[]{"Spieler können sich mit /top", "an die Oberfläche teleportieren."}, null, Material.RAIL));
        inv.setItem(44, emptyField());
        for (int i = 45; i < 48; i++) { inv.setItem(i, buttonField("Presets", new String[]{"Die Presets für dieses Bingo-Spiel"}, "Klicken zum öffnen.", Material.PINK_STAINED_GLASS_PANE)); }
        inv.setItem(48, emptyField());
        inv.addItem(buttonField(ChatColor.RED + "Close", new String[]{}, "Klicken zum schließen.", Material.BARRIER));
        inv.setItem(50, emptyField());
        for (int i = 51; i < 54; i++) { inv.setItem(i, buttonField("Spiel starten", new String[]{"Das Bingo-Spiel mit den aktuellen", "Einstellungen starten."}, "Klicken zum starten.", Material.YELLOW_STAINED_GLASS_PANE)); }
    }

    public ItemStack emptyField() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack descriptionField(String title, String[] description, String action, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + title);
        ArrayList<String> descWithColor = new ArrayList<>();
        for (String line : description) { descWithColor.add(ChatColor.GRAY + line); }
        if (action != null) {
            descWithColor.add(" ");
            descWithColor.add(ChatColor.YELLOW + action);
        }
        meta.setLore(descWithColor);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack toggleField(String title, boolean ticked) {
        Material mat;
        if (ticked) {
            mat = Material.LIME_DYE;
        } else {
            mat = Material.GRAY_DYE;
        }
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + title);
        ArrayList<String> descWithColor = new ArrayList<>();
        if (ticked) {
            descWithColor.add(ChatColor.GRAY + "Aktiviert");
        } else {
            descWithColor.add(ChatColor.GRAY + "Deaktiviert");
        }
        descWithColor.add(" ");
        descWithColor.add(ChatColor.YELLOW + "Klicken zum umstellen.");
        meta.setLore(descWithColor);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack counterField(String title, int index) {
        ItemStack item = new ItemStack(Material.PAPER);
        int count = index;
        if (index != 0) {
            item.setAmount(count);
        } else {
            item.setAmount(1);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + title);
        ArrayList<String> desc = new ArrayList<>();
        desc.add(ChatColor.GRAY + "Wert: " + count);
        desc.add(" ");
        desc.add(ChatColor.YELLOW + "Linksklicken zum vergrößern.");
        desc.add(ChatColor.YELLOW + "Shift-Linksklicken zum verkleinern.");
        meta.setLore(desc);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack buttonField(String title, String[] desc, String action, Material color) {
        ItemStack item = new ItemStack(color);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + title);
        ArrayList<String> descWithColor = new ArrayList<>();
        for (String line : desc) { descWithColor.add(ChatColor.GRAY + line); }
        descWithColor.add(" ");
        descWithColor.add(ChatColor.YELLOW + action);
        meta.setLore(descWithColor);
        item.setItemMeta(meta);
        return item;
    }

    public Inventory getPage(int page) {
        return inv;
    }

    public String getTitle() {
        return title;
    }

    public void setShowTimer(boolean showTimer) {
        this.showTimer = showTimer;
    }

    public boolean isShowTimer() {
        return showTimer;
    }

    public void setRespawnPlayers(boolean respawnPlayers) {
        this.respawnPlayers = respawnPlayers;
    }

    public boolean isRespawnPlayers() {
        return respawnPlayers;
    }

    public void setHunger(boolean hunger) {
        this.hunger = hunger;
    }

    public boolean isHunger() {
        return hunger;
    }

    public void setPvP(boolean announceItems) {
        this.enablePvP = announceItems;
    }

    public boolean isPvP() {
        return enablePvP;
    }

    public void setAnnounceAdvancements(boolean announceAdvancements) {
        this.announceAdvancements = announceAdvancements;
    }

    public boolean isAnnounceAdvancements() {
        return announceAdvancements;
    }

    public void setRandomiseTeams(boolean randomiseTeams) {
        this.randomiseTeams = randomiseTeams;
    }

    public boolean isRandomiseTeams() {
        return randomiseTeams;
    }

    public void setEnableTopCommand(boolean enableTopCommand) {
        this.enableTopCommand = enableTopCommand;
    }

    public boolean isEnableTopCommand() {
        return enableTopCommand;
    }

    public void setGrantDamage(boolean grantDamage) {
        this.grantDamage = grantDamage;
    }

    public boolean isGrantDamage() {
        return grantDamage;
    }

    public void setTeamTeleport(boolean teamTeleport) {
        this.teamTeleport = teamTeleport;
    }

    public boolean isTeamTeleport() {
        return teamTeleport;
    }

    public void setSpreadTeams(boolean spreadTeams) {
        this.spreadTeams = spreadTeams;
    }

    public boolean isSpreadTeams() {
        return spreadTeams;
    }

    public void setTeamBackpackSize(int teamBackpackSize) {
        this.teamBackpackSize = teamBackpackSize;
    }

    public int getTeamBackpackSize() {
        return teamBackpackSize;
    }

    public static ArrayList<Integer> getTeamBackpackSizeValues() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(9);
        list.add(18);
        list.add(27);
        return list;
    }

    public static ArrayList<Integer> getTeamSizeValues() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        return list;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int size) {
        this.teamSize = size;
    }

    public static ArrayList<Integer> getServerSlotValues() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            list.add(i);
        }
        return list;
    }

    public int getServerSlots() {
        return serverSlots;
    }

    public void setServerSlots(int slots) {
        this.serverSlots = slots;
    }

    public static ArrayList<Integer> getBingoItemsValues() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(18);
        return list;
    }

    public int getItemAmountToFind() {
        return itemAmountToFind;
    }

    public void setItemAmountToFind(int amount) {
        this.itemAmountToFind = amount;
    }

    public void reloadConfig() {
        FileConfiguration config = plugin.getConfig();
        teamBackpackSize = config.getInt("settings.teambpsize");
        teamSize = config.getInt("settings.teamsize");
        serverSlots = config.getInt("settings.slots");
        itemAmountToFind = config.getInt("settings.items");
        respawnPlayers = config.getBoolean("settings.keepinv");
        showTimer = config.getBoolean("settings.timer");
        hunger = config.getBoolean("settings.hunger");;
        enablePvP = config.getBoolean("settings.pvp");
        announceAdvancements = config.getBoolean("settings.advancements");
        randomiseTeams = config.getBoolean("settings.randomteams");
        spreadTeams = config.getBoolean("settings.spreadteams");
        teamTeleport = config.getBoolean("settings.teamtp");
        grantDamage = config.getBoolean("settings.damage");
        enableTopCommand = config.getBoolean("settings.top");
    }
}
