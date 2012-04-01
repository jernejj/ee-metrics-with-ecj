package ec.bfa;

import ec.Breeder;
import ec.EvolutionState;
import ec.Population;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;

public class BFABreeder extends Breeder 
{
	
	public Population previousPopulation = null;

	
	public void setup(EvolutionState state, Parameter base)
	{
		
	}
	
	public Population breedPopulation(EvolutionState state)
	{
		Population newPopulation = (Population)state.population.emptyClone();
		
		BFASubpopulation pop = (BFASubpopulation) newPopulation.subpops[0];
		
		/*chemotactic step for each bacteria*/
		for(int i = 0; i < pop.individuals.length; i++)
		{
			BFAIndividual ind = (BFAIndividual) state.population.subpops[0].individuals[i];
			BFAIndividual prevInd = (BFAIndividual) previousPopulation.subpops[0].individuals[i];
			BFAIndividual newInd = (BFAIndividual) pop.individuals[i];
			
			newInd = (BFAIndividual)ind.clone();
			/* check if bacteria will swim or tumble */
			if(ind.countSwimLength < ind.swimLength &&
			   ind.fitness.betterThan(prevInd.fitness))
			{
				
			}
			/* tumble bacteria */
			else
			{
				newInd.thumble();
			}
		}
		
		previousPopulation = state.population;
		
		return newPopulation;
	}

}
