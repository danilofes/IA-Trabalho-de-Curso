package org.danilofes.ia.ebe.othello.evaluator;


public class ExpertEvaluator extends OthelloStateEvaluator {
	
	public static final int cellValue[][] = {
		{ 99, -8,  8,  6,  6,  8, -8, 99}, 
		{ -8,-24, -4, -3, -3, -4,-24, -8}, 
		{  8, -4,  7,  4,  4,  7, -4,  8}, 
		{  6, -3,  4,  0,  0,  4, -3,  6}, 
		{  6, -3,  4,  0,  0,  4, -3,  6}, 
		{  8, -4,  7,  4,  4,  7, -4,  8}, 
		{ -8,-24, -4, -3, -3, -4,-24, -8}, 
		{ 99, -8,  8,  6,  6,  8, -8, 99}
	};

	public ExpertEvaluator() {
		super(new int[]{1, 10, 10});
	}

}
