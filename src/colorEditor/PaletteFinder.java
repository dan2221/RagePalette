package colorEditor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PaletteFinder {
    public static final List<String> NAMES = Arrays.asList(
        "adam", "ash_char", "axel", "bk3_axel", "blaze", "electra_char", "max", "mry",
        "roo_char", "sor1_axel", "sor1_blaze", "sor2_blaze", "sor2_shiva",
        "sor2_skate", "sor3_shiva", "zan", "skate", "rudra_char"
    );

    /**
     * Verifies the existence of the palette directory and checks if all required 
     * palette files are present within it.
     *
     * @param sorrPath the game path.
     */

    public static void findPalettes(File sorrPath) {
        if (sorrPath == null) {
            System.out.println("No directory selected.");
            return;
        }

        File paletteDir = new File(sorrPath, "palettes/chars");
        if (!paletteDir.exists() || !paletteDir.isDirectory()) {
            System.out.println("Palette directory not found.");
            return;
        }

        for (String name : NAMES) {
            boolean baseFileExists = false;
            int count = 0;

            File[] files = paletteDir.listFiles((dir, filename) -> filename.endsWith(".pal"));
            if (files != null) {
                Pattern pattern = Pattern.compile(Pattern.quote(name) + "[1-7]\\.pal");
                for (File file : files) {
                    if (file.getName().equals(name + ".pal")) {
                        baseFileExists = true;
                    } else if (pattern.matcher(file.getName()).matches()) {
                        count++;
                    }
                }
            }

            if (baseFileExists && count == 7) {
                System.out.println("Found all required .pal files for: " + name);
            } else {
                System.out.println("Missing files for: " + name);
            }
        }
    }
}
