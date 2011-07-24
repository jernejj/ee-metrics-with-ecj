/**
 * 
 */
package ec.EEstat;


import java.util.Arrays;

import ec.EvolutionState;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;
import ec.vector.IntegerVectorSpecies;
import ec.vector.VectorIndividual;

/**
 * @author Jernej
 * 
 */
public class IntegerVectorIndividualStat extends IntegerVectorIndividual 
{

	public int indTrace[][];
	public int indStatistics[];
	private static int indID;
	
	
	public Object clone() 
	{
		IntegerVectorIndividualStat myobj = (IntegerVectorIndividualStat) (super
				.clone());

		// must clone the genome
		myobj.indTrace = (int[][]) indTrace.clone();
		myobj.indStatistics = (int[]) indStatistics.clone();

		return myobj;
	}

	public void setup(final EvolutionState state, final Parameter base) 
	{
		indID++;
		super.setup(state, base);

		indTrace = new int[3][2];
		indStatistics = new int[4];

		indTrace[0][0] = -1; 
		indTrace[0][1] = -1;

		indTrace[1][0] = -1;
		indTrace[1][1] = -1;

		indTrace[2][0] = state.generation;
		indTrace[2][1] = indID;

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
		int dimmDiffFirst = this.dimmensionChanged(ind1);
		int dimmDiffSec = this.dimmensionChanged(ind2);
		
		
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
		
		this.indStatistics[1] = 1;
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
		
		/*TODO: preveri kateri je blizji novima nastalima posameznikoma. in to shrani v indTrace.
		 * ter primerno posodobi indStatistics. 
		 */

		
	}
	
	public void defaultMutate(EvolutionState state, int thread)
    {
		IntegerVectorIndividualStat tmpInd = (IntegerVectorIndividualStat)this.clone();
		
		super.defaultMutate(state, thread);
		
		this.indStatistics[2] = this.dimmensionChanged(tmpInd);
    }
    

public void reset(EvolutionState state, int thread)
    {
	    IntegerVectorSpecies s = (IntegerVectorSpecies) species;
	    for(int x=0;x<genome.length;x++)
	        genome[x] = randomValueFromClosedInterval((int)s.minGene(x), (int)s.maxGene(x), state.random[thread]);
	    
	    Arrays.fill(this.indStatistics, 0);
	    Arrays.fill(this.indTrace[0], -1);
	    Arrays.fill(this.indTrace[1], -1);
	    
	    indTrace[2][0] = state.generation;
		indTrace[2][1] = indID;
    }

}
