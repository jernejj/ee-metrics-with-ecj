/**
 * 
 */
package ec.EEstat;


import java.util.Arrays;

import sun.reflect.generics.tree.IntSignature;

import ec.EvolutionState;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;
import ec.vector.IntegerVectorSpecies;
import ec.vector.VectorDefaults;
import ec.vector.VectorIndividual;

/**
 * @author Jernej
 * 
 */


public class IntegerVectorIndividualStat extends IntegerVectorIndividual implements EEStatIndividualI
{

	static int indID;
	public int indTrace[][];
	public int indStatistics[];
	
	public static final String P_INTEGERVECTORINDIVIDUAL = "int-vect-ind-stat";
    
    public Parameter defaultBase()
    {
        return VectorDefaults.base().push(P_INTEGERVECTORINDIVIDUAL);
    }
	
	public Object clone() 
	{
		IntegerVectorIndividualStat myobj = (IntegerVectorIndividualStat) (super.clone());

		// must clone the genome
		myobj.indTrace =  (int[][])(indTrace.clone());
		myobj.indStatistics = (int[])(indStatistics.clone());
		
		
		return myobj;
	}

	public void setup(final EvolutionState state, final Parameter base) 
	{
		super.setup(state, base);

		indTrace = new int[3][2];
		indStatistics = new int[4];

	}
	
	public int dimmensionChanged(VectorIndividual ind)
	{
		int dimmChanged = 0;
		
		for (int i = 0; i < this.genome.length; i++)
		{
			if(this.genome[i] != ((IntegerVectorIndividualStat)ind).genome[i])
				dimmChanged++;
		}
		
		return dimmChanged;
	}
	
	public int similarTo(VectorIndividual ind1, VectorIndividual ind2)
	{
		int dimmDiffFirst = dimmensionChanged(ind1);
		int dimmDiffSec = dimmensionChanged(ind2);
		
		
		if(dimmDiffFirst == dimmDiffSec)
		{
			double distanceToInd1 = this.distanceTo(ind1);
			double distanceToInd2 = this.distanceTo(ind2);
			
			if(distanceToInd1 < distanceToInd2)
				return(1); //this individual is similar to first
			else
				return(2); //this individual is similar to second
		}
		else if(dimmDiffFirst < dimmDiffSec)
			return(1); //this individual is similar to first
		else
			return(2); //this individual is similar to second
		
	}
	
	public void defaultCrossover(EvolutionState state, int thread, VectorIndividual ind)
	{
		IntegerVectorIndividualStat ind1 = (IntegerVectorIndividualStat) this.clone();
		IntegerVectorIndividualStat ind2 = (IntegerVectorIndividualStat) ind.clone();
		int similar = 0;
		
		
		super.defaultCrossover(state, thread, ind);
		
		similar = this.similarTo(ind1, ind2);
		
		
		if(similar == 1)
		{
			this.indTrace[0][0] = ind1.indTrace[2][0];
			this.indTrace[0][1] = ind1.indTrace[2][1];
			
			this.indTrace[1][0] = ind2.indTrace[2][0];
			this.indTrace[1][1] = ind2.indTrace[2][1];
			
			this.indStatistics[0] = this.dimmensionChanged(ind1);
			
		}
		else
		{
			this.indTrace[0][0] = ind2.indTrace[2][0];
			this.indTrace[0][1] = ind2.indTrace[2][1];
			
			this.indTrace[1][0] = ind1.indTrace[2][0];
			this.indTrace[1][1] = ind1.indTrace[2][1];
			
			this.indStatistics[0] = this.dimmensionChanged(ind2);
		}
		
		
		similar = ((IntegerVectorIndividualStat)ind).similarTo(ind1, ind2);
		
		
		if(similar == 1)
		{
			((IntegerVectorIndividualStat)ind).indTrace[0] = ind1.indTrace[2].clone();
			
			((IntegerVectorIndividualStat)ind).indTrace[1] = ind2.indTrace[2].clone();
			
			((IntegerVectorIndividualStat)ind).indStatistics[0] = ((IntegerVectorIndividualStat)ind).dimmensionChanged(ind1);
			
		}
		else
		{
			((IntegerVectorIndividualStat)ind).indTrace[0] = ind2.indTrace[2].clone();
			
			((IntegerVectorIndividualStat)ind).indTrace[1] = ind1.indTrace[2].clone();
			
			((IntegerVectorIndividualStat)ind).indStatistics[0] = ((IntegerVectorIndividualStat)ind).dimmensionChanged(ind2);
		}

	}
	
	public void defaultMutate(EvolutionState state, int thread)
    {

		int dimmChange = 0;
		
		for (int i = 0; i < state.population.subpops[0].individuals.length; i++) 
		{
			if (Arrays.equals(((IntegerVectorIndividualStat)state.population.subpops[0].individuals[i]).genome, genome))
			{
				indTrace[2][0] = i;
				indTrace[2][1] = state.generation;
			}
		}
		
		IntegerVectorIndividualStat tmpInd = (IntegerVectorIndividualStat)this.clone();
		
		super.defaultMutate(state, thread);
		
		dimmChange = this.dimmensionChanged(tmpInd);
		
		if(dimmChange > 0)
		{
			
			//indTrace[0] = indTrace[2].clone();
			indStatistics[1] = dimmChange;
			
		}
		
    }
    

	public void reset(EvolutionState state, int thread)
    {
	    IntegerVectorSpecies s = (IntegerVectorSpecies) species;
	    for(int x=0;x<genome.length;x++)
	        genome[x] = randomValueFromClosedInterval((int)s.minGene(x), (int)s.maxGene(x), state.random[thread]);
	    
	    indTrace = new int[3][2];
		indStatistics = new int[4];
		
	    Arrays.fill(indStatistics, 0);
	    Arrays.fill(indTrace[0], -1);
	    Arrays.fill(indTrace[1], -1);
	    
    }



	public void printIndividualStats(EvolutionState state, int indSeq, int log)
	{
	
		for (int i = 0; i < state.population.subpops[0].individuals.length; i++) {
			if (Arrays
					.equals(((IntegerVectorIndividualStat) state.population.subpops[0].individuals[i]).genome,
							this.genome)) {
				indTrace[2][0] = i;
				indTrace[2][1] = state.generation;
			}
		}
		
		state.output.print("p1(" + indTrace[0][0] + "," + indTrace[0][1] + ") ", log);
		state.output.print("p2(" + indTrace[1][0] + "," + indTrace[1][1] + ") ", log);
		state.output.print("id(" + indSeq + "," + indTrace[2][1] + ") ", log);
		state.output.print("in(" + genotypeToStringForHumans() + ") ", log);
		state.output.print("c" + indStatistics[0] + " ", log);
		state.output.print("m" + indStatistics[1] + " ", log);
		state.output.print("r" + indStatistics[2] + "\n", log);
		
		Arrays.fill(indStatistics, 0);
		
	}

}
