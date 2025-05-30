package colorEditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageColorChanger3 {

	public static BufferedImage loadImage(String imageName) {
		// Obtém o ClassLoader da classe atual
		ClassLoader classLoader = ImageColorChanger3.class.getClassLoader();

		// Carrega a imagem como um InputStream
		try (InputStream inputStream = classLoader.getResourceAsStream(imageName)) {
			if (inputStream == null) {
				System.out.println("Imagem não encontrada: " + imageName);
				return null;
			}
			// Lê a imagem a partir do InputStream
			return ImageIO.read(inputStream);
		} catch (Exception e) {
			System.out.println("Erro ao carregar a imagem: " + e.getMessage());
			return null;
		}
	}

	public static BufferedImage processImages() {
		// Carregar as cores originais e as novas cores
		Color[][] originalColors = TabelaColorida.originalColors;
		Color[][] alternateColors = TabelaColorida.alternateColors;

		// Caminho da imagem de entrada
		System.out.println("Getting loaded character from configuration: " + ConfigManager.selectedPalette);
		BufferedImage originalImage = loadImage("character_images/" + ConfigManager.selectedPalette + ".png");

		// Verifica se a imagem original foi carregada corretamente
		if (originalImage == null) {
			System.err.println("Failed to load the original image.");
			return null; // Retorna null ou lança uma exceção conforme necessário
		}

		// Criar uma nova imagem para armazenar as partes recortadas
		BufferedImage novaImagem = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gNovaImagem = novaImagem.createGraphics();
		gNovaImagem.setComposite(AlphaComposite.Clear);
		gNovaImagem.fillRect(0, 0, novaImagem.getWidth(), novaImagem.getHeight());
		gNovaImagem.setComposite(AlphaComposite.SrcOver);

		// Processar a imagem
		for (int i = 0; i < originalColors.length; i++) {
			for (int j = 0; j < originalColors[i].length; j++) {
				Color corOriginal = originalColors[i][j];
				Color novaCor = alternateColors[i][j];

				BufferedImage tempImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D gTemp = tempImage.createGraphics();
				gTemp.setComposite(AlphaComposite.Clear);
				gTemp.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
				gTemp.setComposite(AlphaComposite.SrcOver);

				for (int y = 0; y < originalImage.getHeight(); y++) {
					for (int x = 0; x < originalImage.getWidth(); x++) {
						int pixelColor = originalImage.getRGB(x, y);
						Color color = new Color(pixelColor, true);

						if (color.equals(corOriginal)) {
							tempImage.setRGB(x, y, novaCor.getRGB());
						} else {
							tempImage.setRGB(x, y, 0x00000000);
						}
					}
				}

				gNovaImagem.drawImage(tempImage, 0, 0, null);
				gTemp.dispose();
			}
		}

		gNovaImagem.dispose(); // Libera os recursos gráficos
		return novaImagem; // Retorna a nova imagem processada
	}
}
