/**
 * 
 */
package com.stoneworks.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.stoneworks.BrickCanvas;

import edu.umd.cs.piccolo.PLayer;

import java.awt.Rectangle;

/**
 * @author clinthill
 * 
 */
public class BrickCanvasManager extends JPanel {

	private static final long serialVersionUID = 1L;

	private BrickCanvas brickCanvas = null;

	private BirdsEyeView birdsEyeView = null;

	private JPanel birdsEyeViewPanel = null;

	/**
	 * 
	 */
	public BrickCanvasManager() {
		super();
		this.initialize();
	}

	/**
	 * @param layout
	 */
	public BrickCanvasManager(LayoutManager layout) {
		super(layout);
		this.initialize();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public BrickCanvasManager(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.initialize();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public BrickCanvasManager(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(640, 480));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Brick Canvas",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		boolean validateNeeded = false;
		if (this.getComponentCount() > 0) {
			this.removeAll();
			validateNeeded = true;
		}
		this.add(this.getBrickCanvas(), BorderLayout.CENTER);
		this.add(this.getBirdsEyeViewPanel(), BorderLayout.SOUTH);
		if (validateNeeded) {
			this.validate();
		}
	}

	/**
	 * This method initializes brickCanvas
	 * 
	 * @return com.stoneworks.BrickCanvas
	 */
	public BrickCanvas getBrickCanvas() {
		if (this.brickCanvas == null) {
			this.brickCanvas = BrickCanvas.getInstance();
			this.brickCanvas.setCutTool(new com.stoneworks.Cutter(this.brickCanvas));
		}
		return this.brickCanvas;
	}

	public void setBrickCanvas(BrickCanvas c) {
		this.brickCanvas = c;
		this.brickCanvas.setCutTool(new com.stoneworks.Cutter(this.brickCanvas));
		this.birdsEyeView = null;
		this.birdsEyeViewPanel = null;
		this.initialize();
	}

	/**
	 * Adds a Brick to the canvas
	 * 
	 * @param b
	 */
	public void addBrick(com.stoneworks.Brick b) {
		this.getBrickCanvas().getLayer().addChild(b);
	}

	/**
	 * This method initializes birdsEyeView
	 * 
	 * @return com.stoneworks.gui.BirdsEyeView
	 */
	private BirdsEyeView getBirdsEyeView() {
		if (this.birdsEyeView == null) {
			this.birdsEyeView = new BirdsEyeView();
			this.birdsEyeView.setBounds(new Rectangle(10, 20, 128, 96));
			this.birdsEyeView.connect(this.getBrickCanvas(),
					new PLayer[] { this.getBrickCanvas().getLayer() });
		}
		return this.birdsEyeView;
	}

	/**
	 * This method initializes birdsEyeViewPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBirdsEyeViewPanel() {
		if (this.birdsEyeViewPanel == null) {
			this.birdsEyeViewPanel = new JPanel();
			this.birdsEyeViewPanel.setLayout(null);
			this.birdsEyeViewPanel.setPreferredSize(new Dimension(480, 125));
			this.birdsEyeViewPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Birds Eye View", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			this.birdsEyeViewPanel.add(this.getBirdsEyeView(), null);
		}
		return this.birdsEyeViewPanel;
	}

}
