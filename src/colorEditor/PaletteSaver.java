package colorEditor;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class to save palette colors back to .pal binary files.
 * Converts Color[][] into binary format that matches the original .pal file structure.
 */
public class PaletteSaver {

	/**
	 * Saves a color matrix to a .pal file in binary format.
	 * Each color is saved as 3 bytes (RGB) in the order they appear in the matrix.
	 *
	 * @param colorMatrix The 4x4 color matrix to save
	 * @param filePath    The path to the .pal file to write to
	 * @return true if the save was successful, false otherwise
	 */
	public static boolean savePalette(Color[][] colorMatrix, String filePath) {
		if (colorMatrix == null || colorMatrix.length != 4 || colorMatrix[0].length != 4) {
			System.err.println("Invalid color matrix. Must be 4x4.");
			return false;
		}

		if (filePath == null || filePath.isEmpty()) {
			System.err.println("Invalid file path.");
			return false;
		}

		try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
			// Iterate through the color matrix and write each color as 3 bytes (RGB)
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Color color = colorMatrix[i][j];
					// Write RGB bytes in order
					fos.write(color.getRed());
					fos.write(color.getGreen());
					fos.write(color.getBlue());
				}
			}

			System.out.println("Palette saved successfully to: " + filePath);
			return true;

		} catch (IOException e) {
			System.err.println("Error saving palette to file: " + filePath);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Saves the current palette with confirmation dialog.
	 * Shows a success or error message to the user.
	 *
	 * @param colorMatrix The color matrix to save
	 * @param filePath    The path to the .pal file
	 * @return true if save was successful
	 */
	public static boolean savePaletteWithConfirmation(Color[][] colorMatrix, String filePath) {
		boolean success = savePalette(colorMatrix, filePath);

		if (success) {
			javax.swing.JOptionPane.showMessageDialog(
				null,
				"Palette saved successfully!",
				"Success",
				javax.swing.JOptionPane.INFORMATION_MESSAGE
			);
		} else {
			javax.swing.JOptionPane.showMessageDialog(
				null,
				"Error saving palette. Please check the file path.",
				"Error",
				javax.swing.JOptionPane.ERROR_MESSAGE
			);
		}

		return success;
	}
}
