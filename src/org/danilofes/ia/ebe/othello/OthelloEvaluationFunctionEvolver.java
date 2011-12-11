package org.danilofes.ia.ebe.othello;

import java.util.Comparator;

import org.danilofes.ia.ebe.core.StateEvaluatorFactory;
import org.danilofes.ia.ebe.core.ga.EvaluationFunctionEvolver;
import org.danilofes.ia.ebe.othello.evaluator.OthelloStateEvaluatorFactory;
import org.danilofes.util.BitString;

public class OthelloEvaluationFunctionEvolver {

	public static void main(String[] args) {
		
		EvaluationFunctionEvolver evolver = new EvaluationFunctionEvolver();
		StateEvaluatorFactory<OthelloState> factory = new OthelloStateEvaluatorFactory();
		Comparator<BitString> comparator = new OthelloEvaluatorComparator(factory);
		
		evolver.run(factory, comparator);
		
	}

}
