package org.danilofes.ia.ebe.othello.evaluator;

import java.util.List;

import org.danilofes.ia.ebe.core.Parameter;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.core.StateEvaluatorFactory;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.util.BitString;

public class OthelloStateEvaluatorFactory extends StateEvaluatorFactory<OthelloState> {

	@Override
	public List<Parameter> getParameters() {
		return OthelloStateEvaluator.PARAMS;
	}

	@Override
	public StateEvaluator<OthelloState> getEvaluator(BitString paramValues) {
		return new OthelloStateEvaluator(this.decodeParams(paramValues));
	}

}
