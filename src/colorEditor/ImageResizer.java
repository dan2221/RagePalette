package colorEditor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageResizer {

    // Método para redimensionar uma imagem
    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        // Cria um novo BufferedImage com as novas dimensões
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        
        // Cria um Graphics2D a partir do novo BufferedImage
        Graphics2D g = resizedImage.createGraphics();
        
        // Redimensiona a imagem original para o novo BufferedImage
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        
        // Libera os recursos do Graphics2D
        g.dispose();
        
        // Retorna a imagem redimensionada
        return resizedImage;
    }
}
