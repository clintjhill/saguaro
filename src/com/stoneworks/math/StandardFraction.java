/*
 * StandardFraction.java
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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.TreeMap;

/**
 * @author clinthill
 *
 */
public class StandardFraction {

	/**
	 * Receives an double value that represents the remainder of a complex number.
	 * 
	 * @param original
	 *            original double value to process
	 * 
	 */
	public StandardFraction(double original) {
		if (original < 1.0D) {
			originalValue = original;
		} else {
			throw new java.lang.NumberFormatException(
					"Value must be less than 1.0 in order to perform fraction.");
		}
	}


	@SuppressWarnings("unchecked")
	private void findSmallestRemainder() {
		if (!resultsSet)
			setResults();
		ComplexNumber.RemainderComparator remainderComparator = new ComplexNumber.RemainderComparator();
		TreeMap<ComplexNumber, Integer> remainders = new TreeMap<ComplexNumber, Integer>(
				remainderComparator);
		if (getWholeNumberOrZero(this.halfResult) == 0)
			remainders.put(new ComplexNumber(this.halfResult), 2);
		if (getWholeNumberOrZero(this.quarterResult) == 0)
			remainders.put(new ComplexNumber(this.quarterResult), 4);
		if (getWholeNumberOrZero(this.eighthResult) == 0)
			remainders.put(new ComplexNumber(this.eighthResult), 8);
		if (getWholeNumberOrZero(this.sixteenthResult) == 0)
			remainders.put(new ComplexNumber(this.sixteenthResult), 16);
		if (getWholeNumberOrZero(this.thirtySecondResult) == 0)
			remainders.put(new ComplexNumber(this.thirtySecondResult), 32);

		this.numerator = remainders.firstKey().getWhole();
		this.denominator = (int) remainders.get(remainders.firstKey());
	}

	private boolean foundSmallestWholeNumber() {
		if (!resultsSet)
			setResults();
		// take all of the results from the division and run them through the tree map
		// to assist with finding the least whole number
		TreeMap<Integer, Integer> wholeNumber = new TreeMap<Integer, Integer>();
		wholeNumber.put(getWholeNumberOrZero(this.halfResult), 2);
		wholeNumber.put(getWholeNumberOrZero(this.quarterResult), 4);
		wholeNumber.put(getWholeNumberOrZero(this.eighthResult), 8);
		wholeNumber.put(getWholeNumberOrZero(this.sixteenthResult), 16);
		wholeNumber.put(getWholeNumberOrZero(this.thirtySecondResult), 32);
		// if the wholeNumber tree has a count of more than one key then it has a whole number
		// to use for us. Otherwise it only found 0's. grab the first key and the first value
		// (numerator,denominator) after removing the 0 key
		if (wholeNumber.size() > 1) {
			wholeNumber.remove(0);
			this.numerator = Integer.valueOf(wholeNumber.firstKey().toString());
			this.denominator = Integer.valueOf(wholeNumber.get(wholeNumber.firstKey()).toString());
			return true;
		}
		return false;
	}

	/**
	 * @return Returns the denominator.
	 */
	public int getDenominator() {
		if (!operationPerformed)
			performOperation();
		return denominator;
	}

	/**
	 * @return Returns the numerator.
	 */
	public int getNumerator() {
		if (!operationPerformed)
			performOperation();
		return numerator;
	}

	/**
	 * @return Returns the originalValue.
	 */
	public double getOriginalValue() {
		return originalValue;
	}

	/**
	 * Rounds the original double value passed to this class into at most a 4 digit number
	 * 
	 * @return double
	 */
	private double getRoundedOriginal() {
		String origStringValue = String.valueOf(originalValue);
		if (origStringValue.length() > 6) {
			MathContext mc = new MathContext(6);
			BigDecimal bigDec = new BigDecimal(originalValue, mc);
			bigDec = bigDec.round(mc);
			return bigDec.doubleValue();
		}
		return originalValue;
	}

	/**
	 * 
	 * @param result
	 * @return int representing the whole number of the double passed in
	 */
	private int getWholeNumberOrZero(double result) {
		int whole = 0;
		String wholeString = String.valueOf(result);
		// if there is a point in the double let's parse it for the whole number
		if (wholeString.indexOf(".") > -1) {
			// if the digits behind the decimal are not zero then it isn't a whole number and return
			// zero
			if (!wholeString.substring(wholeString.indexOf(".") + 1).equals("0")) {
				whole = 0;
			} else {
				// this is a whole number so parse off the decimal and return it
				whole = Integer.valueOf(wholeString.substring(0, wholeString.indexOf(".")));
			}
		} else {
			// if it didn't have a decimal then return it whole
			whole = Integer.valueOf(wholeString);
		}
		return whole;
	}

	private void performOperation() {
		// if the least whole number doesn't exist and we have all complex doubles then
		// let's sort those for least remainder
		if (!foundSmallestWholeNumber()) {
			findSmallestRemainder();
		}
		operationPerformed = true;
	}

	/**
	 * @param originalValue
	 *            The originalValue to set.
	 */
	public synchronized void setOriginalValue(double originalValue) {
		this.originalValue = originalValue;
		this.operationPerformed = false;
		this.resultsSet = false;
	}

	private void setResults() {
		// use the rounded in double form to keep types alike on division
		double roundedOriginal = getRoundedOriginal();
		// perform division on all sizes using scale
		this.halfResult = roundedOriginal / half;
		this.quarterResult = roundedOriginal / quarter;
		this.eighthResult = roundedOriginal / eighth;
		this.sixteenthResult = roundedOriginal / sixteenth;
		this.thirtySecondResult = roundedOriginal / thirtySecond;
		resultsSet = true;
	}

	private int denominator = 0;

	private final double eighth = 0.125D;

	private double eighthResult = 0.0D;

	private final double half = 0.5D;

	private double halfResult = 0.0D;

	private int numerator = 0;

	private boolean operationPerformed = false;

	private double originalValue = 0;

	private final double quarter = 0.25D;

	private double quarterResult = 0.0D;

	private boolean resultsSet = false;

	private final double sixteenth = 0.0625D;

	private double sixteenthResult = 0.0D;

	private final double thirtySecond = 0.03125D;

	private double thirtySecondResult = 0.0D;
}
