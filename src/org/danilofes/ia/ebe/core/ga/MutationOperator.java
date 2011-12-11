package org.danilofes.ia.ebe.core.ga;

import org.danilofes.util.BitString;

public interface MutationOperator {

	void mutate(BitString bitString);
	
}
