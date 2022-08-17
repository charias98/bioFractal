package co.edu.univalle.fractal.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import co.edu.univalle.fractal.bl.BoxCFractalDimension;
import co.edu.univalle.fractal.bl.CGRGenerator;

public class BoxCountSetRange {
	private static Logger logger = Logger.getLogger(BoxCFractalDimension.class.getClass());
	BoxCount[] boxCounts;
	/*
	 * Cada elemeneto de la lista es un arreglo de boxCount para los diferentes
	 * tamaños de caja de cada RANGO
	 */
	List<BoxCount[]> boxCountRanges;
	/**
	 * Este atributo apunta al boxCount de la ventana que se esta procesando
	 */
	BoxCount[] rangeBoxCount;
	int boxSize;
	int rangeCount;
	int range;

	// TODO: Incluir parametros para definir diferentes puntos de inicio para el CGR
	public void countFromFileRange(String p_fileName, int[] segments, int size, int range) throws IOException {
		File file = new File(p_fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		count(reader, segments, size, range);
		reader.close();
	}

	/**
	 * 
	 * @param p_string
	 * @param segments
	 * @param size
	 * @throws IOException
	 */
	// TODO: Incluir parametros para definir diferentes puntos de inicio para el CGR
	public void countFromStringRange(String p_string, int[] segments, int size, int range) throws IOException {
		Reader reader = new StringReader(p_string);
		BufferedReader bReader = new BufferedReader(reader);
		count(bReader, segments, size, range);
		bReader.close();
	}

	public void count(BufferedReader reader, int[] segments, int size, int range) throws IOException {
		this.range = range;
		boxSize = size;
		boxCountRanges = new ArrayList<BoxCount[]>();
		double[][] points = new double[2][2];
		points[0][0] = CGRGenerator.MID_POINT;
		points[0][1] = CGRGenerator.MID_POINT;
// Corregido por Christian Arias, tenia una A adicional		String symbols = "ACTGA";
		String symbols = "ACTG";
		CGRGenerator cgr = new CGRGenerator();
//		cgr.setLabelsPoints(CGRGenerator.LABELS_CGTA);
		cgr.setLabelsPoints(CGRGenerator.LABELS_ACTG);
		boxCounts = new BoxCount[segments.length];
		for (int i = 0; i < segments.length; i++) {
			boxCounts[i] = new BoxCount(segments[i]);
		}
		rangeBoxCount = new BoxCount[segments.length];
		for (int i = 0; i < segments.length; i++) {
			rangeBoxCount[i] = new BoxCount(segments[i]);
		}
		int totalData = 0;
		String text = null;
		Pattern pattern = Pattern.compile("[^" + symbols + "]");
		Matcher matcher;
		int line = 1;
		// repeat until all lines is read
		text = reader.readLine();
		if (text.length() > 0 && text.charAt(0) == '>') {
			text = reader.readLine();
		}
		while (text != null) {
			matcher = pattern.matcher(text);
			line++;
			if (matcher.find()) {
				logger.warn("Caracter invalido en la linea " + line + ":" + matcher.group());
			}
			/* Ciclo para procesar los caracteres de cada linea */
			for (int i = 0; i < text.length(); i++) {
				totalData = addPoints(points, cgr, boxCounts, text, totalData, i, range, segments);
			}
			text = reader.readLine();
		}
		boxCountRanges.add(rangeBoxCount);
	}

	private int addPoints(double[][] points, CGRGenerator cgr, BoxCount[] boxCounts, String text, int totalData, int i,
			int range, int[] segments) {
		try {
			points[1] = cgr.generateNextPoint(points[0], text.charAt(i), true);
			if (points[1] != null) {
				// System.out.println("P:"+points[1][0]+" "+points[1][1]);
				for (BoxCount boxCount : boxCounts) {
					boxCount.addPoint(points[1]);
					points[0][0] = points[1][0];
					points[0][1] = points[1][1];
				}
				for (BoxCount boxCount : rangeBoxCount) {
					boxCount.addPoint(points[1]);
					points[0][0] = points[1][0];
					points[0][1] = points[1][1];
				}
				/**
				 * Modificacion Christian Arias 2019 08 05
				 * Se extrae toda esta seccion de codigo por fuera del IF para que aunque sea un
				 * caracter no reconocible, se cuente dentro de la ventana
				 */
/**				totalData++;
				rangeCount++;
				if (rangeCount == range) {
					boxCountRanges.add(rangeBoxCount);
					rangeBoxCount = new BoxCount[segments.length];
					for (int j = 0; j < segments.length; j++) {
						rangeBoxCount[j] = new BoxCount(segments[j]);
					}
					rangeCount = 0;
				}
**/
			}
			totalData++;
			rangeCount++;
			if (rangeCount == range) {
				boxCountRanges.add(rangeBoxCount);
				rangeBoxCount = new BoxCount[segments.length];
				for (int j = 0; j < segments.length; j++) {
					rangeBoxCount[j] = new BoxCount(segments[j]);
				}
				rangeCount = 0;
			}
		} catch (UnknownSymbol e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalData;
	}

	public BoxCount[] getBoxCounts() {
		return boxCounts;
	}

	public void setBoxCounts(BoxCount[] boxCounts) {
		this.boxCounts = boxCounts;
	}

	public int getBoxSize() {
		return boxSize;
	}

	public void setBoxSize(int boxSize) {
		this.boxSize = boxSize;
	}

	public List<BoxCount[]> getBoxCountRanges() {
		return boxCountRanges;
	}

	public void setBoxCountRanges(List<BoxCount[]> boxCountRanges) {
		this.boxCountRanges = boxCountRanges;
	}
	
	/**
	 * Solo imprime el conteo más fino, el cual presumiblemente es el ultimo
	 * TODO: Se debería corregirp para que busque y encuentre e más fino y no que vaya 
	 * simplemente a la ultima posición
	 * Pos indica que posicion del arreglo de conteos se debe explorar
	 * @return
	 */
	public String getStringCountsMaxRes(int pos) {
		StringBuilder sb=new StringBuilder(1024);
		//DecimalFormat decimalFormat = new DecimalFormat("#.#########");
		BoxCount bcGeneral=boxCounts[pos];
		String ls=System.lineSeparator();
//		double[][] proportion=bcGeneral.getProportion();
		int[][] cluster=bcGeneral.getClusters();
		for(int i=0;i<cluster.length;i++) {
			for(int j=0;j<cluster[0].length;j++) {
//				sb.append("0\t"+bcGeneral.getSegments()+"\t"+i+"\t"+j+"\t"+proportion[i][j]+ls);	
				sb.append("0\t"+bcGeneral.getSegments()+"\t"+i+"\t"+j+"\t"+cluster[i][j]+"\t"+bcGeneral.getTotalData()+ls);	
			}
		}
		int w=1;
		for (BoxCount[] boxCountW: boxCountRanges) {
			BoxCount bc=boxCountW[pos];
//			proportion=bc.getProportion();
			cluster=bc.getClusters();
			for(int i=0;i<cluster.length;i++) {
				for(int j=0;j<cluster[0].length;j++) {
					sb.append(w+"\t"+bc.getSegments()+"\t"+i+"\t"+j+"\t"+cluster[i][j]+"\t"+bc.getTotalData()+ls);	
				}
			}
			w++;
		}
		return sb.toString();
	}

}
