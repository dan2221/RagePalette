package colorEditor;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SpriteRemaper {

	// Método público e estático para ser acessado de qualquer classe
	public static BufferedImage processImage(BufferedImage originalImage) {
		///////////////////////////////////////
		// Carregar as cores originais e as novas cores
		//String[][] originalColors = HexOperations.getHexArray(palettePath + "\\chars\\roo_char.pal");
		//String[][] fileHexColors = HexOperations.getHexArray(palettePath + "\\chars\\roo_char6.pal");

		////////////////////////////////////////
		if (originalImage == null) {
			throw new IllegalArgumentException("A imagem de entrada não pode ser nula.");
		}

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// Criar uma nova imagem para armazenar as alterações
		BufferedImage processedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Processar cada pixel da imagem
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = new Color(originalImage.getRGB(x, y), true); // Preserva a transparência

				// Converter para escala de cinza
				int gray = (int) (color.getRed() * 0.3 + color.getGreen() * 0.59 + color.getBlue() * 0.11);

				// Aumentar o contraste (ajuste simples)
				gray = (gray > 128) ? Math.min(255, gray + 30) : Math.max(0, gray - 30);

				Color newColor = new Color(gray, gray, gray, color.getAlpha()); // Mantém a transparência
				processedImage.setRGB(x, y, newColor.getRGB());
			}
		}

		return processedImage;
	}
}