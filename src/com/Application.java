/**
 * 
 */
package com;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.stoneworks.CutSynchronizer;
import com.stoneworks.Project;
import com.stoneworks.action.ConstructionReportAction;
import com.stoneworks.action.DesignReviewReportAction;
import com.stoneworks.action.InventoryReportAction;
import com.stoneworks.action.TemplateReportAction;
import com.stoneworks.gui.BrickCanvasManager;
import com.stoneworks.gui.BrickManager;
import com.stoneworks.gui.ProjectManager;

/**
 * @author clinthill
 * 
 */
public class Application {

	private JFrame jFrame = null;

	private JPanel jContentPane = null;

	private JMenuBar jJMenuBar = null;

	private JMenu fileMenu = null;

	private JMenu editMenu = null;

	private JMenu helpMenu = null;

	private JMenuItem exitMenuItem = null;

	private JMenuItem aboutMenuItem = null;

	private JMenuItem cutMenuItem = null;

	private JMenuItem copyMenuItem = null;

	private JMenuItem pasteMenuItem = null;

	private JMenuItem saveMenuItem = null;

	private JMenuItem openMenuItem = null;

	private JMenuItem printInventoryMenuItem = null;

	private JMenuItem printDesignReviewMenuItem = null;

	private JMenuItem printConstructionMenuItem = null;

	private JMenuItem printTemplateMenuItem = null;

	private JDialog aboutDialog = null;

	private JPanel aboutContentPane = null;

	private ProjectManager projectManager = null;

	private Project project = new Project(); // @jve:decl-index=0:

	private BrickManager brickManager = null;

	private BrickCanvasManager brickCanvasManager = null;

	private JEditorPane aboutText = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 
	 */
	public Application() {
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (this.jFrame == null) {
			this.jFrame = new JFrame();
			this.jFrame.setTitle("Saguaro Stoneworks");
			this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.jFrame.setSize(640, 480);
			this.jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(
					this.getClass().getResource(
							"/com/stoneworks/icon/groundskeeper_16.png")));
			this.jFrame.setContentPane(this.getJContentPane());
			this.jFrame.setJMenuBar(this.getJJMenuBar());
			@SuppressWarnings("unused")
			CutSynchronizer cutSynch = new CutSynchronizer(
					this.getBrickCanvasManager().getBrickCanvas(), this.getBrickManager()
							.getCutList());
		}
		return this.jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(new BorderLayout());
			this.jContentPane.add(this.getProjectManager(), BorderLayout.WEST);
			this.jContentPane.add(this.getBrickManager(), BorderLayout.EAST);
			this.jContentPane.add(this.getBrickCanvasManager(), BorderLayout.CENTER);
		}
		return this.jContentPane;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (this.jJMenuBar == null) {
			this.jJMenuBar = new JMenuBar();
			this.jJMenuBar.add(this.getFileMenu());
			this.jJMenuBar.add(this.getEditMenu());
			this.jJMenuBar.add(this.getHelpMenu());
		}
		return this.jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (this.fileMenu == null) {
			this.fileMenu = new JMenu();
			this.fileMenu.setText("File");
			this.fileMenu.add(this.getOpenMenuItem());
			this.fileMenu.add(this.getSaveMenuItem());
			this.fileMenu.add(new javax.swing.JSeparator());
			this.fileMenu.add(this.getPrintInventoryMenuItem());
			this.fileMenu.add(this.getPrintTemplateMenuItem());
			this.fileMenu.add(this.getDesignReviewMenuItem());
			this.fileMenu.add(this.getConstructionMenuItem());
			this.fileMenu.add(new javax.swing.JSeparator());
			this.fileMenu.add(this.getExitMenuItem());
		}
		return this.fileMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu() {
		if (this.editMenu == null) {
			this.editMenu = new JMenu();
			this.editMenu.setText("Edit");
			this.editMenu.add(this.getCutMenuItem());
			this.editMenu.add(this.getCopyMenuItem());
			this.editMenu.add(this.getPasteMenuItem());
		}
		return this.editMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (this.helpMenu == null) {
			this.helpMenu = new JMenu();
			this.helpMenu.setText("Help");
			this.helpMenu.add(this.getAboutMenuItem());
		}
		return this.helpMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (this.exitMenuItem == null) {
			this.exitMenuItem = new JMenuItem();
			this.exitMenuItem.setText("Exit");
			this.exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return this.exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (this.aboutMenuItem == null) {
			this.aboutMenuItem = new JMenuItem();
			this.aboutMenuItem.setText("About");
			this.aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = Application.this.getAboutDialog();
					aboutDialog.pack();
					Point loc = Application.this.getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return this.aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (this.aboutDialog == null) {
			this.aboutDialog = new JDialog(this.getJFrame(), true);
			this.aboutDialog.setTitle("About");
			aboutDialog.setPreferredSize(new Dimension(400, 400));
			this.aboutDialog.setContentPane(this.getAboutContentPane());
		}
		return this.aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (this.aboutContentPane == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			this.aboutContentPane = new JPanel();
			this.aboutContentPane.setLayout(new GridBagLayout());
			aboutContentPane.add(getJScrollPane(), gridBagConstraints);
		}
		return this.aboutContentPane;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCutMenuItem() {
		if (this.cutMenuItem == null) {
			this.cutMenuItem = new JMenuItem();
			this.cutMenuItem.setText("Cut");
			this.cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return this.cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCopyMenuItem() {
		if (this.copyMenuItem == null) {
			this.copyMenuItem = new JMenuItem();
			this.copyMenuItem.setText("Copy");
			this.copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return this.copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPasteMenuItem() {
		if (this.pasteMenuItem == null) {
			this.pasteMenuItem = new JMenuItem();
			this.pasteMenuItem.setText("Paste");
			this.pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return this.pasteMenuItem;
	}

	private JMenuItem getOpenMenuItem() {
		if (this.openMenuItem == null) {
			this.openMenuItem = new JMenuItem();
			this.openMenuItem.setText("Open...");
			this.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					Event.CTRL_MASK, true));
			this.openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					int option = chooser.showOpenDialog(Application.this
							.getProjectManager());
					if (option == JFileChooser.APPROVE_OPTION) {
						Application.this.project.open(chooser.getSelectedFile().getPath());
						Application.this.getBrickManager().setCutList(Application.this.project.getCutList());
						Application.this.getBrickCanvasManager().setBrickCanvas(
								Application.this.project.getBrickCanvas());
						Application.this.getProjectManager().setBrickCanvas(
								Application.this.project.getBrickCanvas());
						Application.this.getProjectManager().setClientName(
								Application.this.project.getProjectClient());
						Application.this.getProjectManager().setProjectName(
								Application.this.project.getProjectName());
						@SuppressWarnings("unused")
						CutSynchronizer cutSynch = new CutSynchronizer(
								Application.this.getBrickCanvasManager().getBrickCanvas(),
								Application.this.getBrickManager().getCutList());
					}
				}
			});
		}
		return this.openMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem() {
		if (this.saveMenuItem == null) {
			this.saveMenuItem = new JMenuItem();
			this.saveMenuItem.setText("Save...");
			this.saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			this.saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Application.this.project.setBrickCanvas(Application.this.getBrickCanvasManager()
							.getBrickCanvas());
					Application.this.project.setCutList(Application.this.getBrickManager().getCutList());
					Application.this.project.setProjectClient(Application.this.getProjectManager()
							.getClientName());
					Application.this.project
							.setProjectName(Application.this.getProjectManager()
									.getProjectName());
					if (!Application.this.project.hasFilePath()) {
						JFileChooser chooser = new JFileChooser();
						int option = chooser.showSaveDialog(Application.this
								.getProjectManager());
						if (option == JFileChooser.APPROVE_OPTION) {
							Application.this.project.save(chooser.getSelectedFile().getPath());
						}
					} else {
						Application.this.project.save();
					}
				}
			});
		}
		return this.saveMenuItem;
	}

	private JMenuItem getPrintInventoryMenuItem() {
		if (this.printInventoryMenuItem == null) {
			this.printInventoryMenuItem = new JMenuItem();
			this.printInventoryMenuItem.setText("Print Inventory");
			this.printInventoryMenuItem
					.addActionListener(new InventoryReportAction());
		}
		return this.printInventoryMenuItem;
	}

	private JMenuItem getPrintTemplateMenuItem() {
		if (this.printTemplateMenuItem == null) {
			this.printTemplateMenuItem = new JMenuItem();
			this.printTemplateMenuItem.setText("Print Templates");
			this.printTemplateMenuItem.addActionListener(new TemplateReportAction());
		}
		return this.printTemplateMenuItem;
	}

	private JMenuItem getDesignReviewMenuItem() {
		if (this.printDesignReviewMenuItem == null) {
			this.printDesignReviewMenuItem = new JMenuItem();
			this.printDesignReviewMenuItem.setText("Print Design Review");
			this.printDesignReviewMenuItem
					.addActionListener(new DesignReviewReportAction());
		}
		return this.printDesignReviewMenuItem;
	}

	private JMenuItem getConstructionMenuItem() {
		if (this.printConstructionMenuItem == null) {
			this.printConstructionMenuItem = new JMenuItem();
			this.printConstructionMenuItem.setText("Print Construction Review");
			this.printConstructionMenuItem
					.addActionListener(new ConstructionReportAction());
		}
		return this.printConstructionMenuItem;
	}

	/**
	 * This method initializes projectManager
	 * 
	 * @return com.stoneworks.gui.ProjectManager
	 */
	private ProjectManager getProjectManager() {
		if (this.projectManager == null) {
			this.projectManager = new ProjectManager();
			this.projectManager.setBrickCanvas(this.getBrickCanvasManager()
					.getBrickCanvas());
		}
		return this.projectManager;
	}

	/**
	 * This method initializes brickManager
	 * 
	 * @return com.stoneworks.gui.BrickManager
	 */
	private BrickManager getBrickManager() {
		if (this.brickManager == null) {
			this.brickManager = new BrickManager();
		}
		return this.brickManager;
	}

	/**
	 * This method initializes brickCanvasManager
	 * 
	 * @return com.stoneworks.gui.BrickCanvasManager
	 */
	private BrickCanvasManager getBrickCanvasManager() {
		if (this.brickCanvasManager == null) {
			this.brickCanvasManager = new BrickCanvasManager();
		}
		return this.brickCanvasManager;
	}

	/**
	 * This method initializes aboutText	
	 * 	
	 * @return javax.swing.JEditorPane	
	 * @throws IOException 
	 */
	private JEditorPane getAboutText() {
		if (aboutText == null) {
			try {
				aboutText = new JEditorPane(this.getClass().getResource("about-dialog.htm"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			aboutText.setContentType("text/html");
		}
		return aboutText;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getAboutText());
		}
		return jScrollPane;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Application application = new Application();
				application.getJFrame().setVisible(true);
			}
		});
	}

}
