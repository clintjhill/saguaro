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
 * A class that represents a standard measurement. It includes feet and inches with a standard unit
 * fraction for the inches.
 * 
 * @author clinthill
 * 
 */
public class StandardMeasurement {

	/**
	 * Returns a <code>StandardMeasurement</code> that only contains a measurement value for
	 * inches. This method will convert the inches value passed in from a screen value. Divides
	 * inches by 6 before creating the StandardMeasurement
	 * 
	 * @param inches
	 * @return StandardMeasurement
	 */
	public static StandardMeasurement createForInches(double inches) {
		return new StandardMeasurement(0, new MixedNumber(inches / 6));
	}

	/**
	 * Returns a <code>StandardMeasurement</code> that contains a measurement which includes a
	 * value for feet. This method will convert the inches value passed in from a screen value.
	 * Divides inches by 6 before creating the StandardMeasurement
	 * 
	 * @param feet
	 * @return StandardMeasurement
	 */
	public static StandardMeasurement createForFeet(double feet) {
		StandardMeasurement standard = new StandardMeasurement(feet/ 6);
		return standard;
	}

	/**
	 * Default Constructor
	 */
	public StandardMeasurement() {
	}

	/**
	 * Constructor that initializes feet and inches. Inches is set as a whole number.
	 * 
	 * @param feet
	 * @param inches
	 */
	public StandardMeasurement(int feet, int inches) {
		this.feet = feet;
		this.inches = new MixedNumber(inches, 0, 0);
	}

	/**
	 * Constructor that initializes feet and inches
	 * 
	 * @param feet
	 *            int value of feet for measurement
	 * @param inches
	 *            {@link MixedNumber} value of inches
	 */
	public StandardMeasurement(int feet, MixedNumber inches) {
		this.feet = feet;
		this.inches = inches;
	}

	/**
	 * Constructor that will calculate feet and inches from a double value.
	 * 
	 * @param measure
	 */
	private StandardMeasurement(double measure) {
		ComplexNumber complexNumber = new ComplexNumber(measure);
		// if the whole number is greater than twelve we need to handle the
		// feet measurement of this instance
		if (complexNumber.getWhole() > 12) {
			this.feet = complexNumber.getWhole() / 12;
			int inchRemainder = complexNumber.getWhole() - (this.feet * 12);
			double inchesResult = inchRemainder + complexNumber.getRemainder();
			this.inches = new MixedNumber(inchesResult);
		} else { // the whole number is less than twelve so the value is all inches
			this.inches = new MixedNumber(measure);
		}
	}

	/**
	 * Returns the feet for this measurement
	 * 
	 * @return the feet
	 */
	public int getFeet() {
		return feet;
	}

	/**
	 * Returns the inches value for this measurement
	 * 
	 * @return the inches
	 */
	public MixedNumber getInches() {
		return inches;
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
	 * Sets the inches value for this measurement
	 * 
	 * @param inches
	 *            the inches to set
	 */
	public void setInches(MixedNumber inches) {
		this.inches = inches;
	}

	/**
	 * Returns the double value of this measurement. The value is based on 12 inches in a foot and
	 * the inches in the form of a double value.
	 * 
	 * feet * 12 + inches.doubleValue;
	 * 
	 * @return double
	 */
	public double doubleValue() {
		double result = 0.0D;
		result += this.feet * 12;
		result += this.inches.doubleValue();
		return result;
	}

	/**
	 * Returns the double value of this measurement for the screen.
	 * 
	 * @return double
	 */
	public double screenValue() {
		return this.doubleValue() * 6;
	}

	/**
	 * Returns the double value of this measurement for printing
	 * 
	 * @return double
	 */
	public double printValue() {
		return this.doubleValue() * 72;
	}

	private int feet = 0;

	private MixedNumber inches = null;

	/**
	 * Returns this instance in a readable format
	 * @return String
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String description = "";
		if(this.feet <= 0) {
			description = this.inches.toString() + "''";
		} else {
			description = String.valueOf(this.feet) + "' " + this.inches.toString() + "''";
		}
		return description;
	}
}
