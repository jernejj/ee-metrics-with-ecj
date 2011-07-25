package ec.EEstat;

import java.io.File;
import java.io.IOException;

import ec.EvolutionState;
import ec.Statistics;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;

public class EEStatPrint extends Statistics
{

public static final String P_STATFILE = "stat-file";
public int statLog;


public void setup(final EvolutionState state, final Parameter base)
    {
	    // DO NOT FORGET to call super.setup(...) !!
	    super.setup(state, base);
	
	    // set up stat file
	    File statFile = state.parameters.getFile(base.push(P_STATFILE),null);
	    if (statFile!=null) 
	    {
			try 
			{
				statLog = state.output.addLog(statFile, true);
			} 
			catch (IOException i) 
			{
				state.output
						.fatal("An IOException occurred while trying to create the log "
								+ statFile + ":\n" + i);
			}
	    }

    }

public void postEvaluationStatistics(final EvolutionState state)
    {
	
	    // be certain to call the hook on super!
	    super.postEvaluationStatistics(state);
	
  
	    for (int i = 0; i < state.population.subpops[0].individuals.length; i++) 
	    {
			if (state.population.subpops[0].individuals[i] instanceof EEStatIndividualI)
			{
				((EEStatIndividualI)state.population.subpops[0].individuals[i]).printIndividualStats(state, statLog);
			}
		}
    }

}

