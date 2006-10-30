/**
 * 
 */
package com.stoneworks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.stoneworks.math.StandardMeasurement;

/**
 * @author clinthill
 * 
 */
public class MeasurementPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFormattedTextField wholeNumber = null;

	private JFormattedTextField numerator = null;

	private JFormattedTextField denominator = null;

	/**
	 * 
	 */
	public MeasurementPanel() {
		super();
		this.initialize();
	}

	/**
	 * @param layout
	 */
	public MeasurementPanel(LayoutManager layout) {
		super(layout);
		this.initialize();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public MeasurementPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.initialize();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public MeasurementPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.initialize();
	}

	public StandardMeasurement getMeasurement() {
		if (this.getWholeNumber().getValue() != null
				&& this.getNumerator().getValue() != null
				&& this.getDenominator().getValue() != null) {
			int whole = Integer.valueOf(this.getWholeNumber().getText());
			int numerator = Integer.valueOf(this.getNumerator().getText());
			int denominator = Integer.valueOf(this.getDenominator().getText());
			if (whole > 0) {
				if (denominator > numerator && numerator > 0) {
					return new StandardMeasurement(0, whole,numerator, denominator);
				} else {
					return new StandardMeasurement(0, whole, 0, 0);
				}
			}
			return null;
		} else if (this.getWholeNumber().getValue() != null) {
			int whole = Integer.valueOf(this.getWholeNumber().getText());
			return new StandardMeasurement(0, whole, 0, 0);
		} else {
			return null;
		}
	}

	private DefaultFormatterFactory getNumberFormat() {
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter nf = new NumberFormatter(format);
		DefaultFormatterFactory f = new DefaultFormatterFactory(nf);
		return f;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.gridx = 2;
		gridBagConstraints2.insets = this.textInsets();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.insets = this.textInsets();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = this.textInsets();
		this.setSize(120, 40);
		this.setLayout(new GridBagLayout());
		this.add(this.getWholeNumber(), gridBagConstraints);
		this.add(this.getNumerator(), gridBagConstraints1);
		this.add(this.getDenominator(), gridBagConstraints2);
	}

	/**
	 * This method initializes wholeNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getWholeNumber() {
		if (this.wholeNumber == null) {
			this.wholeNumber = new JFormattedTextField();
			this.wholeNumber.setFormatterFactory(this.getNumberFormat());
			this.wholeNumber.setColumns(2);
		}
		return this.wholeNumber;
	}

	/**
	 * This method initializes numerator
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getNumerator() {
		if (this.numerator == null) {
			this.numerator = new JFormattedTextField();
			this.numerator.setFormatterFactory(this.getNumberFormat());
			this.numerator.setColumns(2);
		}
		return this.numerator;
	}

	/**
	 * This method initializes denominator
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getDenominator() {
		if (this.denominator == null) {
			this.denominator = new JFormattedTextField();
			this.denominator.setFormatterFactory(this.getNumberFormat());
			this.denominator.setColumns(2);
		}
		return this.denominator;
	}

	private java.awt.Insets textInsets() {
		return new java.awt.Insets(3, 5, 3, 5);
	}
}
