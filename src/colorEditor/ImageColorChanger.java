package colorEditor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageColorChanger {
	public static void main(String[] args) {
		try {
			// Carregar a imagem original
			BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\danie\\Downloads\\teste.png"));
			int scaleFactor = 2;

			// Ampliar a imagem
			BufferedImage scaledImage = new BufferedImage(originalImage.getWidth() * scaleFactor,
					originalImage.getHeight() * scaleFactor, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = scaledImage.createGraphics();
			g.drawImage(originalImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
			g.dispose();

			// Definir as cores a serem trocadas
			String[] coresOriginaisHex = { "#FF0000", "#00FF00", "#0000FF" }; // Exemplo de cores originais
			String[] novasCoresHex = { "#FFFF00", "#FF00FF", "#00FFFF" }; // Exemplo de novas cores

			// Criar uma nova imagem para armazenar as partes recortadas
			BufferedImage novaImagem = new BufferedImage(scaledImage.getWidth(), scaledImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D gNovaImagem = novaImagem.createGraphics();
			gNovaImagem.setComposite(AlphaComposite.Clear);
			gNovaImagem.fillRect(0, 0, novaImagem.getWidth(), novaImagem.getHeight());
			gNovaImagem.setComposite(AlphaComposite.SrcOver);

			// Processar a imagem
			for (int i = 0; i < coresOriginaisHex.length; i++) {
				Color corOriginal = Color.decode(coresOriginaisHex[i]);
				Color novaCor = Color.decode(novasCoresHex[i]);

				for (int y = 0; y < scaledImage.getHeight(); y++) {
					for (int x = 0; x < scaledImage.getWidth(); x++) {
						int pixelColor = scaledImage.getRGB(x, y);
						Color color = new Color(pixelColor, true);

						if (color.equals(corOriginal)) {
							// Trocar a cor
							novaImagem.setRGB(x, y, novaCor.getRGB());
						} else {
							// Tornar a cor transparente
							novaImagem.setRGB(x, y, 0x00000000);
						}
					}
				}
			}

			// Salvar a nova imagem
			ImageIO.write(novaImagem, "PNG", new File("C:\\Users\\danie\\Downloads\\nova_imagem.png"));

			System.out.println("Processamento concluído!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
