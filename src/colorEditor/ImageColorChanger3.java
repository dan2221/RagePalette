package colorEditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageColorChanger3 {
	
	public static BufferedImage processImages() {
		try {
			// Carregar as cores originais e as novas cores
			Color[][] originalColors = TabelaColorida.originalColors;
			Color[][] alternateColors = TabelaColorida.alternateColors;

			// Caminhos da imagem de entrada e saída
			String inputPath = "C:\\Users\\danie\\Downloads\\rooframe.png";
			String outputPath = "C:\\Users\\danie\\Downloads\\nova_imagem.png";

			// Carregar a imagem original
			BufferedImage originalImage = ImageIO.read(new File(inputPath));

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

			// Salvar a nova imagem
//			ImageIO.write(novaImagem, "PNG", new File(outputPath));
//			System.out.println("Processamento concluído! A nova imagem foi salva em: " + outputPath);
			return novaImagem;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
