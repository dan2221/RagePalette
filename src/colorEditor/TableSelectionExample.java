package colorEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableSelectionExample {

	public static void main(String[] args) {
		// Create the frame
		JFrame frame = new JFrame("Table Selection Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);

		// Create a table model
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Column 1");
		model.addColumn("Column 2");

		// Add some sample data
		model.addRow(new Object[] { "Row 1, Cell 1", "Row 1, Cell 2" });
		model.addRow(new Object[] { "Row 2, Cell 1", "Row 2, Cell 2" });
		model.addRow(new Object[] { "Row 3, Cell 1", "Row 3, Cell 2" });

		// Create the table
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		// Create a button
		JButton selectButton = new JButton("Select Cell (1, 1)");

		// Add action listener to the button
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Select the cell at row 1, column 1 (second row, second column)
				table.setRowSelectionInterval(1, 1); // Select row 1
				table.setColumnSelectionInterval(1, 1); // Select column 1
				table.scrollRectToVisible(table.getCellRect(1, 1, true)); // Scroll to the selected cell
			}
		});

		// Add components to the frame
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(selectButton, BorderLayout.SOUTH);

		// Make the frame visible
		frame.setVisible(true);
	}
}
