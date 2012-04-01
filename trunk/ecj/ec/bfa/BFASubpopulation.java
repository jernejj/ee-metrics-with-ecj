package ec.bfa;

import ec.*;
import ec.util.*;
import ec.vector.*;

public class BFASubpopulation extends Subpopulation
{
	
	public int numOfBacteria;
	
	public int numOfChemoLoop;
	
	public int countChemoSteps;
	
	public int numOfReproduction;
	
	public double chemotacticStepSize;
	
	public double divisorStepsize;
	
	public static final String P_NUMBEROFBACTERIA = "num-bacteria";
	
	public static final String P_NUMBEROFCHEMOTACTICSTEPS = "num-of-chemosteps";
	
	public static final String P_NUMBEROFREPRODUCATIONS = "number-of-reproducation";
	
	public static final String P_CHEMOTACTICSTEPSIZE = "chemo-step-size";
	
	public static final String P_DIVISORSTEPSIZE = "step-divisor";
	
	public void setup(final EvolutionState state, final Parameter base)
	{
		super.setup(state, base);
        
        if (!(species instanceof FloatVectorSpecies))
            state.output.error("BFASubpopulation requires that its species is ec.vector.FloatVectorSpecies or a subclass.  Yours is: " + species.getClass(),
                null,null);
        if (!(species.i_prototype instanceof BFAIndividual))
            state.output.error("BFASubpopulation requires that its species' prototypical individual bacteria is BFAIndividual.  Yours is: " + species.getClass(),
                null,null);
        
        numOfBacteria = state.parameters.getInt(base.push(P_NUMBEROFBACTERIA), null, 1);
        
        numOfChemoLoop = state.parameters.getInt(base.push(P_NUMBEROFCHEMOTACTICSTEPS), null, 1);
        
        numOfReproduction = state.parameters.getInt(base.push(P_NUMBEROFREPRODUCATIONS), null, 0);
        
        chemotacticStepSize = state.parameters.getDouble(base.push(P_CHEMOTACTICSTEPSIZE), null, 0);
        
        divisorStepsize = state.parameters.getDouble(base.push(P_DIVISORSTEPSIZE), null, 0.0);
        
        countChemoSteps = 0;
		
	}

	void clear(DoubleVectorIndividual[] inds)
    {
		for(int x=0; x<inds.length; x++) 
		{ 
			inds[x] = null; 
		}
		
		countChemoSteps = 0;
    }
	
	public void populate(EvolutionState state, int thread)
    {
		super.populate(state, thread);
    }
	
	
	
}
