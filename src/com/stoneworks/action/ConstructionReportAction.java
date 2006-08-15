/**
 * 
 */
package com.stoneworks.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.stoneworks.report.ConstructionReport;

/**
 * @author clinthill
 * 
 */
public class ConstructionReportAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5128113730254476741L;

	/**
	 * 
	 */
	public ConstructionReportAction(com.stoneworks.BrickCanvas c) {
		super();
		this.canvas = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		ConstructionReport constructionReport = new ConstructionReport();
		if(canvas != null) {
			constructionReport.getTable().addCanvas(canvas);
		}
		constructionReport.executeReport();
	}

	public com.stoneworks.BrickCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(com.stoneworks.BrickCanvas canvas) {
		this.canvas = canvas;
	}

	private com.stoneworks.BrickCanvas canvas = null;

}
