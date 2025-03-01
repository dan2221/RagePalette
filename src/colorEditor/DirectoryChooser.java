package colorEditor;

import javax.swing.*;
import java.io.File;

public class DirectoryChooser {
	public static File chooseDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Sorr Path");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
}
