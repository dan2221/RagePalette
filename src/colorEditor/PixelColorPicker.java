package colorEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PixelColorPicker {
	private BufferedImage image1;
	private BufferedImage image2;

	public PixelColorPicker() {
		try {
			image1 = ImageIO.read(new File("C:\\Users\\danie\\OneDrive\\Jogos\\SORRV5.1\\Tools\\newroo.png")); // Load
																												// first
																												// image
			image2 = ImageIO.read(new File("C:\\Users\\danie\\OneDrive\\Jogos\\SORRV5.1\\Tools\\rooframe4x.png")); // Load
																													// second
																													// image
		} catch (IOException e) {
			System.err.println("Error loading images: " + e.getMessage());
			System.exit(1);
		}

		JFrame frame = new JFrame("Pixel Color Picker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(244, 276); // Set window size to 244x276
		frame.setLayout(new BorderLayout());

		JLabel label = new JLabel(new ImageIcon(image1)); // Display the first image
		frame.add(label, BorderLayout.CENTER);

		JLabel infoLabel = new JLabel("Click on an image to get color from the second image.", SwingConstants.CENTER);
		frame.add(infoLabel, BorderLayout.SOUTH);

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if (x < image2.getWidth() && y < image2.getHeight()) {
					Color color = new Color(image2.getRGB(x, y)); // Get color from second image
					infoLabel.setText("Clicked at: (" + x + ", " + y + ") - Color: " + color);
					System.out.println("color:" + color);
				}
			}
		});

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(PixelColorPicker::new);
	}
}
