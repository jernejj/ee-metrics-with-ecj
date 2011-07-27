/**
 * 
 */
package ec.EEstat;


import java.util.Arrays;

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
		
		
		/* for information: use of indTrace.clone() is not sufficient here, hard copy must be done */
		
		myobj.indTrace = new int[3][2];
		myobj.indStatistics = new int[4];
		
       	for (int i = 0; i < indTrace.length; i++) 
			for (int j = 0; j < indTrace[i].length; j++) 
			{
				myobj.indTrace[i][j] = indTrace[i][j];
			}
		
		for (int i = 0; i < indStatistics.length; i++) 
		{
			myobj.indStatistics[i] = indStatistics[i];
		}
		
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
		
		/* after the crossover, compute and sets the right parent for the new childs */
		
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
		
		IntegerVectorIndividualStat tmpInd = (IntegerVectorIndividualStat)this.clone();
		
		super.defaultMutate(state, thread);
		
		dimmChange = this.dimmensionChanged(tmpInd);
		
		/* store how may dimensions were changed by evolution */
		if(dimmChange > 0)
		{
			indStatistics[1] = dimmChange;
		}
		
    }
    

	public void reset(EvolutionState state, int thread)
    {
		super.reset(state, thread);

		/* reset the statistics and parents of individual */
	    Arrays.fill(indStatistics, 0);
	    Arrays.fill(indTrace[0], -1);
	    Arrays.fill(indTrace[1], -1);
	    
    }



	/* this function must be called in preEvaluationStatistics statistic function, meaning that this
	 * must be call post evaluating sequence of EA.
	 * @see ec.EEstat.EEStatIndividualI#printIndividualStats(ec.EvolutionState, int, int)
	 */
	public void printIndividualStats(EvolutionState state, int indSeq, int log)
	{
	    
		/* set the proper ID for the indvidual */
	    indTrace[2][0] = indSeq;
	    indTrace[2][1] = state.generation;

		state.output.print("p1(" + indTrace[0][0] + "," + indTrace[0][1] + ") ", log);
		state.output.print("p2(" + indTrace[1][0] + "," + indTrace[1][1] + ") ", log);
		state.output.print("id(" + indSeq + "," + indTrace[2][1] + ") ", log);
		state.output.print("in(" + genotypeToStringForHumans() + ") ", log);
		state.output.print("c" + indStatistics[0] + " ", log);
		state.output.print("m" + indStatistics[1] + " ", log);
		state.output.print("r" + indStatistics[2] + "\n", log);
		
		/* erase previus statistics of individual */
		Arrays.fill(indStatistics, 0);
		
	}

}
