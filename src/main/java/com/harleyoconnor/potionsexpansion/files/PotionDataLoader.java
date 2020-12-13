package com.harleyoconnor.potionsexpansion.files;

import com.harleyoconnor.potionsexpansion.ModConstants;
import com.harleyoconnor.potionsexpansion.PotionsExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.util.*;

/**
 * @author Harley O'Connor
 */
public final class PotionDataLoader {

    private static File potionFolder;
    private static final Map<String, List<File>> potionFiles = new HashMap<>();

    public static void loadFiles () {
        potionFolder = new File("config/PotionsExpansion/potions");

        // Create folder if it doesn't exist, or if it isn't directly then crash the game.
        if (!potionFolder.exists()) {
            potionFolder.mkdir();
        } else if (!potionFolder.isDirectory()) {
            throw new RuntimeException(potionFolder.getAbsolutePath() + " is a file - it should be a folder. Please delete or move this file.");
        }

        loadPotionFiles();

        // TODO: Process potion data.
    }

    /**
     * Loads potion files from potions folder.
     */
    private static void loadPotionFiles() {
        // Loop through child files of potions folder.
        for (final File modIdFolder : Objects.requireNonNull(potionFolder.listFiles())) {
            final String modId = modIdFolder.getName(); // Get name of folder.

            // Check if mod with folder name is present.
            if (ModList.get().isLoaded(modId)) {
                // Get the list of files inside the mod folder.
                final List<File> jsonPotionFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(modIdFolder.listFiles())));

                // Remove the file if it is not a json or is not a registered potion.
                jsonPotionFiles.removeIf(potionFile -> {
                    final String potionFileName = potionFile.getName();
                    if (!potionFileName.toLowerCase().endsWith(ModConstants.JSON_EXTENSION)) return true;
                    return !ForgeRegistries.POTIONS.containsKey(new ResourceLocation(modId, potionFileName.replace(ModConstants.JSON_EXTENSION, "")));
                });

                // Add to potion file list if the amount of files was one or more.
                if (jsonPotionFiles.size() > 0) potionFiles.put(modId, jsonPotionFiles);
            }
        }
    }

}
