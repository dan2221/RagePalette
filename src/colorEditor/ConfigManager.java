package colorEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
    
    /**
     * Altera a segunda linha do arquivo de configuração.
     * @param newPalette O novo valor da paleta.
     */
    public static void updatePalette(String newPalette) {
        File configFile = new File(CONFIG_FILE);

        if (configFile.exists()) {
            try {
                // Lê todas as linhas do arquivo
                List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE));

                // Verifica se o arquivo tem pelo menos duas linhas
                if (lines.size() >= 2) {
                    // Altera a segunda linha
                    lines.set(1, newPalette); // A segunda linha tem índice 1

                    // Escreve as linhas de volta no arquivo
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
                        for (String line : lines) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                } else {
                    System.out.println("O arquivo não contém linhas suficientes para atualizar a paleta.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O arquivo de configuração não existe.");
        }
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
