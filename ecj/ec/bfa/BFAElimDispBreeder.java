package ec.bfa;

import java.util.Arrays;
import java.util.Comparator;

import com.sun.corba.se.spi.orbutil.fsm.State;

import ec.Breeder;
import ec.EvolutionState;
import ec.Fitness;
import ec.Individual;
import ec.Population;
import ec.Subpopulation;
import ec.select.FirstSelection;
import ec.simple.SimpleStatistics;
import ec.util.MersenneTwisterFast;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;
import ec.vector.FloatVectorSpecies;

public class BFAElimDispBreeder extends Breeder 
{
	public int numOfChemoLoop;
	
	public int countChemoSteps;
	
	public int numOfReproduction;
	
	private int countReproductionSteps;
	
//	public double chemotacticStepSize;
	
	public double divisorStepsize;
	
	public double eliminationProbability;
	
	public static final String P_NUMBEROFCHEMOTACTICSTEPS = "num-of-chemosteps";
	
	public static final String P_NUMBEROFREPRODUCATIONS = "number-of-reproducation";
	
	public static final String P_CHEMOTACTICSTEPSIZE = "chemo-step-size";
	
	public static final String P_DIVISORSTEPSIZE = "step-divisor";
	
	public static final String P_ELIMINATIONPROBABILITY = "elim-prob";
	
	private boolean firstRun = true;

	
	public void setup(EvolutionState state, Parameter base)
	{
		
        numOfChemoLoop = state.parameters.getInt(base.push(P_NUMBEROFCHEMOTACTICSTEPS), null, 1);
        
        numOfReproduction = state.parameters.getInt(base.push(P_NUMBEROFREPRODUCATIONS), null, 0);
        
 //       chemotacticStepSize = state.parameters.getDouble(base.push(P_CHEMOTACTICSTEPSIZE), null, 0);
        
        divisorStepsize = state.parameters.getDouble(base.push(P_DIVISORSTEPSIZE), null, 0.0);
        
        eliminationProbability = state.parameters.getDouble(base.push(P_ELIMINATIONPROBABILITY), null, 0.0);
        
        countChemoSteps = 0;
        
        countReproductionSteps = 0;
	}
	
	public Population breedPopulation(EvolutionState state)
	{
		BFASubpopulation subPop = (BFASubpopulation)state.population.subpops[0];
		
		
		BFAIndividual[] tempClone = new BFAIndividual[subPop.individuals.length];
		
		for(int i = 0; i < tempClone.length; i++)
		{
			tempClone[i] = (BFAIndividual)subPop.individuals[i].clone();
		}
        
        
		/*chemotactic step for each bacteria*/
		for(int i = 0; i < subPop.individuals.length; i++)
		{
			BFAIndividual ind = (BFAIndividual) subPop.individuals[i];
			BFAIndividual prevInd = (BFAIndividual)subPop.previousIndividuals[i];
			FloatVectorSpecies species = (FloatVectorSpecies)ind.species;
						
			/* check if bacteria will swim or tumble */
			if( (ind.countSwimLength < ind.swimLength &&
			     Math.abs(ind.fitness.fitness()) <= Math.abs(prevInd.fitness.fitness()) )||
			   firstRun)
			{
				for(int gidx = 0; gidx < ind.genome.length; gidx++)
				{
					ind.genome[gidx] += (ind.chemotacticStepSize * ind.direction[gidx]);
					
					/*if bacteria swims out of defined area, put the bacteria to some random place */
					if(ind.genome[gidx] < species.minGene(gidx) ||
					   ind.genome[gidx] > species.maxGene(gidx))
					{
						ind.reset(state, 0);
					}
				}
				ind.countSwimLength ++;
			}
			/* tumble bacteria */
			else
			{
				ind.countSwimLength = 0;
				ind.thumble(state);
			}
			
			ind.created = true;
			ind.indStatistics[1] = 1;
			
			ind.indTrace[0][0] = ind.indTrace[2][0];
			ind.indTrace[0][1] = ind.indTrace[2][1];
			
			ind.healt += Math.abs(ind.fitness.fitness());
			
			if(this.countChemoSteps == this.numOfChemoLoop)
			{

				ind.chemotacticStepSize /= this.divisorStepsize;

			}
			
		}
		
		firstRun = false;
		
		subPop.previousIndividuals = tempClone;
		
		if(this.countChemoSteps == this.numOfChemoLoop)
		{
			this.countChemoSteps = 0;
	//		this.chemotacticStepSize /= this.divisorStepsize;
			
			ReproductBateries(subPop);
			this.countReproductionSteps++;

		}
		else
		{
			this.countChemoSteps++;
		}
		
		if(this.countReproductionSteps == this.numOfReproduction)
		{
			EliminateAndDispers(subPop, state);
			this.countReproductionSteps = 0;
		}
		
		return state.population;
	}
	
	private void EliminateAndDispers(BFASubpopulation subPop, EvolutionState state) 
	{
		MersenneTwisterFast rng = state.random[0];
		for(int i = 0; i < subPop.individuals.length; i++)
		{
			BFAIndividual ind = (BFAIndividual)subPop.individuals[i];
			ind.healt = 0;

			if(rng.nextBoolean(this.eliminationProbability))
			{
				ind.reset(state, 0);
				ind.chemotacticStepSize = ind.initialChemoStepSize;
			}
		}
 
	}

	private void ReproductBateries(BFASubpopulation newSubPop)
	{
		Individual ind[] = (Individual[])newSubPop.individuals;
		int halfNumOfInd = ind.length / 2;
		
		Arrays.sort(ind, new HealthComparator());
		
		for(int i = 0; i < halfNumOfInd; i++)
		{
			ind[halfNumOfInd + i] = (BFAIndividual)ind[i].clone();
			
			((BFAIndividual)ind[halfNumOfInd + i]).indTrace[2][1] = halfNumOfInd + i;
			((BFAIndividual)ind[i]).indTrace[2][1] = i;
			
			((BFAIndividual)ind[halfNumOfInd + i]).healt = 0;
			((BFAIndividual)ind[i]).healt = 0;
		}
	}
	
	/* NOTE: this comparator is applicable only when simple-fitness is used */
	class HealthComparator implements Comparator<Individual>
	{
		public int compare(Individual ind1, Individual ind2)
		{
			Double healtInd1 = ((BFAIndividual)ind1).healt;
			Double healtInd2 = ((BFAIndividual)ind2).healt;
			
			return healtInd1.compareTo(healtInd2);
		}
	}

}


