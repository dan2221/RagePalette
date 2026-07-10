package colorEditor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

/**
 * Custom JTable that displays tooltips with hex color code and RGB values
 * when hovering over color cells.
 */
public class ColorTooltipTable extends JTable {
    private static final long serialVersionUID = 1L;

    @Override
    public String getToolTipText(MouseEvent event) {
        int row = rowAtPoint(event.getPoint());
        int column = columnAtPoint(event.getPoint());

        if (row != -1 && column != -1) {
            Color cellColor = TabelaColorida.alternateColors[row][column];
            return formatColorTooltip(cellColor);
        }

        return super.getToolTipText(event);
    }

    /**
     * Formats the color information into a readable tooltip string.
     * Shows hex code and RGB values.
     *
     * @param color The color to format
     * @return Formatted tooltip string with color information
     */
    private String formatColorTooltip(Color color) {
        String hex = String.format("#%06X", (0xFFFFFF & color.getRGB()));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return String.format(
            "<html><b>Color Information</b><br>" +
            "HEX: %s<br>" +
            "RGB: (%d, %d, %d)</html>",
            hex, red, green, blue
        );
    }
}
