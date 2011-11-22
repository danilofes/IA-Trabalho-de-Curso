package org.danilofes.ia.ebe.util;

import junit.framework.Assert;

import org.danilofes.util.BitString;
import org.junit.Test;

public class TestBitString {

	@Test
	public void testSetInt() {
		this.testSetInt(0, 8, 0, "00000000");
		this.testSetInt(0, 8, 45, "00101101");
		this.testSetInt(0, 4, 5, "01010000");
		this.testSetInt(1, 4, 5, "00101000");
		this.testSetInt(0, 8, -3, "11111101");
		this.testSetInt(2, 5, -1, "00111110");
		this.testSetInt(2, 4, 7, "00011100");
		this.testSetInt(2, 4, -8, "00100000");
	}

	@Test
	public void testSetIntOverflow() {
		this.testSetIntOverflow(2, 4, 8, "00011100");
		this.testSetIntOverflow(2, 4, -9, "00100000");
	}
	
	@Test
	public void testGetInt() {
		this.testGetInt(0, 8, 0, "00000000");
		this.testGetInt(0, 8, 45, "00101101");
		this.testGetInt(0, 4, 5, "01010000");
		this.testGetInt(1, 4, 5, "00101000");
		this.testGetInt(0, 8, -3, "11111101");
		this.testGetInt(2, 5, -1, "00111110");
		this.testGetInt(2, 4, 7, "00011100");
		this.testGetInt(2, 4, -8, "00100000");
	}
	
	private void testSetInt(int start, int offset, int value, String expected) {
		BitString bitString = new BitString(expected.length());
		bitString.setInt(start, offset, value);
		Assert.assertEquals(expected, bitString.toString());
	}

	private void testSetIntOverflow(int start, int offset, int value, String expected) {
		try {
			this.testSetInt(start, offset, value, expected);
		} catch (IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}
	
	private void testGetInt(int start, int offset, int expected, String bits) {
		BitString bitString = new BitString(bits.length());
		bitString.setBits(bits);
		Assert.assertEquals(expected, bitString.getInt(start, offset));
	}
}
