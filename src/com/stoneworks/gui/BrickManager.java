/**
 * 
 */
package com.stoneworks.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.stoneworks.CutList;
import com.stoneworks.math.StandardMeasurement;

/**
 * @author clinthill
 * 
 */
public class BrickManager extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BrickManager() {
		super();
		this.initialize();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public BrickManager(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.initialize();
	}

	/**
	 * @param layout
	 */
	public BrickManager(LayoutManager layout) {
		super(layout);
		this.initialize();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public BrickManager(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.initialize();
	}

	/**
	 * This method initializes addBrick
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddBrick() {
		if (this.addBrick == null) {
			this.addBrick = new JButton();
			this.addBrick.setText("Add Brick");
			this.addBrick.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					StandardMeasurement width = BrickManager.this.getWidthMeasurement()
							.getMeasurement();
					StandardMeasurement length = BrickManager.this.getLengthMeasurement()
							.getMeasurement();
					java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(
							0, 0, width.screenValue(), length.screenValue());
					java.awt.geom.Area shape = new java.awt.geom.Area(rect);
					com.stoneworks.Brick brick = new com.stoneworks.Brick(
							shape, BrickManager.this.getColorPanel().getBackground());
					if (!BrickManager.this.getCutList().contains(brick)) {
						BrickManager.this.getCutList().addElement(brick);
					}
				}
			});
		}
		return this.addBrick;
	}

	public CutList getCutList() {
		if (this.cutListModel == null) {
			this.cutListModel = new CutList();
		}
		return this.cutListModel;
	}

	/**
	 * 
	 * @param c
	 */
	public void setCutList(CutList c) {
		this.cutListModel = c;
		this.cutList.setModel(this.cutListModel);
	}

	/**
	 * This method initializes brickEditor
	 * 
	 * @return JPanel
	 */
	private JPanel getBrickEditor() {
		if (this.brickEditor == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridwidth = 2;
			gridBagConstraints7.gridy = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridwidth = 2;
			gridBagConstraints6.weightx = 1.0;
			this.brickEditor = new JPanel();
			this.brickEditor.setLayout(new GridBagLayout());
			this.brickEditor.setBorder(BorderFactory.createTitledBorder(null,
					"Brick Maker", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			GridBagConstraints widthLabelConstraints = new GridBagConstraints();
			widthLabelConstraints.gridx = 0;
			widthLabelConstraints.gridy = 0;
			this.widthLabel = new JLabel();
			this.widthLabel.setText("Width:");
			this.brickEditor.add(this.widthLabel, widthLabelConstraints);
			GridBagConstraints lengthLabelConstraints = new GridBagConstraints();
			lengthLabelConstraints.gridx = 0;
			lengthLabelConstraints.gridy = 1;
			this.lengthLabel = new JLabel();
			this.lengthLabel.setText("Length:");
			this.brickEditor.add(this.lengthLabel, lengthLabelConstraints);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			this.colorLabel = new JLabel();
			this.colorLabel.setText("Color:");
			this.brickEditor.add(this.colorLabel, gridBagConstraints);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			this.brickEditor.add(this.getColorButton(), gridBagConstraints2);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridwidth = 3;
			this.brickEditor.add(this.getAddBrick(), gridBagConstraints5);
			this.brickEditor.add(this.getWidthMeasurement(), gridBagConstraints6);
			this.brickEditor.add(this.getLengthMeasurement(), gridBagConstraints7);
			this.brickEditor.add(this.getColorPanel(), gridBagConstraints11);
		}
		return this.brickEditor;
	}

	/**
	 * This method initializes colorButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getColorButton() {
		if (this.colorButton == null) {
			this.colorButton = new JButton();
			this.colorButton.setText("Choose...");
			this.colorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Thread colorThread = new Thread() {
						@Override
						public void run() {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									Color color = JColorChooser.showDialog(
											BrickManager.this.getBrickEditor(),
											"Pick a color for the brick",
											Color.ORANGE);
									BrickManager.this.getColorPanel().setBackground(color);
								}
							});
						}
					};
					colorThread.start();
				}
			});
		}
		return this.colorButton;
	}

	/**
	 * This method initializes cutList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (this.cutList == null) {
			this.cutList = new JList();
			this.cutList.setModel(this.getCutList());
			this.cutList.setDragEnabled(true);
			this.cutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.cutList.setCellRenderer(new com.stoneworks.gui.BrickLabel());
			this.cutList
					.setTransferHandler(new com.stoneworks.BrickTransferHandler());
		}
		return this.cutList;
	}

	/**
	 * This method initializes jScroll
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScroll() {
		if (this.jScroll == null) {
			this.jScroll = new JScrollPane();
			this.jScroll.setViewportView(this.getJList());
		}
		return this.jScroll;
	}

	/**
	 * This method initializes cutListPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCutListPanel() {
		if (this.cutListPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			this.cutListPanel = new JPanel();
			this.cutListPanel.setLayout(new GridBagLayout());
			this.cutListPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Brick Cut List", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			this.cutListPanel.add(this.getJScroll(), gridBagConstraints1);
		}
		return this.cutListPanel;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(200, 400);
		this.setPreferredSize(new Dimension(200, 400));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.getBrickEditor(), null);
		this.add(this.getCutListPanel(), null);
	}

	private JButton addBrick = null;

	private JPanel brickEditor = null;

	private JButton colorButton = null;

	private JLabel colorLabel = null;

	private JList cutList = null;

	private JPanel cutListPanel = null;

	private JLabel lengthLabel = null;

	private JLabel widthLabel = null;

	private MeasurementPanel widthMeasurement = null;

	private MeasurementPanel lengthMeasurement = null;

	private CutList cutListModel = null;

	private JScrollPane jScroll = null;

	private JPanel colorPanel = null;

	/**
	 * This method initializes widthMeasurement
	 * 
	 * @return com.stoneworks.gui.MeasurementPanel
	 */
	private MeasurementPanel getWidthMeasurement() {
		if (this.widthMeasurement == null) {
			this.widthMeasurement = new MeasurementPanel();
		}
		return this.widthMeasurement;
	}

	/**
	 * This method initializes lengthMeasurement
	 * 
	 * @return com.stoneworks.gui.MeasurementPanel
	 */
	private MeasurementPanel getLengthMeasurement() {
		if (this.lengthMeasurement == null) {
			this.lengthMeasurement = new MeasurementPanel();
		}
		return this.lengthMeasurement;
	}

	/**
	 * This method initializes colorPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getColorPanel() {
		if (this.colorPanel == null) {
			this.colorPanel = new JPanel();
			this.colorPanel.setLayout(null);
			this.colorPanel.setBackground(new java.awt.Color(102, 51, 0));
			this.colorPanel.setToolTipText("The color of the Brick");
			this.colorPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			this.colorPanel.setPreferredSize(new Dimension(15, 15));
		}
		return this.colorPanel;
	}

}
