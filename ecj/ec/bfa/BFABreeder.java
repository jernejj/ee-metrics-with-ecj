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
		BFASubpopulation newSubPop = (BFASubpopulation) newPopulation.subpops[0];
		BFASubpopulation curSubPop = (BFASubpopulation)state.population.subpops[0];
		
		/*chemotactic step for each bacteria*/
		for(int i = 0; i < newSubPop.individuals.length; i++)
		{
			BFAIndividual ind = (BFAIndividual) state.population.subpops[0].individuals[i];
			BFAIndividual prevInd = (BFAIndividual) previousPopulation.subpops[0].individuals[i];
			BFAIndividual newInd = (BFAIndividual) newSubPop.individuals[i];
			
			newInd = (BFAIndividual)ind.clone();
			
			/* check if bacteria will swim or tumble */
			if(ind.countSwimLength < ind.swimLength &&
			   ind.fitness.betterThan(prevInd.fitness))
			{
				double newGenome[] = newInd.genome;
				
				for(int gidx = 0; gidx < newGenome.length; gidx++)
				{
					newGenome[gidx] += curSubPop.chemotacticStepSize * ind.direction[gidx];
				}
			}
			/* tumble bacteria */
			else
			{
				newInd.thumble();
			}
			
		}
		
		newSubPop.countChemoSteps = curSubPop.countChemoSteps + 1;
		previousPopulation = state.population;
		
		return newPopulation;
	}

}
