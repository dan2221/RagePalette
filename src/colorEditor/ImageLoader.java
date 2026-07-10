package colorEditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Utility class to load character images with priority:
 * 1. First tries to find PNG/BMP files in the game's data folder (sorrPath/data/)
 * 2. Falls back to the built-in character_images folder if not found
 */
public class ImageLoader {

	/**
	 * Loads a character image with fallback priority.
	 * Tries to load from game data folder first, then from built-in resources.
	 *
	 * @param characterName The name of the character (without extension)
	 * @param sorrPath      The path to the SORR game folder
	 * @return The loaded BufferedImage, or null if not found
	 */
	public static BufferedImage loadCharacterImage(String characterName, File sorrPath) {
		if (characterName == null || characterName.isEmpty()) {
			System.err.println("Character name is empty.");
			return null;
		}

		// First, try to load from game data folder
		BufferedImage gameImage = loadFromGameDataFolder(characterName, sorrPath);
		if (gameImage != null) {
			return gameImage;
		}

		// Fallback to built-in character images
		return loadFromResourceFolder(characterName);
	}

	/**
	 * Tries to load image from the game's data folder.
	 * Looks for PNG and BMP files in sorrPath/data/ directory.
	 *
	 * @param characterName The character name without extension
	 * @param sorrPath      The game's root folder path
	 * @return The loaded image, or null if not found
	 */
	private static BufferedImage loadFromGameDataFolder(String characterName, File sorrPath) {
		if (sorrPath == null || !sorrPath.exists()) {
			return null;
		}

		File dataFolder = new File(sorrPath, "data");
		if (!dataFolder.exists() || !dataFolder.isDirectory()) {
			System.out.println("Game data folder not found: " + dataFolder.getAbsolutePath());
			return null;
		}

		// Try PNG first
		File pngFile = new File(dataFolder, characterName + ".png");
		if (pngFile.exists() && pngFile.isFile()) {
			try {
				BufferedImage image = ImageIO.read(pngFile);
				System.out.println("Loaded character image from game data (PNG): " + pngFile.getAbsolutePath());
				return image;
			} catch (IOException e) {
				System.err.println("Error loading PNG image: " + pngFile.getAbsolutePath());
				e.printStackTrace();
			}
		}

		// Try BMP as fallback
		File bmpFile = new File(dataFolder, characterName + ".bmp");
		if (bmpFile.exists() && bmpFile.isFile()) {
			try {
				BufferedImage image = ImageIO.read(bmpFile);
				System.out.println("Loaded character image from game data (BMP): " + bmpFile.getAbsolutePath());
				return image;
			} catch (IOException e) {
				System.err.println("Error loading BMP image: " + bmpFile.getAbsolutePath());
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Loads image from the built-in resource folder (character_images).
	 *
	 * @param characterName The character name without extension
	 * @return The loaded image, or null if not found
	 */
	private static BufferedImage loadFromResourceFolder(String characterName) {
		String resourcePath = "character_images/" + characterName + ".png";
		BufferedImage image = null;

		try (InputStream is = ImageLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
			if (is != null) {
				image = ImageIO.read(is);
				System.out.println("Loaded character image from built-in resources: " + resourcePath);
			} else {
				System.err.println("Character image not found in built-in resources: " + resourcePath);
			}
		} catch (IOException e) {
			System.err.println("Error loading resource image: " + resourcePath);
			e.printStackTrace();
		}

		return image;
	}
}
