package pavlik.john;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUtility {

	private final static JFileChooser fc = new JFileChooser();

	private static File[] chooseFile(boolean open,
			FileNameExtensionFilter filter) {
		if (filter != null)
			fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(open);

		int returnVal = -1;
		if (open) {
			returnVal = fc.showOpenDialog(null);
		} else {
			returnVal = fc.showSaveDialog(null);
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (open)
				return fc.getSelectedFiles();
			else
				return new File[] { fc.getSelectedFile() };
		} else {
			return null;
		}
	}

	public static File[] openFiles(FileNameExtensionFilter filter) {
		return chooseFile(true, filter);
	}

	public static File saveFile(FileNameExtensionFilter filter) {
		File[] files = chooseFile(false, filter);
		if (files.length != 1) {
			throw new RuntimeException(
					"Too many (or few) files returned during save operation. "
							+ files.length + " files returned.");
		}
		return files[0];
	}
}
