package ec.bfa;

import java.util.Random;

import ec.EvolutionState;
import ec.EEstat.DoubleVectorIndividualStat;
import ec.util.Parameter;

public class BFAIndividual extends DoubleVectorIndividualStat
{
	public int swimLength;
	
	public int countSwimLength;
	
	public int direction[];
	
	public static final String P_BACTERIASWIMLENGTH = "swim-length";
	
	public Object clone()
	{
		BFAIndividual clonedInd = (BFAIndividual)super.clone();
		
		clonedInd.swimLength = this.swimLength;
		clonedInd.countSwimLength = this.countSwimLength;
		
		System.arraycopy(this.direction, 0, clonedInd.direction, 0, this.direction.length);
		
		return clonedInd;
	}
	
	public void setup(final EvolutionState state, final Parameter base) 
	{
		super.setup(state, base);

		int genLength = this.genome.length;
		
		Parameter def = defaultBase();
		
		swimLength = state.parameters.getIntWithDefault(base.push(P_BACTERIASWIMLENGTH),
													   def.push(P_BACTERIASWIMLENGTH), 2);
		
		countSwimLength = 0;
		direction = new int[genLength];
		
		this.thumble();
		
	}
	
	public void thumble()
	{
		int genLength = this.genome.length;
		Random rand = new Random();
		
		for(int i = 0; i < genLength; i++)
		{
			direction[i] = rand.nextInt(2) == 0 ? -1 : 1;
		}
	}

}
