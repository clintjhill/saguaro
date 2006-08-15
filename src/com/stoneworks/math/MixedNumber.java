/*
 * MixedNumber.java
 * Stoneworks 2006 v2
 * clinthill
 * May 28, 2006
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
 * @author clinthill
 *
 */
public class MixedNumber {
	/**
	 * Default Constructor
	 *
	 */
	public MixedNumber() {
		
	}
	/**
	 * Constructor that sets a MixedNumber through a decimal value
	 * 
	 * @param value
	 *            the double (decimal) value to set
	 */
	public MixedNumber(double value) {
		this.doubleValue = value;
		ComplexNumber complex = new ComplexNumber(value);
		StandardFraction fraction = new StandardFraction(complex.getRemainder());
		this.wholeNumber = complex.getWhole();
		this.numerator = fraction.getNumerator();
		this.denominator = fraction.getDenominator();
	}

	/**
	 * Constructor that sets a MixedNumber through all values
	 * 
	 * @param wholeNumber
	 *            the wholeNumber to set
	 * @param numerator
	 *            the numerator to set
	 * @param denominator
	 *            the denominator to set
	 */
	public MixedNumber(int wholeNumber, int numerator, int denominator) {
		this.wholeNumber = wholeNumber;
		if (denominator > 0) {
			while (numerator > denominator) {
				numerator = numerator - denominator;
				this.wholeNumber++;
			}
			if (numerator == denominator) {
				this.wholeNumber++;
			} else {
				this.numerator = numerator;
				this.denominator = denominator;
			}
		}
	}

	/**
	 * Returns the calculated double value of this instance
	 * 
	 * @return double
	 */
	public double doubleValue() {
		if (this.doubleValue > 0.0D) {
			return this.doubleValue;
		} else if (this.numerator > 0 && this.denominator > 0) {
			return (double) ((this.wholeNumber * this.denominator) + this.numerator)
					/ this.denominator;
		} else {
			return (double) this.wholeNumber;
		}
	}

	/**
	 * Returns whether or not this instance equals a zero value
	 * 
	 * @return boolean value determining if this instance is zero
	 */
	public boolean isZeroValue() {
		// ignore the denominator
		return this.wholeNumber == 0 && this.numerator == 0;
	}

	/**
	 * Used in equality comparisons
	 * 
	 * @param obj
	 *            Object to compare against
	 * @return boolean value determining whether or not all values are equal for this instance
	 *         (wholeNumber,denominator,numerator)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof MixedNumber)) {
			return false;
		}
		MixedNumber other = (MixedNumber) obj;
		// quick check for the double value
		if (this.doubleValue == other.doubleValue)
			return true;
		// otherwise compare the other values
		return other.wholeNumber == this.wholeNumber && other.numerator == this.numerator
				&& other.denominator == this.denominator;
	}

	/**
	 * @return Returns the denominator.
	 */
	public int getDenominator() {
		return this.denominator;
	}

	/**
	 * @return Returns the numerator.
	 */
	public int getNumerator() {
		return this.numerator;
	}

	/**
	 * @return Returns the wholeNumber.
	 */
	public int getWholeNumber() {
		return this.wholeNumber;
	}

	/**
	 * Returns a hash code representation of this instance
	 * 
	 * @return int Returns the hashcode
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashCode = 7;
		hashCode = hashCode * 37 + this.wholeNumber;
		hashCode = hashCode * 37 + this.numerator;
		hashCode = hashCode * 37 + this.denominator;
		return hashCode;
	}

	/**
	 * @param denominator
	 *            The denominator to set.
	 */
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	/**
	 * @param numerator
	 *            The numerator to set.
	 */
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	/**
	 * @param wholeNumber
	 *            The wholeNumber to set.
	 */
	public void setWholeNumber(int wholeNumber) {
		this.wholeNumber = wholeNumber;
	}

	/**
	 * Returns a string representation of this instance
	 * 
	 * @return Returns the string representation
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.numerator > 0 && this.denominator > 0) {
			if (this.wholeNumber > 0) {
				return this.wholeNumber + " " + this.numerator + "/" + this.denominator;
			} else {
				return this.numerator + "/" + this.denominator;
			}
		} else {
			return String.valueOf(this.wholeNumber);
		}
	}

	private int denominator = 0;

	private double doubleValue = 0.0D;

	private int numerator = 0;

	private int wholeNumber = 0;
}
