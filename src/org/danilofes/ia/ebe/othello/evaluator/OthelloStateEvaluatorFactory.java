package org.danilofes.ia.ebe.othello.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.danilofes.ia.ebe.core.Parameter;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.core.StateEvaluatorFactory;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.util.BitString;

public class OthelloStateEvaluatorFactory extends StateEvaluatorFactory<OthelloState> {

	@Override
	public List<Parameter> getParameters() {
		Parameter parameter = new Parameter(8);
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(parameter);
		return parameters;
	}

	@Override
	public StateEvaluator<OthelloState> getEvaluator(BitString parameters) {
		return new ExpertEvaluator();
	}

}
