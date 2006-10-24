/**
 * 
 */
package com.stoneworks.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.stoneworks.Brick;
import com.stoneworks.report.BrickTemplateReport;

/**
 * @author clinthill
 *
 */
public class TemplateReportAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 754931932743818173L;

	/**
	 * 
	 */
	public TemplateReportAction(com.stoneworks.BrickCanvas c) {
		super();
		this.canvas = c;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		BrickTemplateReport report = new BrickTemplateReport();
		if (canvas != null) {
			for (Object obj : canvas.getBricks()) {
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
