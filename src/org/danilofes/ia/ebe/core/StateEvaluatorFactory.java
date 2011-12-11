package org.danilofes.ia.ebe.core;

import java.util.List;

import org.danilofes.util.BitString;

public abstract class StateEvaluatorFactory<S extends GameState<?>> {

	public abstract StateEvaluator<S> getEvaluator(BitString parameters);

	public abstract List<Parameter> getParameters();

}
