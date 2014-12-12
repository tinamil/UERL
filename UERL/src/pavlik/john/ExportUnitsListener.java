package pavlik.john;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Workbook;

public class ExportUnitsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		File file = FileUtility.saveFile(new FileNameExtensionFilter(
				"Excel Files", "xls"));
		if (file == null)
			return;
		if (!file.getPath().toLowerCase().endsWith(".xls")) {
			file = new File(file.getPath() + ".xls");
		}
		if (file.exists()) {
			if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null,
					"File already exists.  Do you want to overwrite?",
					"File already exists.", JOptionPane.YES_NO_OPTION))
				return;
		}
		List<Unit> units = Main.model.getUnits();
		Workbook wb = Writer.writeUnits(units);

		// create a new file
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);

			// write the workbook to the output stream
			wb.write(out);

			// close our file (don't blow out our file handles
			out.close();
			JOptionPane.showMessageDialog(null, "File successfully saved.");
		} catch (IOException e1) {
			JOptionPane
					.showMessageDialog(null, "EXCEPTION: " + e1.getMessage());
			e1.printStackTrace();
		}
	}

}
