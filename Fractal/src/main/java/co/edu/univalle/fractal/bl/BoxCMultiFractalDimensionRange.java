package co.edu.univalle.fractal.bl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.charias.utils.MathUtils;
import com.charias.utils.MatrixUtils;
import co.edu.univalle.fractal.model.BoxCountSet;
import co.edu.univalle.fractal.model.BoxCountSetRange;
import co.edu.univalle.fractal.model.MultifractalDimension;

public class BoxCMultiFractalDimensionRange{ // implements IMultiFractalCalculator {
	private static Logger logger=Logger.getLogger(BoxCMultiFractalDimension.class.getClass());

	//@Override
	public MultifractalDimension getMFDfromFile(String p_fileName, int[] segments,int[] qs, int size,int range){
		BoxCountSetRange boxCountSet=new BoxCountSetRange();
		try {
			boxCountSet.countFromFileRange(p_fileName, segments, size,range);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return getMFDR(boxCountSet, segments,qs, size,range);
	}

//	@Override
	public MultifractalDimension getMFDfromString(String p_string, int[] segments,int qs[], int size,int range){
		BoxCountSetRange boxCountSet=new BoxCountSetRange();
		try {
			boxCountSet.countFromStringRange(p_string, segments, size,range);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return getMFDR(boxCountSet, segments,qs, size,range);		
	}

	
	private MultifractalDimension getMFDR(BoxCountSetRange boxCountSet, int[] segments,int [] qs, int size,int range) {
	double[][] countingBySize=new double[2][segments.length];
	MultifractalDimension md=new MultifractalDimension(qs,segments,size);
	for(int i=0;i<segments.length;i++){
        double[][] proportionalMatrix=MatrixUtils.proportionalMatrix(boxCountSet.getBoxCounts()[i].getClusters(),
        		boxCountSet.getBoxCounts()[i].getTotalData());
			double epsilon=(double)((double)size)/((double)segments[i]);
			for(int j=0;j<qs.length;j++){
				md.getMatrixQS()[j][i] =fqPair(qs[j], epsilon, proportionalMatrix);
			}
		}
		double[][] ret=new double[qs.length][2];
		for(int j=0;j<qs.length;j++){
			ret[j]=MathUtils.calculateSlopeR2(md.getMatrixQS()[j]);
		}
		md.setDimensions(ret);
	return md;
}
	
	/**
	 * Retorna el logaritmo de la suma de proporcinoes (en pos 1) y el logaritmo de epsilon por (q-1) (en pos 0)
	 * @param q
	 * @param epsilon
	 * @param clusterP
	 * @return
	 */
	public double[] fqPair(int q, double epsilon, double[][] clusterP) {
		double[] d=new double[2];
		double[][] clusterQ;
		double sum;
		if(q!=1){
			clusterQ=MatrixUtils.matrixPower(clusterP, q);			
			sum=MatrixUtils.matrixSum(clusterQ);
		d[1]=Math.log(sum);
	    d[0]=(Math.log(epsilon)*(double)(q-1));
		logger.info("y:"+(Math.log(sum))+" x:"+(Math.log(epsilon)*(double)(q-1)));
		}else{
			sum=MatrixUtils.matrixSumMulLog(clusterP);
			d[1]=-sum;
			d[0]=Math.log(1d/epsilon);
			logger.info("y:"+(-sum)+" x:"+Math.log(1d/epsilon));
		}
		logger.info("dq:"+q+" ");
		return d;
	}


}
