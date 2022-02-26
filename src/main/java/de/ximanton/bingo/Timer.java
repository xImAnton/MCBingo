package de.ximanton.bingo;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Timer {

    private int time;
    private int runId;

    private final Runnable runnable = new BukkitRunnable() {
        @Override
        public void run() {
            if (Main.bingoGame.settings.isShowTimer()) {
                time++;
            }
            update();
            updateScoreboard();
        }
    };

    public Timer(int startTime) {
        this.time = startTime;
    }

    public void start() {
        BukkitTask schedule = Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), runnable, 20, 20);
        runId = schedule.getTaskId();
    }

    public String stop() {
        Bukkit.getScheduler().cancelTask(runId);
        return splitToComponentTimes();
    }

    public String splitToComponentTimes() {
        long longVal = time;
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        String string = "";
        if (hours != 0) {
            if (Integer.toString(hours).length() == 1) {
                string += "0" + hours;
            } else {
                string += hours;
            }
            string += ":";
        }
        if (Integer.toString(mins).length() == 1) {
            string += "0" + mins;
        } else {
            string += mins;
        }
        string += ":";
        if (Integer.toString(secs).length() == 1) {
            string += "0" + secs;
        } else {
            string += secs;
        }
        return string;
    }

    public void update() {
        for (BingoTeam team : Main.bingoGame.teams) {
            for (Player player : team.members) {
                String s = ChatColor.GRAY + "Team " + ChatColor.YELLOW + "#" + team.id + "  " + ChatColor.RESET + ChatColor.GRAY + "Items: " + ChatColor.GREEN + team.aquiredMaterials.size() + ChatColor.GRAY + " / " + ChatColor.RED + Main.bingoGame.itemsToFind.size();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(s));
            }
        }
    }

    public void updateScoreboard() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        String title = ChatColor.BLUE + ChatColor.BOLD.toString() + "BINGO ";
        if (Main.bingoGame.settings.isShowTimer()) {
            title += ChatColor.GOLD + splitToComponentTimes();
        }
        Objective obj = board.registerNewObjective("bingo", "dummy", title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        int index = Main.bingoGame.teams.size();
        for (BingoTeam team : Main.bingoGame.teams) {
            Score teamScore = obj.getScore(ChatColor.GRAY + "Team " + ChatColor.YELLOW + "#" + team.id + ChatColor.DARK_GRAY + " | " + ChatColor.GREEN + team.aquiredMaterials.size() + ChatColor.GRAY + " / " + ChatColor.RED + Main.bingoGame.itemsToFind.size());
            teamScore.setScore(index);
            index--;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board);
        }
    }
}
