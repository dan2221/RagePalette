package colorEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        // Obtém o componente padrão do renderizador
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Obtém a cor da célula a partir da matriz de cores
        Color cellColor = TabelaColorida.alternateColors[row][column];
        cell.setBackground(cellColor);

        // Verifica se esta célula é exatamente a célula selecionada
        if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
            // Para a célula selecionada, inverte a cor do texto e exibe "X" em negrito
            Color invertedColor = getNegativeColor(cellColor);
            setForeground(invertedColor);
            setText("X");
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(getFont().deriveFont(Font.BOLD)); // Define a fonte como negrito
        } else {
            // Caso contrário, exibe o texto original com fonte normal
            setForeground(Color.BLACK);
            setText(value != null ? value.toString() : "");
            setFont(getFont().deriveFont(Font.PLAIN)); // Garante que o restante do texto fique normal
        }

        // Configura a borda somente se a célula selecionada tiver o foco
        if (hasFocus && row == table.getSelectedRow() && column == table.getSelectedColumn()) {
            setBorder(javax.swing.BorderFactory.createLineBorder(getNegativeColor(cellColor), 2));
        } else {
            setBorder(null);
        }

        return cell;
    }

    // Método para calcular a cor invertida
    private Color getNegativeColor(Color color) {
        int red = 255 - color.getRed();
        int green = 255 - color.getGreen();
        int blue = 255 - color.getBlue();
        return new Color(red, green, blue);
    }
}
