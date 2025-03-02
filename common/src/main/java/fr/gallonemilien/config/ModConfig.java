package fr.gallonemilien.config;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModConfig {
    private @NotNull Map<String, Double> fasterBlocks;

    public @NotNull Map<String, Double> getFasterBlocks() {
        return fasterBlocks;
    }

    public void setFasterBlocks(@NotNull List<String> fasterBlocksList) {
        System.out.println("SET FASTER BLOCKS");
        this.fasterBlocks = fasterBlocksList.stream()
            .map(entry -> entry.split(","))
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