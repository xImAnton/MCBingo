package de.ximanton.bingo.commands;

import de.ximanton.bingo.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class PresetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        switch (args[0]) {
            case "reset":
                if (!sender.isOp()) {
                    return false;
                }
                Main.selectedPresets.clear();
                Main.selectedMaterials.clear();
                sender.sendMessage(Main.getPrefix() + "Die Presets wurden zurückgesetzt");
                return false;
            case "add":
                if (!sender.isOp()) {
                    return false;
                }
                if (args.length > 1) {
                    ArrayList<Material> preset = checkPreset(args[1]);
                    if (preset != null) {
                        Main.selectedPresets.add(args[1]);
                        sender.sendMessage(Main.getPrefix() + "Das Preset " + args[1] + " wird hinzugefügt");
                        for (Material material : preset) {
                            if (!Main.selectedMaterials.contains(material)) {
                                Main.selectedMaterials.add(material);
                            }
                        }
                    } else {
                        sender.sendMessage(Main.getPrefix() + "Das Preset " + args[1] + "konnte nicht gefunden werden");
                    }
                } else {
                    sender.sendMessage(Main.getPrefix() + "Bitte gib einen Preset-Namen ein" );
                    return false;
                }
                return false;
            case "set":
                if (!sender.isOp()) {
                    return false;
                }
                if (args.length > 1) {
                    Main.selectedPresets.add(args[1]);
                } else {
                    sender.sendMessage(Main.getPrefix() + "Bitte gib einen Preset-Namen ein" );
                    return false;
                }
                Main.selectedPresets.clear();
                ArrayList<Material> preset = checkPreset(args[1]);
                if (preset != null) {
                    sender.sendMessage(Main.getPrefix() + "Das Preset " + args[1] + " wird aktiviert");
                    Main.selectedMaterials = preset;
                    Main.selectedPresets.add(args[1]);
                } else {
                    sender.sendMessage(Main.getPrefix() + "Das Preset " + args[1] + "konnte nicht gefunden werden");
                }
                return false;
            case "info":
                switch (Main.selectedPresets.size()) {
                    case 0:
                        sender.sendMessage(Main.getPrefix() + "Es wurden noch keine Presets aktiviert");
                        break;
                    case 1:
                        sender.sendMessage(Main.getPrefix() + "Momentan ist das Preset " + ChatColor.GREEN + Main.selectedPresets.get(0) + ChatColor.GRAY + " aktiviert");
                        break;
                    default:
                        sender.sendMessage(Main.getPrefix() + "Momentan sind die Presets " + ChatColor.GREEN + String.join(", ", Main.selectedPresets) + ChatColor.GRAY + " aktiviert");
                        break;
                }
                return false;
        }
        return false;
    }

    public static ArrayList<Material> checkPreset(String name) {
        ArrayList<Material> items = new ArrayList<>();
        switch (name) {
            case "easy":
                items.add(Material.STONE);
                items.add(Material.GRANITE);
                items.add(Material.DIORITE);
                items.add(Material.ANDESITE);
                items.add(Material.POLISHED_ANDESITE);
                items.add(Material.POLISHED_DIORITE);
                items.add(Material.POLISHED_GRANITE);
                items.add(Material.DIRT);
                items.add(Material.COARSE_DIRT);
                items.add(Material.COBBLESTONE);
                items.add(Material.OAK_PLANKS);
                items.add(Material.SPRUCE_PLANKS);
                items.add(Material.BIRCH_PLANKS);
                items.add(Material.DARK_OAK_PLANKS);
                items.add(Material.OAK_SAPLING);
                items.add(Material.SAND);
                items.add(Material.GRAVEL);
                items.add(Material.IRON_ORE);
                items.add(Material.GOLD_ORE);
                items.add(Material.BIRCH_LOG);
                items.add(Material.OAK_WOOD);
                items.add(Material.SPRUCE_LEAVES);
                items.add(Material.GLASS);
                items.add(Material.LAPIS_BLOCK);
                items.add(Material.DISPENSER);
                items.add(Material.SANDSTONE);
                items.add(Material.NOTE_BLOCK);
                items.add(Material.PISTON);
                items.add(Material.POPPY);
                items.add(Material.DANDELION);
                items.add(Material.BOOKSHELF);
                items.add(Material.TNT);
                items.add(Material.BRICKS);
                items.add(Material.SMOOTH_STONE);
                items.add(Material.CHEST);
                items.add(Material.FURNACE);
                items.add(Material.LEVER);
                items.add(Material.LADDER);
                items.add(Material.RAIL);
                items.add(Material.REDSTONE_TORCH);
                items.add(Material.CLAY);
                items.add(Material.CARVED_PUMPKIN);
                items.add(Material.BIRCH_TRAPDOOR);
                items.add(Material.BRICK_STAIRS);
                items.add(Material.HOPPER);
                items.add(Material.LIGHT_BLUE_TERRACOTTA);
                items.add(Material.COAL_BLOCK);
                items.add(Material.SUNFLOWER);
                items.add(Material.PURPLE_GLAZED_TERRACOTTA);
                items.add(Material.LIGHT_GRAY_CONCRETE);
                items.add(Material.FLINT_AND_STEEL);
                items.add(Material.FISHING_ROD);
                items.add(Material.COOKED_PORKCHOP);
                items.add(Material.WATER_BUCKET);
                items.add(Material.EGG);
                items.add(Material.COOKED_COD);
                items.add(Material.ORANGE_BED);
                items.add(Material.ROTTEN_FLESH);
                items.add(Material.SPRUCE_BOAT);
                items.add(Material.CAMPFIRE);
                items.add(Material.FLETCHING_TABLE);
                items.add(Material.SMITHING_TABLE);
                items.add(Material.ACTIVATOR_RAIL);
                items.add(Material.DETECTOR_RAIL);
                items.add(Material.DIAMOND);
                items.add(Material.JUKEBOX);
                items.add(Material.POWERED_RAIL);
                items.add(Material.MINECART);
                items.add(Material.IRON_TRAPDOOR);
                items.add(Material.BONE_BLOCK);
                items.add(Material.FEATHER);
                items.add(Material.PAINTING);
                items.add(Material.BOOK);
                items.add(Material.CAKE);
                items.add(Material.DRIED_KELP);
                items.add(Material.TARGET);
                return items;
            case "nether":
                items.add(Material.CRYING_OBSIDIAN);
                items.add(Material.BLACKSTONE);
                items.add(Material.GOLD_NUGGET);
                items.add(Material.NETHERRACK);
                items.add(Material.RESPAWN_ANCHOR);
                items.add(Material.WARPED_STEM);
                items.add(Material.CRIMSON_STEM);
                items.add(Material.STRIPPED_WARPED_HYPHAE);
                items.add(Material.GOLD_BLOCK);
                items.add(Material.CRIMSON_DOOR);
                items.add(Material.BLAZE_ROD);
                items.add(Material.MAGMA_CREAM);
                items.add(Material.BASALT);
                items.add(Material.BONE);
                items.add(Material.QUARTZ);
                items.add(Material.QUARTZ_BLOCK);
                items.add(Material.NETHER_BRICKS);
                items.add(Material.GLOWSTONE);
                items.add(Material.GLOWSTONE_DUST);
                items.add(Material.WARPED_FENCE_GATE);
                items.add(Material.TWISTING_VINES);
                items.add(Material.WEEPING_VINES);
                items.add(Material.SHROOMLIGHT);
                items.add(Material.CRIMSON_FUNGUS);
                items.add(Material.WARPED_FUNGUS);
                items.add(Material.SOUL_SAND);
                items.add(Material.SOUL_SOIL);
                items.add(Material.GHAST_TEAR);
                items.add(Material.BONE_BLOCK);
                items.add(Material.FIRE_CHARGE);
                items.add(Material.POLISHED_BLACKSTONE_BRICKS);
                return items;
            case "all":
                items.addAll(Arrays.asList(Material.values()));
                return items;
        }
        return null;
    }
}
