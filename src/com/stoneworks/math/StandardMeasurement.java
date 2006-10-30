/*
 * StandardMeasurement.java
 * Stoneworks 2006 v2
 * clinthill
 * May 29, 2006
 * Copyright (c) 2006, H3O Software - clint hill (clint.hill@h3osoftware.com)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, 
 *		 this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, 
 *		 this list of conditions and the following disclaimer in the documentation 
 * 		 and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors 
 * 		 may be used to endorse or promote products derived from this software 
 *		 without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 *
 */
package com.stoneworks.math;

/**
 * A class that represents a standard measurement. It includes feet and inches
 * with a standard unit fraction for the inches.
 * 
 * @author clinthill
 * 
 */
public class StandardMeasurement {

	/**
	 * Returns a <code>StandardMeasurement</code> that contains a measurement
	 * which includes a value for feet. This method will convert the inches
	 * value passed in from a screen value. Divides inches by SCREEN before creating
	 * the StandardMeasurement
	 * 
	 * @param feet
	 * @return StandardMeasurement
	 */
	public static StandardMeasurement feetFromScreen(double feet) {
		// be sure to divide by six to convert from screen
		ComplexNumber feetComplex = new ComplexNumber(feet/SCREEN);
		int feetValue = feetComplex.getWhole();
		double inches = feetComplex.getRemainder() * 12;
		ComplexNumber inchesComplex = new ComplexNumber(inches);
		int wholeValue = inchesComplex.getWhole();
		int numeratorValue = (int)java.lang.Math.round(inchesComplex.getRemainder()*32);
		return new StandardMeasurement(feetValue,wholeValue,numeratorValue,32);
	}

	/**
	 * Returns a <code>StandardMeasurement</code> that only contains a
	 * measurement value for inches. This method will convert the inches value
	 * passed in from a screen value. Divides inches by SCREEN before creating the
	 * StandardMeasurement
	 * 
	 * @param inches
	 * @return StandardMeasurement
	 */
	public static StandardMeasurement inchesFromScreen(double inches) {
		// be sure to divide by six to convert from screen
		double inchesValue = (inches/SCREEN) * 12;	
		ComplexNumber inchesComplex = new ComplexNumber(inchesValue);
		int wholeValue = inchesComplex.getWhole();
		int numeratorValue = (int)java.lang.Math.round(inchesComplex.getRemainder()*32);
		return new StandardMeasurement(0,wholeValue,numeratorValue,32);
	}

	private int denominator = 0;

	private int feet = 0;

	private int numerator = 0;

	private int whole = 0;
	
	public static int SCREEN = 72;
	
	public static int PRINT = 72;

	/**
	 * 
	 * @param feet
	 * @param whole
	 * @param numerator
	 * @param denominator
	 */
	public StandardMeasurement(int feet, int whole, int numerator, int denominator) {
		this.feet = feet;
		this.whole = whole;
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	/**
	 * Returns the double value of this measurement. The value is based on 12
	 * inches in a foot and the inches in the form of a double value.
	 * 
	 * feet * 12 + inches.doubleValue;
	 * 
	 * @return double
	 */
	public double doubleValue() {
		double result = 0.0D;
		double fraction = 0.0D;
		result += this.feet;
		if(this.whole > 0) {
			if(this.denominator > 0 && this.numerator > 0 && this.numerator < this.denominator ) {
				fraction = (((double)this.whole*(double)this.denominator)+(double)this.numerator)/this.denominator;
				result += fraction/12;
			} else {
				result += (double)this.whole/12;
			}
		} else {
			if(this.denominator > 0 && this.numerator > 0 && this.numerator < this.denominator) {
				fraction = (this.numerator/this.denominator)/12;
				result += fraction;
			}
		}
		return result;
	}

	/**
	 * @return the denominator
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * Returns the feet for this measurement
	 * 
	 * @return the feet
	 */
	public int getFeet() {
		return this.feet;
	}

	/**
	 * @return the numerator
	 */
	public int getNumerator() {
		return numerator;
	}

	/**
	 * @return the whole
	 */
	public int getWhole() {
		return whole;
	}

	/**
	 * Returns the double value of this measurement for printing
	 * 
	 * @return double
	 */
	public double printValue() {
		return this.doubleValue() * PRINT;
	}

	/**
	 * Returns the double value of this measurement for the screen.
	 * 
	 * @return double
	 */
	public double screenValue() {
		return this.doubleValue() * SCREEN;
	}

	/**
	 * @param denominator
	 *            the denominator to set
	 */
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	/**
	 * Sets the feet value for this measurement
	 * 
	 * @param feet
	 *            the feet to set
	 */
	public void setFeet(int feet) {
		this.feet = feet;
	}

	/**
	 * @param numerator
	 *            the numerator to set
	 */
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	/**
	 * @param whole
	 *            the whole to set
	 */
	public void setWhole(int whole) {
		this.whole = whole;
	}

	/**
	 * Returns this instance in a readable format
	 * 
	 * @return String
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String description = "";
		if(this.feet > 0) description += this.feet+"'";
		if(this.whole > 0) {
			description += " "+this.whole;
			if(this.denominator > 0 && this.numerator < this.denominator && this.numerator > 0) {
				description += " "+this.numerator+"/"+this.denominator;
			}
			description += "\"";
		} else {
			if(this.denominator > 0 && this.numerator < this.denominator && this.numerator > 0) {
				description += " "+this.numerator+"/"+this.denominator;
			}
			description += "\"";
		}
		return description;
	}
}
