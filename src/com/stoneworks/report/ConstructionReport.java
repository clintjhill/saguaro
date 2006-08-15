/**
 * 
 */
package com.stoneworks.report;

import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;
import org.jfree.report.modules.gui.base.PreviewDialog;
import org.jfree.report.modules.parser.base.ReportGenerator;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ObjectUtilities;

/**
 * @author clinthill
 * 
 */
public class ConstructionReport {

	/**
	 * 
	 */
	public ConstructionReport() {
		table = new ConstructionTable();
		JFreeReportBoot.getInstance().start();
	}

	/**
	 * Parses a report and displays a non-modal preview dialog.
	 * 
	 * @param in
	 *            the URL to the Report Definition XML file
	 * @param data
	 *            a valid tablemodel.
	 */
	public void executeReport() {
		try {
			java.net.URL url = ObjectUtilities.getResourceRelative(
					"construction.xml", ConstructionReport.class);
			final ReportGenerator generator = ReportGenerator.getInstance();
			JFreeReport report = generator.parseReport(url);
			report.setData(this.table);

			final PreviewDialog frame = new PreviewDialog(report);
			frame.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
			frame.pack();
			RefineryUtilities.positionFrameRandomly(frame);
			frame.setVisible(true);
		} catch (Exception e) {
			// throw new ReportDefinitionException("Parsing failed", e);
		}
	}

	public ConstructionTable getTable() {
		return table;
	}

	public void setTable(ConstructionTable table) {
		this.table = table;
	}

	private ConstructionTable table = null;
}
