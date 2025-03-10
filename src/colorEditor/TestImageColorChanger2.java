package colorEditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestImageColorChanger2 {
	public static void main(String[] args) {
		try {
			String palettePath = "C:\\Users\\danie\\OneDrive\\Jogos\\SORRV5.1\\palettes";

			// Carregar as cores originais e as novas cores
			String[][] originalColors = HexOperations.getHexArray(palettePath + "\\chars\\roo_char.pal");
			String[][] fileHexColors = HexOperations.getHexArray(palettePath + "\\chars\\roo_char6.pal");

			// Caminhos da imagem de entrada e saída
			String inputPath = "C:\\Users\\danie\\Downloads\\rooframe.png";
			String outputPath = "C:\\Users\\danie\\Downloads\\nova_imagem.png";

			// Carregar a imagem original
			BufferedImage originalImage = ImageIO.read(new File(inputPath));
			int scaleFactor = 2;

			// Ampliar a imagem
			BufferedImage scaledImage = new BufferedImage(originalImage.getWidth() * scaleFactor,
					originalImage.getHeight() * scaleFactor, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = scaledImage.createGraphics();
			g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
			g.dispose();

			// Criar uma nova imagem para armazenar as partes recortadas
			BufferedImage novaImagem = new BufferedImage(scaledImage.getWidth(), scaledImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D gNovaImagem = novaImagem.createGraphics();
			gNovaImagem.setComposite(AlphaComposite.Clear);
			gNovaImagem.fillRect(0, 0, novaImagem.getWidth(), novaImagem.getHeight());
			gNovaImagem.setComposite(AlphaComposite.SrcOver);

			// Processar a imagem
			for (int i = 0; i < originalColors.length; i++) {
				// Assumindo que cada linha de originalColors e fileHexColors contém pares de
				// cores
				for (int j = 0; j < originalColors[i].length; j++) {
					Color corOriginal = Color.decode(originalColors[i][j]);
					Color novaCor = Color.decode(fileHexColors[i][j]);

					// Criar uma imagem temporária para recortar
					BufferedImage tempImage = new BufferedImage(scaledImage.getWidth(), scaledImage.getHeight(),
							BufferedImage.TYPE_INT_ARGB);
					Graphics2D gTemp = tempImage.createGraphics();
					gTemp.setComposite(AlphaComposite.Clear);
					gTemp.fillRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
					gTemp.setComposite(AlphaComposite.SrcOver);

					for (int y = 0; y < scaledImage.getHeight(); y++) {
						for (int x = 0; x < scaledImage.getWidth(); x++) {
							int pixelColor = scaledImage.getRGB(x, y);
							Color color = new Color(pixelColor, true);

							if (color.equals(corOriginal)) {
								// Trocar a cor
								tempImage.setRGB(x, y, novaCor.getRGB());
							} else {
								// Tornar a cor transparente
								tempImage.setRGB(x, y, 0x00000000);
							}
						}
					}

					// Desenhar a imagem temporária na nova imagem
					gNovaImagem.drawImage(tempImage, 0, 0, null);
					gTemp.dispose();
				}
			}

			// Salvar a nova imagem
			ImageIO.write(novaImagem, "PNG", new File(outputPath));

			System.out.println("Processamento concluído! A nova imagem foi salva em: " + outputPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
