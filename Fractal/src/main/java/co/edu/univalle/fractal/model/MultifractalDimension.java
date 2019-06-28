package co.edu.univalle.fractal.model;

public class MultifractalDimension {
	
	public static final int ZQPOSITION=0;
	public static final int R2POSITION=1;
	
	/**
	 * Double matrix where is stored Zq represented by x and y values for each segment size.
	 * The first coordinate is q, the second is the quantity of segments,
	 * and the third are the x (sum log),y (log epsilon * (q-1))values
	 */
	double[][][] matrixQS;
	int[] segments;
	int[] qs;
	
	/**
	 * Store Z(q) and R2 value of q
	 */
	double dimensions[][];
	double dq;
	double R2;
	double originalBoxSize;
	
	public double[][][] getMatrixQS() {
		return matrixQS;
	}
	public void setMatrixQS(double[][][] matrixQS) {
		this.matrixQS = matrixQS;
	}
	public double[][] getDimensions() {
		return dimensions;
	}
	public void setDimensions(double[][] dimensions) {
		this.dimensions = dimensions;
	}
	public double getOriginalBoxSize() {
		return originalBoxSize;
	}
	public void setOriginalBoxSize(double originalBoxSize) {
		this.originalBoxSize = originalBoxSize;
	}
	public int[] getSegments() {
		return segments;
	}
	public void setSegments(int[] segments) {
		this.segments = segments;
	}
	public int[] getQs() {
		return qs;
	}
	public void setQs(int[] qs) {
		this.qs = qs;
	}
	public double getR2() {
		return R2;
	}
	public void setR2(double r2) {
		R2 = r2;
	}
	public MultifractalDimension(int[] qs,int[] segments,double originalBoxSize) {
		super();
		this.segments=segments;
		this.qs=qs;
		this.originalBoxSize = originalBoxSize;
		this.matrixQS=new double[qs.length][segments.length][2];
	}
	public String getTabQs(){
		int items=qs.length;
		StringBuilder sb=new StringBuilder(items*20);
		for(int i=0;i<items;i++){			
			sb.append(qs[i]);
			sb.append("\t");
			sb.append(dimensions[i][0]);
			sb.append("\t");
			sb.append(dimensions[i][1]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	//TODO:Revisar, esta función debería llmarseDelta DQ
	public double getDq(){
		return dimensions[0][ZQPOSITION]-dimensions[dimensions.length-1][ZQPOSITION];
		
	}
	public String getQSString(){
		StringBuilder sb=new StringBuilder(matrixQS.length*matrixQS[0].length*matrixQS[0][0].length*10);
		for(int i=0;i<matrixQS.length;i++){
			for(int j=0;j<matrixQS[0].length;j++){
				for(int k=0;k<matrixQS[0][0].length;k++){
					sb.append(matrixQS[i][j][k]);
					if(k!=matrixQS[i][j].length-1){
						sb.append("\t");
					}else{
						sb.append("\n");							
					}
				}

			}
		}
		return sb.toString();
	}
}
