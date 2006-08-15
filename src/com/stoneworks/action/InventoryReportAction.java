/**
 * 
 */
package com.stoneworks.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.stoneworks.Brick;
import com.stoneworks.report.BrickInventoryReport;

/**
 * @author clinthill
 * 
 */
public class InventoryReportAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 253898896597597770L;

	/**
	 * 
	 * @param c
	 * @param l
	 */
	public InventoryReportAction(com.stoneworks.BrickCanvas c) {
		super();
		this.canvas = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		BrickInventoryReport report = new BrickInventoryReport();
		
		if (canvas != null) {
			for (Object obj : canvas.getLayer().getChildrenReference()) {
				if (obj instanceof Brick) {
					Brick brick = (Brick) obj;
					report.getTable().addBrick(brick);
				}
			}
		}
		
		report.executeReport();
	}

	public com.stoneworks.BrickCanvas getCanvas() {
		return canvas;
	}


	public void setCanvas(com.stoneworks.BrickCanvas canvas) {
		this.canvas = canvas;
	}


	private com.stoneworks.BrickCanvas canvas = null;


}
