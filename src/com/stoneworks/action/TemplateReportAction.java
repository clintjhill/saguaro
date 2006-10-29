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
	public TemplateReportAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		BrickTemplateReport report = new BrickTemplateReport();
		for (Brick b : this.canvas.getBricks()) {
			report.getTable().addBrick(b);
		}
		report.executeReport();
	}

	private com.stoneworks.BrickCanvas canvas = com.stoneworks.BrickCanvas
			.getInstance();
}
