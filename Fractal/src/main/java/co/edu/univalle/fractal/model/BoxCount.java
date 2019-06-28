package co.edu.univalle.fractal.model;

import org.apache.log4j.Logger;

public class BoxCount {
	
	private static Logger logger=Logger.getLogger(BoxCount.class.getClass());
	
	int[][] clusters;
	int boxesUsed;
	double[][] proportion;
	int totalData;
	double segmentsSize;
	int segments;
	double size;
	
	
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public int[][] getClusters() {
		return clusters;
	}
	public void setClusters(int[][] clusters) {
		this.clusters = clusters;
	}
	public int getBoxes() {
		return boxesUsed;
	}
	public void setBoxes(int boxes) {
		this.boxesUsed = boxes;
	}
	public double[][] getProportion() {
		if(proportion==null){
			
		}
		return proportion;
	}
	public void setProportion(double[][] proportion) {
		this.proportion = proportion;
	}
	
	public int getTotalData() {
		return totalData;
	}
	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}
	public BoxCount(int[][] clusters, int boxes, int totalData, double[][] proportion) {
		super();
		this.clusters = clusters;
		this.boxesUsed = boxes;
		this.proportion = proportion;
		this.totalData = totalData;
	}
	public BoxCount(int segments){
		int boxes=0;
		this.segments=segments;
		clusters=new int[segments][segments];
		segmentsSize=(1d/segments);
		totalData=0;
	}
	
	public void addPoint(double[] point){
		if(point!=null){
			int x=(int)(point[0]/segmentsSize);
			int y=(int)(point[1]/segmentsSize);
			//TODO: Por qué en ocasiones se presenta esto si se supone que debería truncar?
			if(x==segments){
				x--;
			}
			//TODO: Por qué en ocasiones se presenta esto si se supone que debería truncar?
			if(y==segments){
				y--;
			}
			if(clusters[x][y]==0){
				boxesUsed++;
			}
			clusters[x][y]++;
		}else{
			logger.debug("No se encontraron valores para la posición ???");
		}
		totalData++;
	}
	

}
