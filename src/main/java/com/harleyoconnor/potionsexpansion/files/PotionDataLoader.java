package com.harleyoconnor.potionsexpansion.files;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harleyoconnor.potionsexpansion.ModConstants;
import com.harleyoconnor.potionsexpansion.PotionsExpansion;
import com.harleyoconnor.potionsexpansion.potions.data.PotionData;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @author Harley O'Connor
 */
public final class PotionDataLoader {

    private static final JsonParser jsonParser = new JsonParser();

    private static File potionFolder;
    private static final Map<String, List<File>> potionFiles = new HashMap<>();

    private static final Map<Potion, PotionData> potionDataMap = new HashMap<>();

    public static Map<Potion, PotionData> loadData () {
        getPotionFolder(); // Gets the potion folder from the config.
        loadPotionFiles(); // Loads the potion files onto a list.
        processPotionFiles(); // Processes the potion list into a map of potions and potion data.

        return potionDataMap;
    }

    /**
     * Gets the potion folder from the config.
     */
    public static void getPotionFolder() {
        potionFolder = new File("config/PotionsExpansion/potions");

        // Create folder if it doesn't exist, or if it isn't directly then crash the game.
        if (!potionFolder.exists()) {
            potionFolder.mkdir();
        } else if (!potionFolder.isDirectory()) {
            throw new RuntimeException(potionFolder.getAbsolutePath() + " is a file - it should be a folder. Please delete or move this file.");
        }
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

                    final String potionName = potionFileName.replace(ModConstants.JSON_EXTENSION, "");
                    final ResourceLocation resourceLocation = new ResourceLocation(modId, potionName);

                    final boolean potionExists = ForgeRegistries.POTION_TYPES.getKeys().stream().anyMatch(potionResourceLocation -> potionResourceLocation.equals(resourceLocation));

                    if (!potionExists)
                        PotionsExpansion.getLogger().warn("Error processing file " + potionFileName + ", potion resource location '" + modId + ":" + potionName + "' does not exist in the potion registry.");

                    return !potionExists;
                });

                // Add to potion file list if the amount of files was one or more.
                if (jsonPotionFiles.size() > 0) potionFiles.put(modId, jsonPotionFiles);
            }
        }
    }

    /**
     * Processes the potion files into potionDataMap.
     */
    private static void processPotionFiles () {
        for (final String modId : potionFiles.keySet()) {
            for (final File potionFile : potionFiles.get(modId)) {
                processPotionFile(modId, potionFile);
            }
        }
    }

    /**
     * Processes a potion file into a potion data entry.
     *
     * @param modId The mod ID of the current potion.
     * @param potionFile The file for the current potion.
     */
    private static void processPotionFile(String modId, File potionFile) {
        try {
            final JsonObject jsonFile = (JsonObject) jsonParser.parse(new FileReader(potionFile));

            final byte difficulty = jsonFile.get("difficulty").getAsByte();
            final String opposite = jsonFile.get("opposite").getAsString();

            final ResourceLocation oppositeEffect = new ResourceLocation(
                    opposite.substring(0, opposite.indexOf(':')),
                    opposite.substring(opposite.indexOf(':') + 1)
            );

            if (ForgeRegistries.POTIONS.getKeys().stream().noneMatch(effectResourceLocation -> effectResourceLocation.equals(oppositeEffect))) {
                PotionsExpansion.getLogger().warn("Error processing file " + potionFile.getName() + ", opposite effect resource location '" + oppositeEffect + "' does not exist in the effect registry.");
                return;
            }

            final Potion potion = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(modId, potionFile.getName().replace(ModConstants.JSON_EXTENSION, "")));
            final PotionData potionData = new PotionData(difficulty, ForgeRegistries.POTIONS.getValue(oppositeEffect));

            potionDataMap.put(potion, potionData);

            PotionsExpansion.getLogger().info("Added " + potion.getRegistryName() + " to potion data with difficulty " + difficulty + " and opposite effect " + potionData.getOppositeEffect().getRegistryName() + ".");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading json file " + potionFile.getName() + ". Please report this with the above stacktrace to the Potions Expansion issue tracker.");
        }
    }

    public static Map<Potion, PotionData> getPotionDataMap() {
        return potionDataMap;
    }

}
