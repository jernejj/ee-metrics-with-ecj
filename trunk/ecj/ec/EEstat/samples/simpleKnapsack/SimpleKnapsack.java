package ec.EEstat.samples.simpleKnapsack;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.EEstat.BitVectorIndividualStat;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;

public class SimpleKnapsack extends Problem implements SimpleProblemForm 
{

	private int[] elementsWeigth = {23,31,29,44,53,38,63,85,89,82};
    private int[] elementsValue = {92,57,49,68,60,43,67,84,87,72};
    private int capacity = 165;
    
	@Override
	public void evaluate(EvolutionState state, Individual ind,
			int subpopulation, int threadnum) 
	{
		if (ind.evaluated) return;

        if (!(ind instanceof BitVectorIndividualStat))
            state.output.fatal("Whoa!  It's not a BitVectorIndividualStat!!!",null);
        
        
        BitVectorIndividualStat ind2 = (BitVectorIndividualStat)ind;
        
        int weigth = 0;
        int value = 0;
        
        do
        {  
        	weigth = 0;
            value = 0;
            
	        for (int i = 0; i < ind2.genome.length; i++)
	        {
	            weigth += (ind2.genome[i] ? 1 : 0) * elementsWeigth[i];
	            value += (ind2.genome[i] ? 1 : 0) * elementsValue[i];
	        }
	        if (weigth > capacity)
	        {
	        	int itemToDelete = (int)(Math.random() * (ind2.genome.length));
	        	ind2.genome[itemToDelete] = false;
	        	ind2.indStatistics[2]++;
	        }
        }while(weigth > capacity);
        
        ((SimpleFitness)ind2.fitness).setFitness(state, value, value == Integer.MAX_VALUE);
        
        ind2.evaluated = true;

	}

}
