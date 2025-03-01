package colorEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeListener;

public class ColorChooserPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JColorChooser colorChooser;

    public ColorChooserPanel() {
        colorChooser = new JColorChooser();
        colorChooser.setPreviewPanel(new JPanel()); // Hide preview panel

        // Remove sliders from ALL tabs
        for (AbstractColorChooserPanel panel : colorChooser.getChooserPanels()) {
            removeSliders(panel);
        }

        add(colorChooser);
    }

    // Recursively search & remove sliders from all panels
    private void removeSliders(Container container) {
        Component[] components = container.getComponents();
        for (Component comp : components) {
            if (comp instanceof JSlider) {
                container.remove(comp); // Remove sliders
            } else if (comp instanceof Container) {
                removeSliders((Container) comp); // Recursive check inside subpanels
            }
        }
        container.revalidate();
        container.repaint();
    }

    public void addColorChangeListener(ChangeListener listener) {
        colorChooser.getSelectionModel().addChangeListener(listener);
    }

    public Color getSelectedColor() {
        return colorChooser.getColor();
    }
}
