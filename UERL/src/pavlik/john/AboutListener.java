package pavlik.john;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// for copying style
		JLabel label = new JLabel();
		Font font = label.getFont();

		// create some css from the label's font
		StringBuffer style = new StringBuffer("font-family:" + font.getFamily()
				+ ";");
		style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
		style.append("font-size:" + font.getSize() + "pt;");

		// html content
		JEditorPane ep = new JEditorPane(
				"text/html",
				"<html><body style=\""
						+ style
						+ "\">" //
						+ "Application proposed by and created for 1LT Thomas Blaschke and application created by CPT John Pavlik "
						+ "of the 4th Special Troops Battalion, 4th Sustainment Brigade.<br /><br />"
						+ "You can reach John Pavlik for technical support at "
						+ "<a href=\"mailto:john.a.pavlik.mil@Mil.mil\">john.a.pavlik.mil@mail.mil</a>"
						+ "</body></html>");

		// handle link events
		ep.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					try {
						Desktop.getDesktop().mail((e.getURL().toURI()));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
			}
		});
		ep.setEditable(false);
		ep.setBackground(label.getBackground());

		// show
		JOptionPane.showMessageDialog(
				arg0.getSource() instanceof Component ? (Component) arg0
						.getSource() : null, ep);
	}

}
