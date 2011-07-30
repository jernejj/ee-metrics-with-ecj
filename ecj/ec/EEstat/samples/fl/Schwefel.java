package ec.EEstat.samples.fl;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.EEstat.FloatVectorIndividualStat;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;

public class Schwefel extends Problem implements SimpleProblemForm 
{

	@Override
	public void evaluate(EvolutionState state, Individual ind,
			int subpopulation, int threadnum)
	{
		
		
		if( !( ind instanceof FloatVectorIndividualStat ) )
            state.output.fatal( "The individuals for this problem should be DoubleVectorIndividuals." );

        float[] genome = ((FloatVectorIndividualStat)ind).genome;
        int len = genome.length;
        float value = 0;

        // Compute the Sphere function for our genome
       for(int i = 0; i < len; i++)
       {
	        value += genome[i] * Math.sin( Math.sqrt(Math.abs(genome[i])) );
       }
       
       value = (float)418.9829 * len + value;

        ((KozaFitness)(ind.fitness)).setStandardizedFitness(state, value);
    
        ind.evaluated = true;

	}

}
