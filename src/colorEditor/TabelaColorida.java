package colorEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabelaColorida {

	private static final int SCALE_FACTOR = 4;
	private static String PALETTE_PATH;
	public static Color[][] alternateColors = new Color[4][4];
	public static File sorrPath;
	public static Color[][] originalColors = new Color[4][4];
	private static JTable tabela;
	private static BufferedImage originalImage;
	private static JPanel imagePanel;
	private static JLabel imageLabel = new JLabel();

	public static void main(String[] args) {
		sorrPath = ConfigManager.getData();
		// If no palette is selected, a window will appear to choose a character.
		if (ConfigManager.selectedPalette == null) {
			ConfigManager.selectedPalette = PaletteSelector.showPaletteSelection();
		}
		PALETTE_PATH = sorrPath + File.separator + "palettes" + File.separator + "chars" + File.separator
				+ ConfigManager.selectedPalette + ".pal";
		initializeColors();
		// Check palette files
		PaletteFinder.findPalettes(sorrPath);
		// Create and show GUI
		SwingUtilities.invokeLater(TabelaColorida::createAndShowGUI);
	}

	private static void initializeColors() {
		if (PALETTE_PATH != null) {
			String[][] originalHexColors = HexOperations.getHexArray(sorrPath + File.separator + "palettes"
					+ File.separator + "chars" + File.separator + ConfigManager.selectedPalette + ".pal");
			String[][] alternateHexColors = HexOperations.getHexArray(PALETTE_PATH);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					originalColors[i][j] = Color.decode(originalHexColors[i][j]);
					alternateColors[i][j] = Color.decode(alternateHexColors[i][j]);
				}
			}
			ImageColorChanger3.processImages();
		} else {
			System.out.println("Palette path is not set!");
			return;
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Palette Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(760, 410);
		frame.setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createButtonPanel(), BorderLayout.WEST);
		mainPanel.add(createTablePanel(), BorderLayout.CENTER);
		mainPanel.add(createButtonPanel2(), BorderLayout.EAST);
		mainPanel.add(createColorChooserPanel(), BorderLayout.SOUTH);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(createImagePanel(), BorderLayout.EAST);
		frame.setVisible(true);
	}

	private static JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding

		JButton selectCharacterButton = new JButton("👤 Load Char");
		JButton importButton = new JButton("📥 Import");
		JButton saveButton = new JButton("💾 Save");
		JButton exportButton = new JButton("📤 Export");

		Dimension buttonSize = new Dimension(150, 25); // Reduce button height
		selectCharacterButton.setMaximumSize(buttonSize);
		importButton.setMaximumSize(buttonSize);
		saveButton.setMaximumSize(buttonSize);
		exportButton.setMaximumSize(buttonSize);

		// Placeholder for future action listeners
		selectCharacterButton.addActionListener(e -> {
			
			/* TODO: Add action */});
		importButton.addActionListener(e -> {
			/* TODO: Add action */});
		saveButton.addActionListener(e -> {
			/* TODO: Add action */});
		exportButton.addActionListener(e -> {
			/* TODO: Add action */});

		buttonPanel.add(selectCharacterButton);
		buttonPanel.add(importButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(exportButton);

		return buttonPanel;
	}

	private static JPanel createButtonPanel2() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding

		JButton undoButton = new JButton("↩️ Undo");
		JButton restorePaletteButton = new JButton("♻️ Restore All");
		JButton restoreBackupButton = new JButton("🔄 From Backup");

		Dimension buttonSize = new Dimension(150, 25); // Reduce button height
		undoButton.setMaximumSize(buttonSize);
		restorePaletteButton.setMaximumSize(buttonSize);
		restoreBackupButton.setMaximumSize(buttonSize);

		// Placeholder for future action listeners
		undoButton.addActionListener(e -> {
			/* TODO: Add action */});
		restorePaletteButton.addActionListener(e -> {
			/* TODO: Add action */});
		restoreBackupButton.addActionListener(e -> {
			/* TODO: Add action */});

		buttonPanel.add(undoButton);
		buttonPanel.add(restorePaletteButton);
		buttonPanel.add(restoreBackupButton);

		return buttonPanel;
	}

	private static JPanel createTablePanel() {
		String[][] dados = new String[4][4];
		String[] colunas = { "Column 1", "Column 2", "Column 3", "Column 4" };
		tabela = new JTable(dados, colunas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabela.setDefaultRenderer(Object.class, new ColorRenderer());
		tabela.setTableHeader(null);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Select cell (0,0) at startup
		tabela.setRowSelectionInterval(0, 0);
		tabela.setColumnSelectionInterval(0, 0);

		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setPreferredSize(new Dimension(200, 50));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		tablePanel.add(createComboBoxPanel(), BorderLayout.NORTH);
		tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding

		return tablePanel;
	}

	private static JPanel createComboBoxPanel() {
		JPanel comboPanel = new JPanel(new BorderLayout());
		comboPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Adiciona um espaço de 10 pixels abaixo
		JComboBox<String> comboBox = new JComboBox<>(new String[] { "Original palette", "1st palette", "2nd palette",
				"3rd palette", "4th palette", "5th palette", "6th palette", "7th palette" });

		// Adiciona um ActionListener à combo box
		comboBox.addActionListener(e -> {
			String selectedItem = (String) comboBox.getSelectedItem();
			System.out.println("Selected palette: " + selectedItem);
			// Ação a ser realizada com o item selecionado
			String paletteIndex;
			if (selectedItem == "Original palette") {
				paletteIndex = "";
			} else {
				char firstNumber = selectedItem.charAt(0);
				paletteIndex = String.valueOf(firstNumber);
			}

			PALETTE_PATH = sorrPath + File.separator + "palettes" + File.separator + "chars" + File.separator
					+ ConfigManager.selectedPalette + paletteIndex + ".pal";
			System.out.println("Full path: " + PALETTE_PATH);

			initializeColors();

			changeImage();
		});

		comboPanel.add(comboBox, BorderLayout.CENTER);
		return comboPanel;
	}

	private static JPanel createColorChooserPanel() {
		ColorChooserPanel colorPanel = new ColorChooserPanel();
		colorPanel.addColorChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int row = tabela.getSelectedRow();
				int col = tabela.getSelectedColumn();
				if (row != -1 && col != -1) {
					alternateColors[row][col] = colorPanel.getSelectedColor();
					tabela.repaint();
				}
			}
		});
		return colorPanel;
	}

	private static JPanel createImagePanel() {
		imagePanel = new JPanel(new BorderLayout());
		imagePanel.setBorder(BorderFactory.createEmptyBorder()); // Remove borders

		originalImage = ImageColorChanger3.loadImage("images/"+ ConfigManager.selectedPalette + ".png");;

		// Resize Image
		Image scaledImage = resizeImage(originalImage);
		imageLabel.setIcon(new ImageIcon(scaledImage));

		// Add imageLabel directly to imagePanel (NO SCROLLPANE)
		imagePanel.add(imageLabel, BorderLayout.NORTH); // Align to top

		// Add MouseListener to imageLabel
		imageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleImageClick(e, imageLabel); // Now it correctly matches your original method's parameters
			}
		});

		return imagePanel;
	}

	// Método para redimensionar uma imagem
	public static Image resizeImage(BufferedImage originalImg) {
		int scaledWidth = originalImg.getWidth(null) * SCALE_FACTOR;
		int scaledHeight = originalImg.getHeight(null) * SCALE_FACTOR;
		// Resize Image
		Image scaledImage = originalImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		return scaledImage;
	}

	private static void changeImage() {
	    // Chama o método que processa a imagem e retorna um BufferedImage
	    BufferedImage originalImage = ImageColorChanger3.processImages();

	    // Verifica se a imagem foi processada corretamente
	    if (originalImage != null) {
	        // Redimensiona a imagem para 4x o tamanho original
	        BufferedImage resizedImage = ImageResizer.resizeImage(originalImage, 
	            originalImage.getWidth() * 4, 
	            originalImage.getHeight() * 4);

	        // Converte o BufferedImage redimensionado em ImageIcon
	        ImageIcon imageIcon = new ImageIcon(resizedImage);

	        // Define o ícone no JLabel
	        imageLabel.setIcon(imageIcon);
	    } else {
	        System.out.println("Erro ao processar a imagem.");
	    }
	}


	private static void handleImageClick(MouseEvent e, JLabel imageLabel) {
		if (!(originalImage instanceof BufferedImage))
			return;

		BufferedImage bufferedImage = (BufferedImage) originalImage;

		int originalX = e.getX() * bufferedImage.getWidth() / imageLabel.getWidth();
		int originalY = e.getY() * bufferedImage.getHeight() / imageLabel.getHeight();

		if (originalX >= 0 && originalY >= 0 && originalX < bufferedImage.getWidth()
				&& originalY < bufferedImage.getHeight()) {
			Color color = new Color(bufferedImage.getRGB(originalX, originalY));
			System.out.println("Clicked Color: " + color);
			selectMatchingColorCell(color);
		}
	}

	private static void selectMatchingColorCell(Color color) {
		for (int row = 0; row < tabela.getRowCount(); row++) {
			for (int col = 0; col < tabela.getColumnCount(); col++) {
				Component component = tabela.getCellRenderer(row, col).getTableCellRendererComponent(tabela,
						tabela.getValueAt(row, col), false, false, row, col);
				if (component.getBackground().equals(color)) {
					tabela.setRowSelectionInterval(row, row);
					tabela.setColumnSelectionInterval(col, col);
					System.out.printf("Cell [%d, %d] MATCHES the specified color!\n", row, col);
				}
			}
		}
	}
}
