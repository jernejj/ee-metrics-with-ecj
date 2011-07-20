/**
 * 
 */
package ec.vector;


import ec.EvolutionState;
import ec.util.Parameter;

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
	
	public int similarTo(VectorIndividual ind1, VectorIndividual ind2)
	{
		int dimmDiffFirst = 0;
		int dimmDiffSec = 0;
		
		for (int i = 0; i < this.genome.length; i++)
		{
			if(this.genome[i] != ((IntegerVectorIndividualStat)ind1).genome[i])
				dimmDiffFirst++;
		}
		
		for (int i = 0; i < this.genome.length; i++)
		{
			if(this.genome[i] != ((IntegerVectorIndividualStat)ind2).genome[i])
				dimmDiffSec++;
		}
		
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
		VectorIndividual ind1 = (VectorIndividual) this.clone();
		VectorIndividual ind2 = (VectorIndividual) ind.clone();
		
		super.defaultCrossover(state, thread, ind);
		
		/*TODO: preveri kateri je blizji novima nastalima posameznikoma. in to shrani v indTrace.
		 * ter primerno posodobi indStatistics. 
		 */
		
		
		
	}

}
