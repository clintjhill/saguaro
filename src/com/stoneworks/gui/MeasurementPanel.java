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

import com.stoneworks.math.MixedNumber;
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
		initialize();
	}

	/**
	 * @param layout
	 */
	public MeasurementPanel(LayoutManager layout) {
		super(layout);
		initialize();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public MeasurementPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public MeasurementPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	public StandardMeasurement getMeasurement() {
		if (getWholeNumber().getValue() != null
				&& getNumerator().getValue() != null
				&& getDenominator().getValue() != null) {
			int whole = Integer.valueOf(getWholeNumber().getText());
			int numerator = Integer.valueOf(getNumerator().getText());
			int denominator = Integer.valueOf(getDenominator().getText());
			if(whole > 0) {
				if(denominator > numerator) {
					return new StandardMeasurement(0,new MixedNumber(whole,numerator,denominator));
				} else {
					return new StandardMeasurement(0,whole);
				}
			}
			return null;
		} else if (getWholeNumber().getValue() != null) {
			int whole = Integer.valueOf(getWholeNumber().getText());
			return new StandardMeasurement(0,whole);
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
		gridBagConstraints2.insets = textInsets();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.insets = textInsets();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = textInsets();
		this.setSize(120, 40);
		this.setLayout(new GridBagLayout());
		this.add(getWholeNumber(), gridBagConstraints);
		this.add(getNumerator(), gridBagConstraints1);
		this.add(getDenominator(), gridBagConstraints2);
	}

	/**
	 * This method initializes wholeNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getWholeNumber() {
		if (wholeNumber == null) {
			wholeNumber = new JFormattedTextField();
			wholeNumber.setFormatterFactory(getNumberFormat());
			wholeNumber.setColumns(2);
		}
		return wholeNumber;
	}

	/**
	 * This method initializes numerator
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getNumerator() {
		if (numerator == null) {
			numerator = new JFormattedTextField();
			numerator.setFormatterFactory(getNumberFormat());
			numerator.setColumns(2);
		}
		return numerator;
	}

	/**
	 * This method initializes denominator
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getDenominator() {
		if (denominator == null) {
			denominator = new JFormattedTextField();
			denominator.setFormatterFactory(getNumberFormat());
			denominator.setColumns(2);
		}
		return denominator;
	}

	private java.awt.Insets textInsets() {
		return new java.awt.Insets(3, 5, 3, 5);
	}
}
