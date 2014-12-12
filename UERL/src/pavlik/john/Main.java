package pavlik.john;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	private JFrame frame;
	static final ReadWriteListModel model = new ReadWriteListModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException
				| InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("UERL Application");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonPanel buttonPanel = new ButtonPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

		final JList<Unit> list = new JList<>(model);
		ImportUERLListener.addUnitListener(new UnitListener() {

			@Override
			public void unitAdded(Unit unit) {
				model.addUnit(unit);
			}
		});
		final JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("Delete Unit");
		jPopupMenu.add(deleteMenu);
		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteListUnits(list.getSelectedValuesList());
			}

		});

		list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				check(e);
			}

			public void mouseReleased(MouseEvent e) {
				check(e);
			}

			public void check(MouseEvent e) {
				if (e.isPopupTrigger()) { // if the event shows the menu
					list.setSelectedIndex(list.locationToIndex(e.getPoint())); // select
																				// the
																				// item
					if (list.getSelectedIndex() != -1)
						jPopupMenu.show(list, e.getX(), e.getY()); // and show
																	// the menu
				}
			}

		});
		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_DELETE) {
					return;
				}
				deleteListUnits(list.getSelectedValuesList());
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		frame.getContentPane().add(list, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmClose = new JMenuItem("Exit Program");
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		mntmClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnFile.add(mntmClose);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new AboutListener());
	}

	private void deleteListUnits(List<Unit> units) {
		for (Unit u : units) {
			model.removeUnit(u);
		}
	}
}
