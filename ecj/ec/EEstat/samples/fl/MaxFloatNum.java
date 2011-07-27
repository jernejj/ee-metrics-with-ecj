package ec.EEstat.samples.fl;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.EEstat.FloatVectorIndividualStat;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;

public class MaxFloatNum extends Problem implements SimpleProblemForm
{
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int thread) 
	{
		if (ind.evaluated)
			return;
		
		if (!(ind instanceof FloatVectorIndividualStat))
			state.output.fatal("Whoa! It's not an FloatVectorIndividualStat!!!");
		
		float[] genome = ((FloatVectorIndividualStat) ind).genome;
		float product = 1;
		
		for (int x = 0; x < genome.length; x++)
			product = product * genome[x];
		
		((SimpleFitness) ind.fitness).setFitness(state, product,
				product == Double.POSITIVE_INFINITY);
		
		ind.evaluated = true;
	}
}
