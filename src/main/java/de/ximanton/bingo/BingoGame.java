package de.ximanton.bingo;

import de.ximanton.bingo.gui.BingoSettings;
import de.ximanton.bingo.preset.PresetManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class BingoGame {

    public List<Material> itemsToFind;
    public ArrayList<BingoTeam> teams = new ArrayList<>();
    public boolean running = false;
    public BingoSettings settings;
    public final Timer timer;

    public BingoGame() {
        timer = new Timer(0);
        settings = new BingoSettings();
        for (int i = 0; i < 4; i++) {
            BingoTeam team = new BingoTeam(teams.size() + 1);
            teams.add(team);
        }
    }

    public void startGame() {
        this.itemsToFind = PresetManager.getInstance().getRandomMaterials(settings.getItemAmountToFind());
        this.running = true;
        timer.start();
        World world = Bukkit.getWorld("world");
        world.setTime(1000L);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, settings.isAnnounceAdvancements());
        world.setGameRule(GameRule.KEEP_INVENTORY, settings.isRespawnPlayers());
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setDifficulty(Difficulty.NORMAL);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 3.0F, 1F);
            player.setSaturation(20f);
            player.setFoodLevel(20);
        }

        if (Main.bingoGame.settings.isRandomiseTeams()) {
            randomiseTeams();
        }

        removeEmptyTeams();

        if (Main.bingoGame.settings.getTeamBackpackSize() != 0) {
            for (BingoTeam team : teams) {
                team.backpack = Bukkit.createInventory(null, Main.bingoGame.settings.getTeamBackpackSize(), ChatColor.BLUE + "Backpack von " + ChatColor.YELLOW + "Team #" + team.id);
            }
        }
        Bukkit.broadcastMessage(Main.getPrefix() + "Das Bingo-Spiel beginnt!");
    }

    public void removeTeam(int id) {
        for (BingoTeam team : teams) {
            if (team.id == id) {
                teams.remove(id - 1);
            }
        }
    }

    private void removeEmptyTeams() {
        ArrayList<BingoTeam> teamsToRemove = new ArrayList<>();

        for (BingoTeam teaml : teams) {
            if (teaml.members.isEmpty()) {
                teamsToRemove.add(teaml);
            }
        }

        teams.removeAll(teamsToRemove);
    }

    private void randomiseTeams() {
        for (BingoTeam team : teams) {
            for (Player member : team.members) {
                team.leavePlayer(member);
            }
        }
        boolean foundTeam;
        Random random = new Random();
        for (Player player : Bukkit.getOnlinePlayers()) {
            foundTeam = false;
            while (!foundTeam) {
                BingoTeam tryTeam = teams.get(random.nextInt(teams.size()));
                if (!tryTeam.isFull()) {
                    tryTeam.joinPlayer(player);
                    foundTeam = true;
                }
            }
        }
    }

    public BingoTeam getTeamById(int id) {
        for (BingoTeam teamL : teams) {
            if (teamL.id == id) {
                return teamL;
            }
        }
        return null;
    }

    public void addTeam(BingoTeam team) {
        teams.add(team);
    }

    public BingoTeam getTeam(Player player) {
        for (BingoTeam teamL : teams) {
            if (teamL.members.contains(player)) {
                return teamL;
            }
        }
        return null;
    }

    public ArrayList<BingoTeam> sortTeams() {
        ArrayList<BingoTeam> newTeams = (ArrayList<BingoTeam>) teams.clone();
        newTeams.sort(Comparator.comparingInt(o -> o.id));
        return newTeams;
    }

    public ArrayList<BingoTeam> sortTeamsByPlace() {
        ArrayList<BingoTeam> newTeams = (ArrayList<BingoTeam>) teams.clone();
        newTeams.sort(Comparator.comparingInt(o -> o.aquiredMaterials.size()));
        Collections.reverse(newTeams);
        return newTeams;
    }

    public void stop(BingoTeam winner) {
        this.running = false;
        Bukkit.broadcastMessage("");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3.0F, 1F);
            player.setGameMode(GameMode.SPECTATOR);
        }
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.YELLOW + "Team #" + winner.id + ChatColor.GREEN + " (" + winner.getMemberString() + ")" + ChatColor.GRAY + " hat alle Items gefunden und das Spiel gewonnen!");
        String stopTime = timer.stop();
        int place = 1;
        for (BingoTeam teamRow : sortTeamsByPlace()) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "Platz " + place + ": " + ChatColor.YELLOW + "Team #" + teamRow.id + ChatColor.GREEN + " (" + teamRow.getMemberString() + ") " + ChatColor.GRAY + "mit " + ChatColor.GREEN + teamRow.aquiredMaterials.size() + ChatColor.GRAY + " / " + ChatColor.RED + itemsToFind.size() + ChatColor.GRAY + " Items");
            place++;
        }
        if (settings.isShowTimer()) {
            Bukkit.broadcastMessage(Main.getPrefix() + "Das Spiel dauerte " + ChatColor.GOLD + stopTime);
        }
    }

}
