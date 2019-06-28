package co.edu.univalle.fractal.bl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.charias.utils.MathUtils;
import com.charias.utils.MatrixUtils;

import co.edu.univalle.fractal.model.BoxCountSet;

public class BoxCFractalDimension implements IFractalCalculator {
	private static Logger logger=Logger.getLogger(BoxCFractalDimension.class.getClass());

	@Override
	public double[] getFDfromFile(String p_fileName, int[] segments, int size){
		BoxCountSet boxCountSet=new BoxCountSet();
		try {
			boxCountSet.countFromFile(p_fileName, segments, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return getFD(boxCountSet, segments, size);
	}

	@Override
	public double[] getFDfromString(String p_string, int[] segments, int size){
		BoxCountSet boxCountSet=new BoxCountSet();
		try {
			boxCountSet.countFromString(p_string, segments, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return getFD(boxCountSet, segments, size);		
	}

	
	private double[] getFD(BoxCountSet boxCountSet, int[] segments, int size) {
	double[][] countingBySize=new double[2][segments.length];
	for(int i=0;i<segments.length;i++){
		int boxes=boxCountSet.getBoxCounts()[i].getBoxes();
		countingBySize[0][i]=((double)size)/((double)segments[i]);
		countingBySize[1][i]=boxes;
	}
	System.out.println(MatrixUtils.matrixToString(countingBySize));
	double[] res=MathUtils.log10AndSlope(countingBySize);
	return res;
}


}
