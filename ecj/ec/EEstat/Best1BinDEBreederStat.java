package ec.EEstat;

import ec.EvolutionState;
import ec.Individual;
import ec.de.Best1BinDEBreeder;
import ec.vector.DoubleVectorIndividual;

public class Best1BinDEBreederStat extends Best1BinDEBreeder 
{
	
	public DoubleVectorIndividual createIndividual(final EvolutionState state, int subpop, int index, int thread) 
	{
		Individual[] inds = state.population.subpops[subpop].individuals;

		DoubleVectorIndividualStat v = (DoubleVectorIndividualStat) (inds[index].clone());

		do 
		{
			// select three indexes different from each other and from that of
			// the current parent
			int r0, r1, r2;
			// do
			{
				r0 = bestSoFarIndex[subpop];
			}
			// while( r0 == index );
			do
			{
				r1 = state.random[thread].nextInt(inds.length);
			} while (r1 == r0 || r1 == index);
			do 
			{
				r2 = state.random[thread].nextInt(inds.length);
			} while (r2 == r1 || r2 == r0 || r2 == index);

			DoubleVectorIndividualStat g0 = (DoubleVectorIndividualStat) (inds[r0]);
			DoubleVectorIndividualStat g1 = (DoubleVectorIndividualStat) (inds[r1]);
			DoubleVectorIndividualStat g2 = (DoubleVectorIndividualStat) (inds[r2]);

			for (int i = 0; i < v.genome.length; i++)
				v.genome[i] = g0.genome[i]
						+ (F + state.random[thread].nextDouble() * F_NOISE - (F_NOISE / 2.0))
						* (g1.genome[i] - g2.genome[i]);
			
			
			int similar = v.similarTo(g0, g1);
			
			if (similar == 1)
			{
				similar = v.similarTo(g0, g2);
				
				if(similar == 1)
				{
					v.indTrace[0][0] = g0.indTrace[2][0];
					v.indTrace[0][1] = g0.indTrace[2][1];
					
					v.indTrace[1][0] = g2.indTrace[2][0];
					v.indTrace[1][1] = g2.indTrace[2][1];
					
					v.indStatistics[0] = 0;
					v.indStatistics[1] = v.dimmensionChanged(g0);
					v.indStatistics[2] = g0.indStatistics[2];
					v.indStatistics[3] = g0.indStatistics[3];
					
					
				}
				else
				{
					v.indTrace[0][0] = g2.indTrace[2][0];
					v.indTrace[0][1] = g2.indTrace[2][1];
					
					v.indTrace[1][0] = g0.indTrace[2][0];
					v.indTrace[1][1] = g0.indTrace[2][1];
					
					v.indStatistics[0] = 0;
					v.indStatistics[1] = v.dimmensionChanged(g2);
					v.indStatistics[2] = g2.indStatistics[2];
					v.indStatistics[3] = g2.indStatistics[3];

				}
			}
			else
			{
				similar = v.similarTo(g1, g2);
				
				if(similar == 1)
				{
					v.indTrace[0][0] = g1.indTrace[2][0];
					v.indTrace[0][1] = g1.indTrace[2][1];
					
					v.indTrace[1][0] = g2.indTrace[2][0];
					v.indTrace[1][1] = g2.indTrace[2][1];
					
					v.indStatistics[0] = 0;
					v.indStatistics[1] = v.dimmensionChanged(g1);
					v.indStatistics[2] = g1.indStatistics[2];
					v.indStatistics[3] = g1.indStatistics[3];

					
				}
				else
				{
					v.indTrace[0][0] = g2.indTrace[2][0];
					v.indTrace[0][1] = g2.indTrace[2][1];
					
					v.indTrace[1][0] = g1.indTrace[2][0];
					v.indTrace[1][1] = g1.indTrace[2][1];
					
					v.indStatistics[0] = 0;
					v.indStatistics[1] = v.dimmensionChanged(g2);
					v.indStatistics[2] = g2.indStatistics[2];
					v.indStatistics[3] = g2.indStatistics[3];

				}
			}
			v.created = true;
			
			
		} while (!valid(v));
		
		DoubleVectorIndividualStat tmpV = (DoubleVectorIndividualStat)v.clone();
		DoubleVectorIndividualStat parent = (DoubleVectorIndividualStat)(inds[index]);
		DoubleVectorIndividualStat newInd = (DoubleVectorIndividualStat)crossover(state, parent, v, thread);
		int similar = newInd.similarTo(tmpV, parent);
		
		if (similar == 2)
		{
			v.indTrace[0][0] = parent.indTrace[2][0];
			v.indTrace[0][1] = parent.indTrace[2][1];
			
			v.indTrace[1][0] = -1;
			v.indTrace[1][1] = -1;
			
			v.indStatistics[0] = v.dimmensionChanged(parent);
			v.indStatistics[1] = 0;
		}
		else
		{
			v.indStatistics[0] = 0;
			v.indStatistics[1] = v.dimmensionChanged(parent);
		}
		
		return newInd;
	}


}
