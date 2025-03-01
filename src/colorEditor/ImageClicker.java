package colorEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageClicker extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private final int SCALE_FACTOR = 3; // Scale factor for the image

	public ImageClicker() {
		try {
			// Load the image from the specified path
			image = ImageIO.read(new File("C:\\Users\\danie\\OneDrive\\Jogos\\SORRV5.1\\Tools\\rooframe.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Add mouse listener to handle clicks
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Get the pixel color at the clicked position
				int x = e.getX() / SCALE_FACTOR; // Adjust for scaling
				int y = e.getY() / SCALE_FACTOR; // Adjust for scaling
				if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
					int rgb = image.getRGB(x, y);
					Color color = new Color(rgb);
					System.out.println("Clicked color: " + color);
				} else {
					System.out.println("Clicked outside the image.");
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the image scaled by the scale factor
		if (image != null) {
			g.drawImage(image, 0, 0, image.getWidth() * SCALE_FACTOR, image.getHeight() * SCALE_FACTOR, this);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Image Clicker");
		ImageClicker imageClicker = new ImageClicker();
		frame.add(imageClicker);
		frame.setSize(800, 600); // Set the size of the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
