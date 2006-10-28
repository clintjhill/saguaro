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
	public InventoryReportAction() {
		super();
	}

	public void actionPerformed(ActionEvent e) {
		BrickInventoryReport report = new BrickInventoryReport();
		for (Brick b : canvas.getBricks()) {
			report.getTable().addBrick(b);
		}
		report.executeReport();
	}


	private com.stoneworks.BrickCanvas canvas = com.stoneworks.BrickCanvas.getInstance();


}
