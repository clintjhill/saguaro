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
		initialize();
	}

	/**
	 * @param layout
	 */
	public BrickCanvasManager(LayoutManager layout) {
		super(layout);
		initialize();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public BrickCanvasManager(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public BrickCanvasManager(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(640,480));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Brick Canvas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		boolean validateNeeded = false;
		if(this.getComponentCount() > 0) { 
			this.removeAll(); 
			validateNeeded = true;
		}
		this.add(getBrickCanvas(), BorderLayout.CENTER);
		this.add(getBirdsEyeViewPanel(), BorderLayout.SOUTH);
		if(validateNeeded) this.validate();
	}

	/**
	 * This method initializes brickCanvas	
	 * 	
	 * @return com.stoneworks.BrickCanvas	
	 */
	public BrickCanvas getBrickCanvas() {
		if (brickCanvas == null) {
			brickCanvas = BrickCanvas.getInstance();
			brickCanvas.setCutTool(new com.stoneworks.Cutter(brickCanvas));
		}
		return brickCanvas;
	}
	public void setBrickCanvas(BrickCanvas c) {
		this.brickCanvas = c;
		brickCanvas.setCutTool(new com.stoneworks.Cutter(brickCanvas));
		this.birdsEyeView = null;
		this.birdsEyeViewPanel = null;
		initialize();
	}
	/**
	 * Adds a Brick to the canvas
	 * @param b
	 */
	public void addBrick(com.stoneworks.Brick b) {
		getBrickCanvas().getLayer().addChild(b);
	}
	
	/**
	 * This method initializes birdsEyeView	
	 * 	
	 * @return com.stoneworks.gui.BirdsEyeView	
	 */
	private BirdsEyeView getBirdsEyeView() {
		if (birdsEyeView == null) {
			birdsEyeView = new BirdsEyeView();
			birdsEyeView.setBounds(new Rectangle(10, 20, 128, 96));
			birdsEyeView.connect(getBrickCanvas(), new PLayer[]{getBrickCanvas().getLayer()});
		}
		return birdsEyeView;
	}

	/**
	 * This method initializes birdsEyeViewPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getBirdsEyeViewPanel() {
		if (birdsEyeViewPanel == null) {
			birdsEyeViewPanel = new JPanel();
			birdsEyeViewPanel.setLayout(null);
			birdsEyeViewPanel.setPreferredSize(new Dimension(480,125));
			birdsEyeViewPanel.setBorder(BorderFactory.createTitledBorder(null, "Birds Eye View", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			birdsEyeViewPanel.add(getBirdsEyeView(), null);
		}
		return birdsEyeViewPanel;
	}

}
