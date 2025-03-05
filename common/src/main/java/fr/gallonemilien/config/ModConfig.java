package fr.gallonemilien.config;

import fr.gallonemilien.items.ShoeType;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModConfig {
    private @NotNull Map<String, Double> fasterBlocks = new HashMap<>();
    private final Map<Pair<ConfigDataType, ConfigMaterialType>, Double> modifiers = new HashMap<>();


    private final Map<ShoeType, Double> shoeLoot = new HashMap<>();

    public @NotNull Map<String, Double> getFasterBlocks() {
        return fasterBlocks;
    }

    public void setModifier(Pair<ConfigDataType, ConfigMaterialType> key, double value) {
        modifiers.put(key, value);
    }

    public Double getModifier(Pair<ConfigDataType, ConfigMaterialType> key) {
        return modifiers.get(key);
    }

    public float getShoeLoot(ShoeType key) {
        return (float)shoeLoot.get(key).doubleValue();
    }

    public void setShoeLoot(ShoeType key, double value) {
        this.shoeLoot.put(key, value);
    }

    public void setFasterBlocks(@NotNull List<String> fasterBlocksList) {
        this.fasterBlocks = fasterBlocksList.stream()
            .map(entry -> entry.split("="))
            .filter(parts -> parts.length == 2)
            .collect(Collectors.toMap(
                parts -> parts[0],
                parts -> {
                    try {
                        return Double.parseDouble(parts[1]);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in entry: " + Arrays.toString(parts));
                        return 0.0;
                    }
                },
                (existing, replacement) -> existing, // Gestion des clés en double
                HashMap::new
            ));
    }
}