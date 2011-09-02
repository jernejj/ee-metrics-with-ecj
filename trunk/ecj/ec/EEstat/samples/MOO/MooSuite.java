/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.EEstat.samples.MOO;

import ec.util.*;
import ec.*;
import ec.multiobjective.MultiObjectiveFitness;
import ec.simple.*;
import ec.vector.*;


 
public class MooSuite extends Problem implements SimpleProblemForm
    {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String P_WHICH_PROBLEM = "type";
    public static final String P_G01 = "g01";
    public static final String P_G03 = "g03";
    public static final String P_G09 = "g09";
    public static final String P_G10 = "g10";


    //Some of the following problems requires an exact number of decision variables (genes). This is mentioned in comment preceding the problem.

    public static final int PROB_G01 = 1;
    public static final int PROB_G03 = 2;
    public static final int PROB_G09 = 3;
    public static final int PROB_G10 = 4;

    public int problemType = PROB_G01;  // defaults on zdt1

    public void setup(final EvolutionState state, final Parameter base)
        {
        super.setup(state, base);
        String wp = state.parameters.getStringWithDefault( base.push( P_WHICH_PROBLEM ), null, "" );
        if( wp.compareTo( P_G01) == 0 )
            problemType = PROB_G01;
        else if ( wp.compareTo( P_G03) == 0 )
            problemType = PROB_G03;
        else if ( wp.compareTo( P_G09) == 0 )
            problemType = PROB_G09; 
        else if ( wp.compareTo( P_G10) == 0 )
            problemType = PROB_G10;
        else state.output.fatal(
            "Invalid value for parameter, or parameter not found.\n" +
            "Acceptable values are:\n" +
            "  " + P_G01 + "\n" +
            "  " + P_G03 + "\n" +
            "  " + P_G09 + "\n" +
            "  " + P_G10 + "\n" +
            base.push( P_WHICH_PROBLEM ) );
        }

    public void evaluate(final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum)
        {
        if( !( ind instanceof DoubleVectorIndividual ) )
            state.output.fatal( "The individuals for this problem should be DoubleVectorIndividuals." );

        DoubleVectorIndividual temp = (DoubleVectorIndividual)ind;
        double[] genome = temp.genome;
        int numDecisionVars = genome.length;

        float[] objectives = ((MultiObjectiveFitness)ind.fitness).getObjectives();

        double sum;
                
        switch(problemType)
            {
            case PROB_G01:
            	double sum1 = 0;
            	double sum2 = 0;
            	double sum3 = 0;
                sum = 0;
                for(int i = 0; i< 4; ++i)
                    sum1 += genome[i];
                sum1 *= 5;
                
                for(int i = 0; i< 4; ++i)
                    sum2 += Math.pow(genome[i], 2);
                sum2 *= 5;
                
                for(int i = 4; i < 13; ++i)
                	sum3 += genome[i]; 
                
                sum = sum1 - sum2 - sum3;
                objectives[0] = (float)sum;
                objectives[1] = (float)(2*genome[0] + 2*genome[1] + 2*genome[9] + genome[10] - 10);
                objectives[2] = (float)(2*genome[0] + 2*genome[2] + 2*genome[9] + genome[11] - 10);
                objectives[3] = (float)(2*genome[1] + 2*genome[2] + 2*genome[10] + genome[11] - 10);
                objectives[4] = (float)(-8*genome[0] + genome[9]);
                objectives[5] = (float)(-8*genome[1] + genome[10]);
                objectives[6] = (float)(-8*genome[2] + genome[11]);
                objectives[7] = (float)(-2*genome[3] - genome[4] + genome[9]);
                objectives[8] = (float)(-2*genome[5] - genome[6] + genome[10]);
                objectives[9] = (float)(-2*genome[7] - genome[8] + genome[11]);
                break;
                
            case PROB_G03:
            	double prod = 1.0;
            	sum = 0;
                int length = genome.length;
                for(int i = 0; i < length; ++i)
                	prod *= genome[i];
                
                for(int i = 0; i < length; ++i)
                	sum += Math.pow(genome[i], 2);
                
                objectives[0] = (float)(-Math.pow(Math.sqrt(length), length) * prod);
                objectives[1] = (float)(sum - 1);
                break;
                
            case PROB_G09:
            	objectives[0] = (float)(Math.pow(genome[0] - 10, 2) + 5*Math.pow(genome[1] - 12, 2) + Math.pow(genome[2], 4) +
            					        3*Math.pow(genome[3] - 11, 2) + 10*Math.pow(genome[4], 6) + 7*Math.pow(genome[5], 2) +
            					        Math.pow(genome[6], 4) - 4*genome[5]*genome[6] - 10*genome[5] - 8*genome[6]);
            	objectives[1] = (float)(-127 + 2*Math.pow(genome[0], 2) + 3*Math.pow(genome[1], 4) + genome[2] + 4*Math.pow(genome[3], 2) + 5*genome[5]);
            	objectives[2] = (float)(-282 + 7*genome[0] + 3*genome[1] + 10*Math.pow(genome[2], 2) +genome[3] - genome[4] );
            	objectives[3] = (float)(-196 + 23*genome[0] + Math.pow(genome[1], 2) + 6*Math.pow(genome[5], 2) - 8*genome[6]);
            	objectives[4] = (float)(4*Math.pow(genome[0], 2) + Math.pow(genome[1], 2) - 3*genome[0]*genome[1] + 2*Math.pow(genome[2], 2) + 5*genome[5] - 11*genome[6]);
            	
            case PROB_G10:
            	objectives[0] = (float)(genome[0] + genome[1] + genome[2]);
            	objectives[1] = (float)(-1 + 0.0025*(genome[3] + genome[5]));
            	objectives[2] = (float)(-1 + 0.0025*(genome[4] + genome[6] - genome[3]));
            	objectives[3] = (float)(-1 + 0.01*(genome[7] - genome[4]));
            	objectives[4] = (float)(-genome[0]*genome[5] + 833.33252*genome[3] + 100*genome[0] - 83333.333);
            	objectives[5] = (float)(-genome[1]*genome[6] + 1250*genome[4] + genome[1]*genome[3] - 1250*genome[3]);
            	objectives[6] = (float)(-genome[2]*genome[7] + 1250000 + genome[2]*genome[4] - 2500*genome[4]);
                
            default:
                state.output.fatal( "ec.app.ecsuite.ECSuite has an invalid problem -- how on earth did that happen?" );
                break;
            }

        ((MultiObjectiveFitness)ind.fitness).setObjectives(state, objectives);
        ind.evaluated = true;
        }
    }
