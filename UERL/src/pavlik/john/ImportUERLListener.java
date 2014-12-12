package pavlik.john;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportUERLListener implements ActionListener {
	private static List<UnitListener> listeners = new ArrayList<>();

	@Override
	public void actionPerformed(ActionEvent arg0) {
		File[] openFiles = FileUtility.openFiles(new FileNameExtensionFilter(
				"UERL Excel Files (.xls, .xlsx)", "xls", ".xlsx"));
		try {
			if (openFiles != null) {
				for (File file : openFiles) {
					Unit unit = Parser.ParseUERL(file);
					fireUnitAdded(unit);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fireUnitAdded(Unit unit) {
		for (UnitListener l : listeners) {
			l.unitAdded(unit);
		}
	}

	public static void addUnitListener(UnitListener l) {
		listeners.add(l);
	}

	public static void removeUnitListener(UnitListener l) {
		listeners.remove(l);
	}

}
