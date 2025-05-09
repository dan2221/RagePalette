package colorEditor;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// Get the default renderer component
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Set the default background color based on the color matrix
		if (!isSelected) {
			cell.setBackground(TabelaColorida.alternateColors[row][column]); // Use color from the jColors matrix
			cell.setForeground(Color.BLACK); // Default text color
		} else {
			cell.setBackground(TabelaColorida.alternateColors[row][column]); // No change for background on selection
			cell.setForeground(Color.BLACK); // Default text color
		}

		// Handle the focus outline (border) when a cell has focus
		if (hasFocus) {
			// Calculate the negative color of the background color
			Color currentBackground = TabelaColorida.alternateColors[row][column];
			Color negativeColor = getNegativeColor(currentBackground);

			// Set the border to the negative color of the current background
			setBorder(javax.swing.BorderFactory.createLineBorder(negativeColor, 2)); // Apply the border with the
																						// negative color
		} else {
			setBorder(null); // Remove border when the cell does not have focus
		}

		return cell;
	}

	// Method to calculate the negative color of the given color
	private Color getNegativeColor(Color color) {
		int red = 255 - color.getRed();
		int green = 255 - color.getGreen();
		int blue = 255 - color.getBlue();
		return new Color(red, green, blue);
	}
}
