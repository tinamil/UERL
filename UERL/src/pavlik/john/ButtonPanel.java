package pavlik.john;

import javax.swing.JPanel;
import javax.swing.JButton;

public class ButtonPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ButtonPanel() {
		
		JButton btnImportUerl = new JButton("Import UERL");
		add(btnImportUerl);
		btnImportUerl.addActionListener(new ImportUERLListener());
		
		JButton btnExportToExcel = new JButton("Export to Excel");
		add(btnExportToExcel);
		btnExportToExcel.addActionListener(new ExportUnitsListener());
	}

}
