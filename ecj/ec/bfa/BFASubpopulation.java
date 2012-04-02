package ec.bfa;

import ec.*;
import ec.util.*;
import ec.vector.*;

public class BFASubpopulation extends Subpopulation
{
	
	public BFAIndividual previousIndividuals[];
	
	public void setup(final EvolutionState state, final Parameter base)
	{
		super.setup(state, base);
        
        if (!(species instanceof FloatVectorSpecies))
            state.output.error("BFASubpopulation requires that its species is ec.vector.FloatVectorSpecies or a subclass.  Yours is: " + species.getClass(),
                null,null);
        if (!(species.i_prototype instanceof BFAIndividual))
            state.output.error("BFASubpopulation requires that its species' prototypical individual bacteria is BFAIndividual.  Yours is: " + species.getClass(),
                null,null);
       
        previousIndividuals = new BFAIndividual[individuals.length];
	}

	void clear(DoubleVectorIndividual[] inds)
    {
		for(int x=0; x<inds.length; x++) 
		{ 
			inds[x] = null; 
		}
		
    }
	
	public void populate(EvolutionState state, int thread)
    {
		super.populate(state, thread);
		
		 for (int i = 0; i < individuals.length; i++)
         {
	         BFAIndividual prevInd = (BFAIndividual)individuals[i].clone();                  
	         previousIndividuals[i] = prevInd;
         }
    }
	
	
	
}
