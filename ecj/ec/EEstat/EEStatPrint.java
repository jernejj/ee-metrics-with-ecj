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
public int popLog;

public int infoLog;

public void setup(final EvolutionState state, final Parameter base)
    {
    // DO NOT FORGET to call super.setup(...) !!
    super.setup(state, base);

    // set up stat file
    File popFile = state.parameters.getFile(
        base.push(P_STATFILE),null);
    if (popFile!=null) try
                           {
                           popLog = state.output.addLog(popFile,true);
                           }
        catch (IOException i)
            {
            state.output.fatal("An IOException occurred while trying to create the log " + 
                popFile + ":\n" + i);
            }

    }

public void postEvaluationStatistics(final EvolutionState state)
    {
    // be certain to call the hook on super!
    super.postEvaluationStatistics(state);

    // write out a warning that the next generation is coming 
    state.output.println("-----------------------\nGENERATION " + 
        state.generation + "\n-----------------------", popLog);

    // print out the population 
    state.population.printPopulation(state,popLog);

    // print out best genome #3 individual in subpop 0
    int best = 0;
    double best_val = ((DoubleVectorIndividual)state.population.subpops[0].individuals[0]).genome[3];
    for(int y=1;y<state.population.subpops[0].individuals.length;y++)
        {
        // We'll be unsafe and assume the individual is a DoubleVectorIndividual
        double val = ((DoubleVectorIndividual)state.population.subpops[0].individuals[y]).genome[3];
        if (val > best_val)
            {
            best = y;
            best_val = val;
            }
        }
    state.population.subpops[0].individuals[best].printIndividualForHumans(state,infoLog);
    }
}