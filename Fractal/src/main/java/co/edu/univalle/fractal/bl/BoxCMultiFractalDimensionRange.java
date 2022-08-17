package co.edu.univalle.fractal.bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.charias.utils.MathUtils;
import com.charias.utils.MatrixUtils;

import co.edu.univalle.fractal.model.BoxCount;
import co.edu.univalle.fractal.model.BoxCountSet;
import co.edu.univalle.fractal.model.BoxCountSetRange;
import co.edu.univalle.fractal.model.MultifractalDimension;

public class BoxCMultiFractalDimensionRange { // implements IMultiFractalCalculator {
	private static Logger logger = Logger.getLogger(BoxCMultiFractalDimension.class.getClass());

	// @Override
	public List<MultifractalDimension> getMFDRfromFile(String p_fileName, int[] segments,int[] qs, int size,int range){
		BoxCountSetRange boxCountSetRange=new BoxCountSetRange();
		try {
			boxCountSetRange.countFromFileRange(p_fileName, segments, size,range);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return getMFDR(boxCountSetRange, segments,qs, size,range);
	}


	//@Override
	public List<MultifractalDimension> getMFDRfromString(String p_string, int[] segments,int qs[], int size,int range){
		BoxCountSetRange boxCountSetRange=new BoxCountSetRange();
		try {
			boxCountSetRange.countFromStringRange(p_string, segments, size,range);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return getMFDR(boxCountSetRange, segments,qs, size,range);		
	}


	public List<MultifractalDimension> getMFDR(BoxCountSetRange boxCountSetRange, int[] segments,int [] qs, int size,int range) {
		List<MultifractalDimension> multifractalDimensionsList=new ArrayList<MultifractalDimension>();
		multifractalDimensionsList.add(getMFD(boxCountSetRange.getBoxCounts(),segments,qs,size));
		for (BoxCount[] boxCounts : boxCountSetRange.getBoxCountRanges()) {
			multifractalDimensionsList.add(getMFD(boxCounts,segments,qs,size));
		}
		logger.info("Rangos encontrados:"+boxCountSetRange.getBoxCountRanges().size());
		return multifractalDimensionsList;
	}
	
	private MultifractalDimension getMFD(BoxCount[] boxCounts, int[] segments,int [] qs, int size) {
		MultifractalDimension md=new MultifractalDimension(qs,segments,size);
		for(int i=0;i<segments.length;i++){
			double[][] proportionalMatrix=MatrixUtils.proportionalMatrix(boxCounts[i].getClusters(),
					boxCounts[i].getTotalData());

			/**Adicionado por Christian Arias, se aprovecha el calculo de la proporcionalidad y se actualiza el
       			boxCount original al cual no se le habia hecho el proceso, 
       			para poder pregutnar por  esta proporcionalidad posteriormente
			 */
			boxCounts[i].setProportion(proportionalMatrix);
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
	 * Retorna el logaritmo de la suma de proporcinoes (en pos 1) y el logaritmo de
	 * epsilon por (q-1) (en pos 0)
	 * 
	 * @param q
	 * @param epsilon
	 * @param clusterP
	 * @return
	 */
	public double[] fqPair(int q, double epsilon, double[][] clusterP) {
		double[] d = new double[2];
		double[][] clusterQ;
		double sum;
		if (q != 1) {
			clusterQ = MatrixUtils.matrixPower(clusterP, q);
			sum = MatrixUtils.matrixSum(clusterQ);
			d[1] = Math.log(sum);
			d[0] = (Math.log(epsilon) * (double) (q - 1));
			logger.info("y:" + (Math.log(sum)) + " x:" + (Math.log(epsilon) * (double) (q - 1)));
		} else {
			sum = MatrixUtils.matrixSumMulLog(clusterP);
			d[1] = -sum;
			d[0] = Math.log(1d / epsilon);
			logger.info("y:" + (-sum) + " x:" + Math.log(1d / epsilon));
		}
		logger.info("dq:" + q + " ");
		return d;
	}

}
