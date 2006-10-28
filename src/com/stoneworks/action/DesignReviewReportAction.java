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
	public DesignReviewReportAction() {
		super();
	}

	public void actionPerformed(ActionEvent e) {
		DesignReviewReport designReport = new DesignReviewReport();
		designReport.executeReport();
	}
}
