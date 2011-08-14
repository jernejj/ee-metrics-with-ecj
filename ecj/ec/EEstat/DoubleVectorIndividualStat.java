package ec.EEstat;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

import ec.EvolutionState;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;
import ec.vector.VectorDefaults;
import ec.vector.VectorIndividual;

public class DoubleVectorIndividualStat extends DoubleVectorIndividual implements EEStatIndividualI
{
	public int indTrace[][];
	public int indStatistics[];
	public Vector<Integer> mutatedGenoms;
	public boolean created;
	private int fractNums;
	
	public static final String P_DOUBLEVECTORINDIVIDUALSTAT = "double-vect-ind-stat";
	public static final String P_DOUBLEVECTORPRECISONOUTPUT = "fraction-digits";
    
    public Parameter defaultBase()
    {
        return VectorDefaults.base().push(P_DOUBLEVECTORINDIVIDUALSTAT);
    }
	
	public Object clone() 
	{
		DoubleVectorIndividualStat myobj = (DoubleVectorIndividualStat) (super.clone());

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
		
		myobj.mutatedGenoms = (Vector<Integer>)mutatedGenoms.clone();
		myobj.created = false;
		
		return myobj;
	}

	public void setup(final EvolutionState state, final Parameter base) 
	{
		super.setup(state, base);

		Parameter def = defaultBase();
		
		fractNums = state.parameters.getIntWithDefault(base.push(P_DOUBLEVECTORPRECISONOUTPUT),
													   def.push(P_DOUBLEVECTORPRECISONOUTPUT), 3);
		
		indTrace = new int[3][2];
		indStatistics = new int[4];
		mutatedGenoms = new Vector<Integer>();
		created = false;


	}
	
	public int dimmensionChanged(VectorIndividual ind)
	{
		int dimmChanged = 0;
		
		for (int i = 0; i < this.genome.length; i++)
		{
			if(this.genome[i] != ((DoubleVectorIndividualStat)ind).genome[i])
				dimmChanged++;
		}
		
		return dimmChanged;
	}
	
	public int mutatedDimmensionChanged(VectorIndividual ind)
	{
		int dimmChanged = 0;
		
		for (int i = 0; i < this.genome.length; i++)
		{
			if(this.genome[i] != ((DoubleVectorIndividualStat)ind).genome[i])
			{
				dimmChanged++;
				mutatedGenoms.add(i);
			}
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
	
	public void repairMutations(DoubleVectorIndividualStat similarParent1, DoubleVectorIndividualStat parent2)
	{
		this.indStatistics[1] = 0;
		
		for (int i = 0; i < genome.length; i++) 
		{
			if(genome[i] == similarParent1.genome[i]  &&
				similarParent1.mutatedGenoms.contains(i))
			{
				indStatistics[1]++;
			}
			
			else if(genome[i] != similarParent1.genome[i]  &&
				genome[i] == parent2.genome[i] &&
				parent2.mutatedGenoms.contains(i))
			{
				indStatistics[1]++;
			}
			
		}
	}
	
	
	public void defaultCrossover(EvolutionState state, int thread, VectorIndividual ind)
	{
		DoubleVectorIndividualStat ind1 = (DoubleVectorIndividualStat) this.clone();
		DoubleVectorIndividualStat ind2 = (DoubleVectorIndividualStat) ind.clone();
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
			this.indStatistics[1] = ind1.indStatistics[1];
			this.indStatistics[2] = ind1.indStatistics[2];
			this.indStatistics[3] = ind1.indStatistics[3];
			
			this.repairMutations(ind1, ind2);
			
		}
		else
		{
			this.indTrace[0][0] = ind2.indTrace[2][0];
			this.indTrace[0][1] = ind2.indTrace[2][1];
			
			this.indTrace[1][0] = ind1.indTrace[2][0];
			this.indTrace[1][1] = ind1.indTrace[2][1];
			
			this.indStatistics[0] = this.dimmensionChanged(ind2);
			this.indStatistics[1] = ind2.indStatistics[1];
			this.indStatistics[2] = ind2.indStatistics[2];
			this.indStatistics[3] = ind2.indStatistics[3];
			
			this.repairMutations(ind2, ind1);
		}
		
		
		similar = ((DoubleVectorIndividualStat)ind).similarTo(ind1, ind2);
		
		if(similar == 1)
		{
			((DoubleVectorIndividualStat)ind).indTrace[0] = ind1.indTrace[2].clone();
			
			((DoubleVectorIndividualStat)ind).indTrace[1] = ind2.indTrace[2].clone();
			
			((DoubleVectorIndividualStat)ind).indStatistics = ind1.indStatistics.clone();
			((DoubleVectorIndividualStat)ind).indStatistics[0] = ((DoubleVectorIndividualStat)ind).dimmensionChanged(ind1);
			
			((DoubleVectorIndividualStat)ind).repairMutations(ind1, ind2);
			
		}
		else
		{
			((DoubleVectorIndividualStat)ind).indTrace[0] = ind2.indTrace[2].clone();
			
			((DoubleVectorIndividualStat)ind).indTrace[1] = ind1.indTrace[2].clone();
			
			((DoubleVectorIndividualStat)ind).indStatistics = ind2.indStatistics.clone();
			((DoubleVectorIndividualStat)ind).indStatistics[0] = ((DoubleVectorIndividualStat)ind).dimmensionChanged(ind2);
			
			((DoubleVectorIndividualStat)ind).repairMutations(ind2, ind1);
		}
		
		created = true;
		((DoubleVectorIndividualStat)ind).created = true;

	}
	
	public void defaultMutate(EvolutionState state, int thread)
    {

		int dimmChange = 0;
		
		DoubleVectorIndividualStat tmpInd = (DoubleVectorIndividualStat)this.clone();
		
		super.defaultMutate(state, thread);
		
		dimmChange = this.mutatedDimmensionChanged(tmpInd);
		
		/* store how may dimensions were changed by evolution */
		if(dimmChange > 0)
		{
			indStatistics[1] = dimmChange;
		}
		created = true;
		
    }
    

	public void reset(EvolutionState state, int thread)
    {
	    super.reset(state, thread);

		/* reset the statistics and parents of individual */
	    Arrays.fill(indStatistics, 0);
	    Arrays.fill(indTrace[0], -1);
	    Arrays.fill(indTrace[1], -1);
	    mutatedGenoms.clear();
	    created = true;
	    
    }

	public String genotypeToStringForHumans()
    {
		DecimalFormat decForm = new DecimalFormat();
		decForm.setMaximumFractionDigits(fractNums);
		
	    String s = "";
	    for (int i = 0; i < genome.length; i++)
	        s = s + " " + decForm.format(genome[i]);
	    return s;
    }


	/* this function must be called in preEvaluationStatistics statistic function, meaning that this
	 * must be call post evaluating sequence of EA.
	 * @see ec.EEstat.EEStatIndividualI#printIndividualStats(ec.EvolutionState, int, int)
	 */
	public void printIndividualStats(EvolutionState state, int indSeq, int log)
	{
		DecimalFormat decForm = new DecimalFormat();
		decForm.setMaximumFractionDigits(fractNums);
		
		if(!created)
		{
			return;
		}
		
		/* set the proper ID for the indvidual */
	    indTrace[2][0] = indSeq;
	    indTrace[2][1] = state.generation;

		state.output.print("p1(" + indTrace[0][0] + "," + indTrace[0][1] + ") ", log);
		state.output.print("p2(" + indTrace[1][0] + "," + indTrace[1][1] + ") ", log);
		state.output.print("id(" + indSeq + "," + indTrace[2][1] + ") ", log);
		state.output.print("in(" + genotypeToStringForHumans() + ") ", log);
		state.output.print("c" + indStatistics[0] + " ", log);
		state.output.print("m" + indStatistics[1] + " ", log);
		state.output.print("r" + indStatistics[2] + " ", log);
		state.output.print("fit(" + decForm.format(this.fitness.fitness()) + ")\n", log);
		
		/* erase previus statistics of individual */
		Arrays.fill(indStatistics, 0);
		mutatedGenoms.clear();
		created = false;
		
	}
}
