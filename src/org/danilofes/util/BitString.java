package org.danilofes.util;

public class BitString {

	private final boolean[] bits;

	public BitString(int size) {
		this.bits = new boolean[size];
		for (int i = 0; i < size; i ++) {
			this.bits[i] = false;
		}
	}

	public void setBits(String bits) {
		assert bits.length() <= this.bits.length;
		for (int i = 0; i < bits.length(); i++) {
			this.bits[i] = (bits.charAt(i) == '1') ? true : false;
		}
	}

	public void set(int i, boolean v) {
		this.bits[i] = v;
	}
	
	public boolean get(int i) {
		return this.bits[i];
	}
	
	public void setInt(int start, int length, int value) {
		int maxValue = 1 << (length - 1);
		if (value >= maxValue) {
			throw new IllegalArgumentException("value must be smaller then " + maxValue);
		}
		if (value < -maxValue) {
			throw new IllegalArgumentException("value must be greater then " + -maxValue);
		}

		for (int i = 0; i < length; i++) {
			int place = start + length - i - 1;
			boolean bit = ((value >> i) & 0x1) > 0;
			this.set(place, bit);
		}
	}
	
	public int getInt(int start, int length) {
		int result = 0;
		for (int i = 0; i < length; i++) {
			int place = start + i;
			result = result << 1;
			if (this.get(place)) {
				result = result | 0x1;
			}
		}
		int bitsToFill = 32 - length;
		return (result << bitsToFill) >> bitsToFill;
	}
	
	@Override
	public String toString() {
		int length = this.bits.length;
		char[] stringData = new char[length];
		for (int i = 0; i < length; i++) {
			stringData[i] = this.bits[i] ? '1' : '0';
		}
		return new String(stringData);
	}
}
