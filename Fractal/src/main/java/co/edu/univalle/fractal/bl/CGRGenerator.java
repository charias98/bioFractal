package co.edu.univalle.fractal.bl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import co.edu.univalle.fractal.model.UnknownSymbol;

public class CGRGenerator {

	private static Logger logger=Logger.getLogger(CGRGenerator.class.getClass());

	
	public static final int STANDARD_SEED=0;
	public static final int SELF_REFERENCE_SEED=1;
	public static final int RANDOM_SEED=2;
	public static final double MID_POINT=0.5d;

	public static String[] DEFAULT_LABELS={"G","T","A","C"};
	public static String[] LABELS_GTAC={"G","T","A","C"};
	public static String[] LABELS_GTCA={"G","T","C","A"};
	public static String[] LABELS_GACT={"G","A","C","T"};
	public static String[] LABELS_GATC={"G","A","T","C"};
	public static String[] LABELS_ATGC={"A","T","G","C"};
	public static String[] LABELS_ATCG={"A","T","C","G"};
	public static String[] LABELS_ACTG={"A","C","T","G"};
	public static String[] LABELS_ACGT={"A","C","G","T"};
	public static String[] LABELS_AGCT={"A","G","C","T"};
	public static String[] LABELS_AGTC={"A","G","T","C"};
	public static String[] LABELS_TAGC={"T","A","G","C"};
	public static String[] LABELS_TACG={"T","A","C","G"};
	public static String[] LABELS_TGCA={"T","G","C","A"};
	public static String[] LABELS_TGAC={"T","G","A","C"};
	public static String[] LABELS_CTGA={"C","T","G","A"};
	public static String[] LABELS_CTAG={"C","T","A","G"};
	public static String[] LABELS_CGAT={"C","G","A","T"};
	public static String[] LABELS_CGTA={"C","G","T","A"};
	public static String[][] LABELS_SET={LABELS_GTAC,LABELS_GTCA,LABELS_GACT,LABELS_GATC,
		LABELS_ATGC,LABELS_ATCG,LABELS_AGCT,LABELS_AGTC,
		LABELS_TAGC,LABELS_TACG,LABELS_TGCA,LABELS_TGAC,
		LABELS_CTGA,LABELS_CTAG,LABELS_CGAT,LABELS_CGTA};
	public static double[][] DEFAULT_BASE_POINTS={{0d,0d},{0d,1d},{1d,0d},{1d,1d}};
	public static String[] EXTENDED_LABELS={"A","C","G","T",
								"R","Y","S","W",
								"K","M","B","D",
								"H","V","N"};
	public static double[][] EXTENDED_BASE_POINTS={{0d,0d},{0d,1d},{1d,0d},{1d,1d},
				{0d,0.5d},{0.5d,1d},{0.5d,0.5d},{0.5d,0.5d},
				{1d,0.5d},{0.5d,0d},{0.75d,0.75d},{0.75d,0.25d},
				{0.25d,0.75d},{0.25d,0.25d},{0.5d,0.5d}};
	
	public static double DEFAULT_FACTOR=0.5d;
	public static int DEFAULT_DIMENTIONS=2;
	public static int DEFAULT_LOOK_BACKWARDS=100;
	public static int DEFAULT_SEED=STANDARD_SEED;
	private String[] labelsPoints;
	private double[][] basePoints;
	private double factor;
	private Map<String,Integer> labelMap;
	private double[] size={1d,1d};
	// coordenas de los puntos generados. La primera es la candidad de puntos y la segunda es las dimensiones de cada punto
	private String chain;
	private int lookBackwards;
	private int dimensions;
	private double[] seed;

	public CGRGenerator() {
		super();
//		setLabelsPoints(EXTENDED_LABELS);
//		setBasePoints(EXTENDED_BASE_POINTS);
		setLabelsPoints(DEFAULT_LABELS);
		setBasePoints(DEFAULT_BASE_POINTS);
		setFactor(DEFAULT_FACTOR);
		setDimensions(DEFAULT_DIMENTIONS);
		setLookBackwards(DEFAULT_LOOK_BACKWARDS);
		setSeed(STANDARD_SEED);
	}

	public CGRGenerator(String[] labelsPoints, double[][] basePoints) {
		super();
		setLabelsPoints(labelsPoints);
		setBasePoints(basePoints);
		setDimensions(basePoints[0].length);
		setFactor(DEFAULT_FACTOR);
		setLookBackwards(DEFAULT_LOOK_BACKWARDS);
		setSeed(STANDARD_SEED);
	}
	
	public CGRGenerator(String[] labelsPoints, double[][] basePoints, double factor) {
		super();
		setLabelsPoints(labelsPoints);
		setBasePoints(basePoints);
		setFactor(factor);
		setDimensions(basePoints[0].length);
		setLookBackwards(DEFAULT_LOOK_BACKWARDS);
		setSeed(STANDARD_SEED);
	}
	
	public double[] setSeed(int type){
		double[] seed=new double[dimensions];
		switch(type){
		case STANDARD_SEED:for(int i=0;i<dimensions;i++){
			seed[i]=MID_POINT;
			}break;
		case RANDOM_SEED:for(int i=0;i<dimensions;i++){
			seed[i]=Math.random();
		}break;
		case SELF_REFERENCE_SEED:
			int l=chain.length();
			int startPointPosition=l-lookBackwards%l;
			int pos=startPointPosition%l;
			seed=setSeed(STANDARD_SEED);
			for(int i=0;i<lookBackwards;i++){
				try {
					seed=generateNextPoint(seed, chain.substring(pos,pos+1),true);
				} catch (UnknownSymbol e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pos=(pos+1)%l;
			}
			System.out.println("New pos:"+pos+" a 0 was expected");
			break;
		}
		return seed;
	}
	
	public double[]getSeed(){
		return seed;
	}
	
	public String[] getLabelsPoints() {
		return labelsPoints;
	}

	public void setLabelsPoints(String[] labelsPoints) {
		this.labelsPoints = labelsPoints;
		this.labelMap=new HashMap<String, Integer>();
		for(int i=0;i<labelsPoints.length;i++){
			labelMap.put(labelsPoints[i], i);
		}
	}

	public double[][] getBasePoints() {
		return basePoints;
	}

	public void setBasePoints(double[][] basePoints) {
		this.basePoints = basePoints;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public Map<String, Integer> getLabelMap() {
		return labelMap;
	}

	public void setLabelMap(Map<String, Integer> labelMap) {
		this.labelMap = labelMap;
	}

	public double[] getSize() {
		return size;
	}

	public void setSize(double[] size) {
		this.size = size;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}
	
	public int getLookBackwards() {
		return lookBackwards;
	}

	public void setLookBackwards(int lookBackwards) {
		this.lookBackwards = lookBackwards;
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public double[] generateNextPoint(double[] px_1,String label,boolean ignoreUnknown) throws UnknownSymbol{
		double[] px=new double[px_1.length];
		Integer pos;
		for(int i=0;i<px_1.length;i++){
//			px[i]=factor*(px_1[i]+basePoints[labelMap.get(label)][i]);
			pos=labelMap.get(label);
			if(pos==null){
				if(ignoreUnknown){
				return null;
				}
				else{
					throw new UnknownSymbol(label);
				}
			}
			px[i]=px_1[i]+factor*(basePoints[pos][i]-px_1[i]);
		}
		return px;
	}
	/*
	public double[][] calculateDots() throws UnknownSymbol{
		return calculateDots(STANDARD_SEED);
	}
	public double[][] calculateDots(int seedType) throws UnknownSymbol{
		return calculateDots(seedType,true);
	}
	
		public double[][] calculateDots(int seedType,boolean ignoreUnknown) throws UnknownSymbol{
		points=new double[chain.length()][dimensions];
		double[] previusPoint=getSeed(seedType);
		for (int i=0;i<chain.length();i++) {
			points[i]=generateNextPoint(previusPoint, chain.substring(i,i+1),ignoreUnknown);
			logger.debug("Punto "+i);
			if(points[i]!=null){
				previusPoint=points[i];
			}
		}
		return points;
	}
	
	public String getStringPoints(){
		String string="";
		for (double[] point : points) {
			for (double d : point) {
				string+=" "+d;
			}
			string+="\n";
		}
		return string;
	}
	*/
	public String getLabelFromPoint(double[] coordinates){
		double[] reference=new double[coordinates.length];
		for (int i=0;i<coordinates.length;i++) {
			reference[i] = coordinates[i] < 0.5d ? 0d : 1d;
		}
		boolean like;
		for(int i=0;i<basePoints.length;i++){
			like=true;
			for(int j=0;j<basePoints[0].length;j++){
				if(basePoints[i][j]!=reference[j]){
					like=false;
					break;
				}
			}
			if(like){
				return labelsPoints[i];
			}
		}
		return null;
	}

}
