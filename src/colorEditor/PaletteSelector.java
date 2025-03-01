package colorEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PaletteSelector {
    public static String showPaletteSelection() {
        JDialog dialog = new JDialog((Frame) null, "Select Palette", true);
        dialog.setSize(300, 400);
        dialog.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<String> names = PaletteFinder.NAMES;
        for (String name : names) {
            listModel.addElement(name);
        }

        JList<String> nameList = new JList<>(listModel);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(nameList);

        JTextField searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            private void filterList() {
                String filter = searchField.getText().toLowerCase();
                listModel.clear();
                for (String name : names) {
                    if (name.toLowerCase().contains(filter)) {
                        listModel.addElement(name);
                    }
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> {
            if (!nameList.isSelectionEmpty()) {
                dialog.setVisible(false);
            }
        });

        nameList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !nameList.isSelectionEmpty()) {
                    dialog.setVisible(false);
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(openButton);

        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return nameList.getSelectedValue();
    }
}
