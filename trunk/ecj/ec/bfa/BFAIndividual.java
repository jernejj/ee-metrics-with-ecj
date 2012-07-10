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
	
	public double healt;
	
	private int initialSwimLength;
	
	public static final String P_BACTERIASWIMLENGTH = "swim-length";
	
	public Object clone()
	{
		BFAIndividual clonedInd = (BFAIndividual)super.clone();
		
		clonedInd.swimLength = this.swimLength;
		clonedInd.countSwimLength = this.countSwimLength;
		clonedInd.healt = this.healt;
		clonedInd.initialSwimLength = this.initialSwimLength;
		
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
		healt = 0;
		direction = new int[genLength];
		
		initialSwimLength = swimLength;
		
		this.thumble(state);
		
	}
	
	public void reset(EvolutionState state, int thread)
	{
		super.reset(state, thread);
		
		this.swimLength = this.initialSwimLength;
		this.countSwimLength = 0;
		this.healt = 0;
		thumble(state);
	}
	
	public void thumble(EvolutionState state)
	{
		int genLength = this.genome.length;
		
		for(int i = 0; i < genLength; i++)
		{
			direction[i] = state.random[0].nextBoolean() ? -1 : 1;
		}
	}

}
