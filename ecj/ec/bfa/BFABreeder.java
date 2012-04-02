package ec.bfa;

import java.util.Arrays;
import java.util.Comparator;

import ec.Breeder;
import ec.EvolutionState;
import ec.Fitness;
import ec.Individual;
import ec.Population;
import ec.Subpopulation;
import ec.select.FirstSelection;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;

public class BFABreeder extends Breeder 
{
	public int numOfChemoLoop;
	
	public int countChemoSteps;
	
	public int numOfReproduction;
	
	public double chemotacticStepSize;
	
	public double divisorStepsize;
	
	public static final String P_NUMBEROFCHEMOTACTICSTEPS = "num-of-chemosteps";
	
	public static final String P_NUMBEROFREPRODUCATIONS = "number-of-reproducation";
	
	public static final String P_CHEMOTACTICSTEPSIZE = "chemo-step-size";
	
	public static final String P_DIVISORSTEPSIZE = "step-divisor";
	
	private boolean firstRun = true;

	
	public void setup(EvolutionState state, Parameter base)
	{
		
        numOfChemoLoop = state.parameters.getInt(base.push(P_NUMBEROFCHEMOTACTICSTEPS), null, 1);
        
        numOfReproduction = state.parameters.getInt(base.push(P_NUMBEROFREPRODUCATIONS), null, 0);
        
        chemotacticStepSize = state.parameters.getDouble(base.push(P_CHEMOTACTICSTEPSIZE), null, 0);
        
        divisorStepsize = state.parameters.getDouble(base.push(P_DIVISORSTEPSIZE), null, 0.0);
        
        countChemoSteps = 0;
	}
	
	public Population breedPopulation(EvolutionState state)
	{
		BFASubpopulation subPop = (BFASubpopulation)state.population.subpops[0];
		
		
		BFAIndividual[] tempClone = new BFAIndividual[subPop.individuals.length];
		
		for(int i = 0; i < tempClone.length; i++)
		{
			tempClone[i] = (BFAIndividual)subPop.individuals[i].clone();
		}
        //System.arraycopy(subPop.individuals, 0, tempClone, 0, subPop.individuals.length);
        
		/*chemotactic step for each bacteria*/
		for(int i = 0; i < subPop.individuals.length; i++)
		{
			BFAIndividual ind = (BFAIndividual) subPop.individuals[i];
			BFAIndividual prevInd = (BFAIndividual)subPop.previousIndividuals[i];
						
			/* check if bacteria will swim or tumble */
			if(ind.countSwimLength < ind.swimLength &&
			   ind.fitness.betterThan(prevInd.fitness) ||
			   firstRun)
			{
				for(int gidx = 0; gidx < ind.genome.length; gidx++)
				{
					ind.genome[gidx] += this.chemotacticStepSize * ind.direction[gidx];
				}
			}
			/* tumble bacteria */
			else
			{
				ind.thumble();
			}
			
			ind.created = true;
			ind.indStatistics[1] = 1;
			
			ind.indTrace[0][0] = ind.indTrace[2][0];
			ind.indTrace[0][1] = ind.indTrace[2][1];
			
		}
		
		firstRun = false;
		
		subPop.previousIndividuals = tempClone;
		
		if(this.countChemoSteps == this.numOfChemoLoop)
		{
			this.countChemoSteps = 0;
			this.countChemoSteps /= this.divisorStepsize;
			
			ReproductBateries(subPop);

		}
		else
		{
			this.countChemoSteps++;
		}
		
		return state.population;
	}
	
	private void ReproductBateries(BFASubpopulation newSubPop)
	{
		Individual ind[] = (Individual[])newSubPop.individuals;
		int halfNumOfInd = ind.length / 2;
		
		Arrays.sort(ind, new FitnessComparator());
		
		for(int i = 0; i < halfNumOfInd; i++)
		{
			ind[halfNumOfInd + i] = (BFAIndividual)ind[i].clone();
			
			((BFAIndividual)ind[halfNumOfInd + i]).indTrace[2][1] = halfNumOfInd + i;
			((BFAIndividual)ind[i]).indTrace[2][1] = i;
		}
	}

}

class FitnessComparator implements Comparator<Individual>
{
	public int compare(Individual ind1, Individual ind2)
	{
		Fitness fitInd1 = ind1.fitness;
		Fitness fitInd2 = ind2.fitness;
		
		if(fitInd1.betterThan(fitInd2))
		{
			return -1;
		}

		return 1;
	}
}
