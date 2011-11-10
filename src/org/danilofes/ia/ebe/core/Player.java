package org.danilofes.ia.ebe.core;

public enum Player {
	PLAYER_1((byte)-1),
	PLAYER_2((byte)1);
	
	private final byte value;
	
	private Player(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public Player getOpponent() {
		return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
	}
}
