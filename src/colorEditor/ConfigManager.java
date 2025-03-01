package colorEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    public static File sorrPath;
    public static String selectedPalette;
    private static final String CONFIG_FILE = "config.cfg";

    /**
     * Get data from configuration file.
     * @return
     */
    public static File getData() {
        File configFile = new File(CONFIG_FILE);

        if (configFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
                String path = br.readLine();
                selectedPalette = br.readLine();

                // If no path is saved, a window will appear to select a directory.
                if (path != null && !path.isEmpty()) {
                    sorrPath = new File(path);
                    if (!sorrPath.exists() || !sorrPath.isDirectory()) {
                        selectDirectory();
                    }
                } else {
                    selectDirectory();
                }

                if (selectedPalette == null || selectedPalette.isEmpty() || !PaletteFinder.NAMES.contains(selectedPalette)) {
                    selectedPalette = PaletteSelector.showPaletteSelection();
                    saveConfig();
                }
            } catch (IOException e) {
                e.printStackTrace();
                selectDirectory();
            }
        } else {
            selectDirectory();
            selectedPalette = PaletteSelector.showPaletteSelection();
            saveConfig();
        }
        return sorrPath;
    }

    private static void selectDirectory() {
        sorrPath = DirectoryChooser.chooseDirectory();
        if (sorrPath != null) {
            saveConfig();
        }
    }

    private static void saveConfig() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            bw.write(sorrPath.getAbsolutePath() + "\n");
            bw.write(selectedPalette + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
