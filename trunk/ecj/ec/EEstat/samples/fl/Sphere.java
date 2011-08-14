package ec.EEstat.samples.fl;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.DoubleVectorIndividual;

public class Sphere extends Problem implements SimpleProblemForm 
{

	@Override
	public void evaluate(EvolutionState state, Individual ind,
			int subpopulation, int threadnum) 
	{
        
        if( !( ind instanceof DoubleVectorIndividual ) )
            state.output.fatal( "The individuals for this problem should be DoubleVectorIndividuals." );

        double[] genome = ((DoubleVectorIndividual)ind).genome;
        int len = genome.length;
        float value = 0;

        // Compute the Sphere function for our genome
       for(int i = 0; i < len; i++)
       {
	        value += Math.pow(genome[i], 2);
       }

        ((KozaFitness)(ind.fitness)).setStandardizedFitness(state, value);
    
        ind.evaluated = true;

	}

}
