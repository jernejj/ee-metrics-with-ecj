/**
 * 
 */
package ec.vector;

import java.util.Map;

import ec.EvolutionState;
import ec.util.Parameter;

/**
 * @author Jernej
 *
 */
public class IntegerVectorIndividualStat extends IntegerVectorIndividual
{

	public int indTrace[][];
	public int indStatistics[];
	
	public Object clone()
    {
		IntegerVectorIndividualStat myobj = (IntegerVectorIndividualStat) (super.clone());

	    // must clone the genome
	    myobj.indTrace = (int[][])indTrace.clone();
	    myobj.indStatistics = (int[])indStatistics.clone();
	    
	    return myobj;
    } 
	
	public void setup(final EvolutionState state, final Parameter base)
    {
	    super.setup(state,base); 
	    
	    indTrace = new int[3][2];
	    indStatistics = new int[4];

    }


}
