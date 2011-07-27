package ec.EEstat.samples.integer;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.EEstat.IntegerVectorIndividualStat;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;

public class MaxOnesInt extends Problem implements SimpleProblemForm
{
	public void evaluate(final EvolutionState state,
	        final Individual ind,
	        final int subpopulation,
	        final int threadnum)
	        {
	        if (ind.evaluated) return;

	        if (!(ind instanceof IntegerVectorIndividualStat))
	            state.output.fatal("Whoa!  It's not a BitVectorIndividual!!!",null);
	        
	        int sum=0;
	        IntegerVectorIndividualStat ind2 = (IntegerVectorIndividualStat)ind;
	        
	        for(int x=0; x<ind2.genome.length; x++)
	            sum += (ind2.genome[x] == 1 ? 1 : 0);
	        
	        if (!(ind2.fitness instanceof SimpleFitness))
	            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
	        ((SimpleFitness)ind2.fitness).setFitness(state,
	            /// ...the fitness...
	            (float)(((double)sum)/ind2.genome.length),
	            ///... is the individual ideal?  Indicate here...
	            /*sum == ind2.genome.length*/false);
	        ind2.evaluated = true;
	        }
}
