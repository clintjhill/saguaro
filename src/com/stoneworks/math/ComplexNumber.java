/*
 * ComplexNumber.java
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

import java.util.Comparator;

/**
 * A math class that will take a double value (i.e. 1.2548) and separate the
 * values into a whole number value and a remainder value. The remainder value
 * stays in a double type to maintain type safety.
 * 
 * @author clinthill
 * 
 */
public class ComplexNumber implements Comparable {

	/**
	 * Receives a double value and converts it into a whole number with a
	 * remainder
	 * 
	 * @param d
	 */
	public ComplexNumber(double d) {
		this.original = d;
		String originalString = String.valueOf(this.original);
		String beforeDecimal = originalString.substring(0, originalString
				.indexOf("."));
		String afterDecimal = originalString.substring(originalString
				.indexOf("."));
		try {
			this.whole = Integer.valueOf(beforeDecimal);
		} catch (NumberFormatException ne) {
			beforeDecimal = beforeDecimal.substring(0, 5);
			this.whole = Integer.valueOf(beforeDecimal);
		}
		try {
			this.remainder = Double.valueOf(afterDecimal);
		} catch (NumberFormatException ne) {
			afterDecimal = afterDecimal.substring(0, 5);
			this.remainder = Double.valueOf(afterDecimal);
		}
	}

	/**
	 * 
	 * @param o
	 * @return int
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Object o) {
		ComplexNumber cn = (ComplexNumber) o;
		// check whole numbers first
		if (cn.whole < this.whole) {
			return 1;
		}
		if (cn.whole > this.whole) {
			return -1;
		}
		// if whole numbers are equal then go to remainders
		if (cn.remainder > this.remainder) {
			return -1;
		}
		if (cn.remainder < this.remainder) {
			return 1;
		}
		// all else equal then return equal (0)
		return 0;
	}

	/**
	 * @param obj
	 * @return boolean
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ComplexNumber)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		ComplexNumber other = (ComplexNumber) obj;
		return other.original == this.original;
	}

	/**
	 * @return Returns the original.
	 */
	public double getOriginal() {
		return this.original;
	}

	/**
	 * @return Returns the remainder.
	 */
	public double getRemainder() {
		return this.remainder;
	}

	/**
	 * @return Returns the whole.
	 */
	public int getWhole() {
		return this.whole;
	}

	/**
	 * 
	 * @return int
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.hashCode == 0) {
			int result = 17;
			result = 37 * result + this.whole;
			result = 37 * result + (int) this.remainder;
			this.hashCode = result;
		}
		return this.hashCode;
	}

	/**
	 * Returns this instance in string format: "Whole: 0, Remainder: 0,
	 * Original: 0"
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

		return "Whole: " + this.whole + ", Remainder: " + this.remainder + ", Original: "
				+ this.original;
	}

	private volatile int hashCode = 0;

	private double original = 0.0D;

	private double remainder = 0;

	private int whole = 0;

	/**
	 * Comparator class that will assist with comparing just the remainder
	 * values of ComplexNumbers
	 * 
	 * @author clinthill
	 * 
	 */
	public static class RemainderComparator implements Comparator {

		/**
		 * Default Constructor
		 */
		public RemainderComparator() {
		}

		/**
		 * Compares the value of the incoming objects remainder against this
		 * instances remainder.
		 * 
		 * @param o1
		 *            first ComplexNumber to compare
		 * @param o2
		 *            second ComplexNumber to compare
		 * @return int the compared value
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			ComplexNumber a = (ComplexNumber) o1;
			ComplexNumber b = (ComplexNumber) o2;
			if (a.getRemainder() > b.getRemainder()) {
				return 1;
			}
			if (a.getRemainder() < b.getRemainder()) {
				return -1;
			}
			return 0;
		}
	}
}
