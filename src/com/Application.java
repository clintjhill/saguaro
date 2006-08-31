/**
 * 
 */
package com;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
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
import java.awt.Toolkit;


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

	private JLabel aboutVersionLabel = null;

	private ProjectManager projectManager = null;
	
	private Project project = new Project();  //  @jve:decl-index=0:

	private BrickManager brickManager = null;

	private BrickCanvasManager brickCanvasManager = null;

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
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setTitle("Saguaro Stoneworks");
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			jFrame.setSize(640, 480);
			jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/stoneworks/icon/groundskeeper_16.png")));
			jFrame.setContentPane(getJContentPane());
			jFrame.setJMenuBar(getJJMenuBar());
			@SuppressWarnings("unused")
			CutSynchronizer cutSynch = new CutSynchronizer(
					getBrickCanvasManager().getBrickCanvas(), getBrickManager()
							.getCutList());
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getProjectManager(), BorderLayout.WEST);
			jContentPane.add(getBrickManager(), BorderLayout.EAST);
			jContentPane.add(getBrickCanvasManager(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(new javax.swing.JSeparator());
			fileMenu.add(getPrintInventoryMenuItem());
			fileMenu.add(getPrintTemplateMenuItem());
			fileMenu.add(getDesignReviewMenuItem());
			fileMenu.add(getConstructionMenuItem());
			fileMenu.add(new javax.swing.JSeparator());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 1.0");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	private JMenuItem getOpenMenuItem() {
		if(openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open...");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					Event.CTRL_MASK, true));
			openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					int option = chooser.showOpenDialog(Application.this.getProjectManager());
					if(option == JFileChooser.APPROVE_OPTION) {
						project.open(chooser.getSelectedFile().getPath());
						getBrickManager().setCutList(project.getCutList());
						getBrickCanvasManager().setBrickCanvas(project.getBrickCanvas());
						getProjectManager().setBrickCanvas(project.getBrickCanvas());
						getProjectManager().setClientName(project.getProjectClient());
						getProjectManager().setProjectName(project.getProjectName());
						@SuppressWarnings("unused")
						CutSynchronizer cutSynch = new CutSynchronizer(
								getBrickCanvasManager().getBrickCanvas(), getBrickManager()
										.getCutList());
					}
				}
			});
		}
		return openMenuItem;
	}
	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save...");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					project.setBrickCanvas(getBrickCanvasManager().getBrickCanvas());
					project.setCutList(getBrickManager().getCutList());
					project.setProjectClient(getProjectManager().getClientName());
					project.setProjectName(getProjectManager().getProjectName());
					if(!project.hasFilePath()) {
						JFileChooser chooser = new JFileChooser();
						int option = chooser.showSaveDialog(Application.this.getProjectManager());
						if(option == JFileChooser.APPROVE_OPTION) {
							project.save(chooser.getSelectedFile().getPath());
						}
					} else {
						project.save();
					}
				}
			});
		}
		return saveMenuItem;
	}

	private JMenuItem getPrintInventoryMenuItem() {
		if (printInventoryMenuItem == null) {
			printInventoryMenuItem = new JMenuItem();
			printInventoryMenuItem.setText("Print Inventory");
			printInventoryMenuItem.addActionListener(new InventoryReportAction(getBrickCanvasManager().getBrickCanvas()));
		}
		return printInventoryMenuItem;
	}

	private JMenuItem getPrintTemplateMenuItem() {
		if(printTemplateMenuItem == null) {
			printTemplateMenuItem = new JMenuItem();
			printTemplateMenuItem.setText("Print Templates");
			printTemplateMenuItem.addActionListener(new TemplateReportAction(getBrickCanvasManager().getBrickCanvas()));
		}
		return printTemplateMenuItem;
	}
	private JMenuItem getDesignReviewMenuItem() {
		if (printDesignReviewMenuItem == null) {
			printDesignReviewMenuItem = new JMenuItem();
			printDesignReviewMenuItem.setText("Print Design Review");
			printDesignReviewMenuItem.addActionListener(new DesignReviewReportAction(getBrickCanvasManager().getBrickCanvas()));
		}
		return printDesignReviewMenuItem;
	}

	private JMenuItem getConstructionMenuItem() {
		if(printConstructionMenuItem == null) {
			printConstructionMenuItem = new JMenuItem();
			printConstructionMenuItem.setText("Print Construction Review");
			printConstructionMenuItem.addActionListener(new ConstructionReportAction(getBrickCanvasManager().getBrickCanvas()));
		}
		return printConstructionMenuItem;
	}
	/**
	 * This method initializes projectManager
	 * 
	 * @return com.stoneworks.gui.ProjectManager
	 */
	private ProjectManager getProjectManager() {
		if (projectManager == null) {
			projectManager = new ProjectManager();
			projectManager.setBrickCanvas(getBrickCanvasManager().getBrickCanvas());
		}
		return projectManager;
	}

	/**
	 * This method initializes brickManager
	 * 
	 * @return com.stoneworks.gui.BrickManager
	 */
	private BrickManager getBrickManager() {
		if (brickManager == null) {
			brickManager = new BrickManager();
		}
		return brickManager;
	}

	/**
	 * This method initializes brickCanvasManager
	 * 
	 * @return com.stoneworks.gui.BrickCanvasManager
	 */
	private BrickCanvasManager getBrickCanvasManager() {
		if (brickCanvasManager == null) {
			brickCanvasManager = new BrickCanvasManager();
		}
		return brickCanvasManager;
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
