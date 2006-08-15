/**
 * 
 */
package com.stoneworks.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.stoneworks.report.DesignReviewReport;

/**
 * @author clinthill
 * 
 */
public class DesignReviewReportAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9207873585359496412L;

	/**
	 * 
	 */
	public DesignReviewReportAction(com.stoneworks.BrickCanvas c) {
		super();
		this.canvas = c;
	}

	public void actionPerformed(ActionEvent e) {
		DesignReviewReport designReport = new DesignReviewReport();
		if (canvas != null) {
			designReport.getTable().addCanvas(canvas);
		}
		designReport.executeReport();
	}

	public com.stoneworks.BrickCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(com.stoneworks.BrickCanvas canvas) {
		this.canvas = canvas;
	}

	private com.stoneworks.BrickCanvas canvas = null;

}
