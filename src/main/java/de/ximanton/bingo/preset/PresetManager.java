package de.ximanton.bingo.preset;

import de.ximanton.bingo.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class PresetManager {

    private static PresetManager INSTANCE = null;

    public static PresetManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PresetManager();
        }
        return INSTANCE;
    }

    private final Set<Preset> presets = new HashSet<>();
    private final Set<Preset> activePresets = new HashSet<>();
    private final PresetGUI gui;

    public PresetManager() {
        this.gui = new PresetGUI();
    }

    public void reload() {
        presets.clear();

        ConfigurationSection presetConfig = Main.getInstance().getConfig().getConfigurationSection("presets");

        for (String name : presetConfig.getKeys(false)) {
            presets.add(Preset.fromConfig(name, presetConfig.getConfigurationSection(name)));
        }

        gui.update();
    }

    public Set<Preset> getActivePresets() {
        return activePresets;
    }

    public Set<Preset> getInactivePresets() {
        Set<Preset> diff = new HashSet<>(presets);
        diff.removeAll(activePresets);
        return diff;
    }

    public Set<Material> getUniqueItems() {
        Set<Material> combinedItems = new HashSet<>();

        for (Set<Material> presetMaterials : presets.stream().map(Preset::getItems).toList()) {
            combinedItems.addAll(presetMaterials);
        }

        return combinedItems;
    }

    public List<Material> getRandomMaterials(int count) {
        List<Material> possibleItems = getUniqueItems().stream().toList();
        List<Material> out = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Material randomItem;

            do {
                randomItem = possibleItems.get(random.nextInt(possibleItems.size()));
            } while (out.contains(randomItem));

            out.add(randomItem);
        }

        return out;
    }

    public PresetGUI getGUI() {
        return gui;
    }
}
