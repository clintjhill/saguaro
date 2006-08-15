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
		initialize();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public BrickManager(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	/**
	 * @param layout
	 */
	public BrickManager(LayoutManager layout) {
		super(layout);
		initialize();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public BrickManager(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	/**
	 * This method initializes addBrick
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddBrick() {
		if (addBrick == null) {
			addBrick = new JButton();
			addBrick.setText("Add Brick");
			addBrick.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					StandardMeasurement width = getWidthMeasurement()
							.getMeasurement();
					StandardMeasurement length = getLengthMeasurement()
							.getMeasurement();
					java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(
							0, 0, width.screenValue(), length.screenValue());
					java.awt.geom.Area shape = new java.awt.geom.Area(rect);
					com.stoneworks.Brick brick = new com.stoneworks.Brick(
							shape, getColorPanel().getBackground());
					if (!getCutList().contains(brick)) {
						getCutList().addElement(brick);
					}
				}
			});
		}
		return addBrick;
	}

	public CutList getCutList() {
		if (cutListModel == null) {
			cutListModel = new CutList();
		}
		return cutListModel;
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
		if (brickEditor == null) {
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
			brickEditor = new JPanel();
			brickEditor.setLayout(new GridBagLayout());
			brickEditor.setBorder(BorderFactory.createTitledBorder(null,
					"Brick Maker", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			GridBagConstraints widthLabelConstraints = new GridBagConstraints();
			widthLabelConstraints.gridx = 0;
			widthLabelConstraints.gridy = 0;
			widthLabel = new JLabel();
			widthLabel.setText("Width:");
			brickEditor.add(widthLabel, widthLabelConstraints);
			GridBagConstraints lengthLabelConstraints = new GridBagConstraints();
			lengthLabelConstraints.gridx = 0;
			lengthLabelConstraints.gridy = 1;
			lengthLabel = new JLabel();
			lengthLabel.setText("Length:");
			brickEditor.add(lengthLabel, lengthLabelConstraints);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			colorLabel = new JLabel();
			colorLabel.setText("Color:");
			brickEditor.add(colorLabel, gridBagConstraints);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			brickEditor.add(getColorButton(), gridBagConstraints2);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridwidth = 3;
			brickEditor.add(getAddBrick(), gridBagConstraints5);
			brickEditor.add(getWidthMeasurement(), gridBagConstraints6);
			brickEditor.add(getLengthMeasurement(), gridBagConstraints7);
			brickEditor.add(getColorPanel(), gridBagConstraints11);
		}
		return brickEditor;
	}

	/**
	 * This method initializes colorButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getColorButton() {
		if (colorButton == null) {
			colorButton = new JButton();
			colorButton.setText("Choose...");
			colorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Thread colorThread = new Thread() {
						public void run() {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									Color color = JColorChooser.showDialog(
											getBrickEditor(),
											"Pick a color for the brick",
											Color.ORANGE);
									getColorPanel().setBackground(color);
								}
							});
						}
					};
					colorThread.start();
				}
			});
		}
		return colorButton;
	}

	/**
	 * This method initializes cutList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (cutList == null) {
			cutList = new JList();
			cutList.setModel(getCutList());
			cutList.setDragEnabled(true);
			cutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cutList.setCellRenderer(new com.stoneworks.gui.BrickLabel());
			cutList
					.setTransferHandler(new com.stoneworks.BrickTransferHandler());
		}
		return cutList;
	}

	/**
	 * This method initializes jScroll
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScroll() {
		if (jScroll == null) {
			jScroll = new JScrollPane();
			jScroll.setViewportView(getJList());
		}
		return jScroll;
	}

	/**
	 * This method initializes cutListPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCutListPanel() {
		if (cutListPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			cutListPanel = new JPanel();
			cutListPanel.setLayout(new GridBagLayout());
			cutListPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Brick Cut List", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			cutListPanel.add(getJScroll(), gridBagConstraints1);
		}
		return cutListPanel;
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
		this.add(getBrickEditor(), null);
		this.add(getCutListPanel(), null);
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
		if (widthMeasurement == null) {
			widthMeasurement = new MeasurementPanel();
		}
		return widthMeasurement;
	}

	/**
	 * This method initializes lengthMeasurement
	 * 
	 * @return com.stoneworks.gui.MeasurementPanel
	 */
	private MeasurementPanel getLengthMeasurement() {
		if (lengthMeasurement == null) {
			lengthMeasurement = new MeasurementPanel();
		}
		return lengthMeasurement;
	}

	/**
	 * This method initializes colorPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getColorPanel() {
		if (colorPanel == null) {
			colorPanel = new JPanel();
			colorPanel.setLayout(null);
			colorPanel.setBackground(new java.awt.Color(102, 51, 0));
			colorPanel.setToolTipText("The color of the Brick");
			colorPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			colorPanel.setPreferredSize(new Dimension(15, 15));
		}
		return colorPanel;
	}

}
