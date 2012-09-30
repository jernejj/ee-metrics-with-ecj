/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package ec.EEstat.samples.DE;

import javabbob.JNIfgeneric;
import ec.util.*;
import ec.*;
import ec.simple.*;
import ec.vector.*;

public class BBOBSuite extends Problem implements SimpleProblemForm 
{
	public static final String P_NUMBER_OF_EVALUETIONS = "numOfEval";
	
	public static final String P_FUNCTION_ID = "fun_id";
	
	public static final String P_FUNCTION_DIMM = "fun_dimm";

	public int numOfEval = 100;

	public int fun_id = 1;
	
	public int fun_dimm;
	
	JNIfgeneric fgeneric;

	// nothing....
	public void setup(final EvolutionState state, final Parameter base) 
	{
		super.setup(state, base);

		fgeneric = new JNIfgeneric();

		String outputPath;

		JNIfgeneric.Params params = new JNIfgeneric.Params();
		
		/*
		 * Modify the following parameters, choosing a different setting for
		 * each new experiment
		 */
		params.algName = "EEStat";
		params.comments = "Exploration and Exploitation Calculation";
		outputPath = "D:/outputBlackBoxTest";


		/* Creates the folders for storing the experimental data. */
		if (JNIfgeneric.makeBBOBdirs(outputPath, false)) 
		{
			System.out.println("BBOB data directories at " + outputPath
					+ " created.");
		}
		else 
		{
			state.output.fatal("Error! BBOB data directories at " + outputPath
					+ " was NOT created, stopping.");
			return;
		}
		
		fun_dimm = state.parameters.getIntWithDefault(
				base.push(P_FUNCTION_DIMM), null, 2);

		fun_id = state.parameters.getIntWithMax(
				base.push(P_FUNCTION_ID), null, 1, 24);

		numOfEval = state.parameters.getIntWithDefault(
				base.push(P_NUMBER_OF_EVALUETIONS), null, 500);

		
		fgeneric.initBBOB(fun_id, 1, fun_dimm, outputPath, params);
	}

	public void evaluate(final EvolutionState state, final Individual ind,
			final int subpopulation, final int threadnum) 
	{
		/*
		 * if( !( ind instanceof DoubleVectorIndividual ) ) state.output.fatal(
		 * "The individuals for this problem should be DoubleVectorIndividuals."
		 * );
		 */
		
		DoubleVectorIndividual temp = (DoubleVectorIndividual) ind;
		double fit = 0.0;

		if (fun_dimm != temp.genomeLength())
		{
			state.output.fatal("function dimmension and genome size are different!");
			fgeneric.exitBBOB();
		}
		
		fit = -Math.abs(fgeneric.evaluate(temp.genome) - fgeneric.getFtarget());

		boolean isOptimal = false;

		if (fgeneric.getEvaluations() == numOfEval)
		{
			state.output.println("reach evaluation limit, finishing...", 0);
			state.finisher.finishPopulation(state, ec.EvolutionState.R_SUCCESS);
			isOptimal = true;
		}

		// set the fitness appropriately
		((SimpleFitness) (ind.fitness)).setFitness(state, (float) fit,
				isOptimal);
		ind.evaluated = true;

	}

	public void closeContacts(EvolutionState state, int result) 
	{
		super.closeContacts(state, result);
		
		System.out.println("number of evaluations " + fgeneric.getEvaluations());

		fgeneric.exitBBOB();


	}

}
