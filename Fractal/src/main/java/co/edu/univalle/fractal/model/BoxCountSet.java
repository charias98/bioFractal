package co.edu.univalle.fractal.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import co.edu.univalle.fractal.bl.BoxCFractalDimension;
import co.edu.univalle.fractal.bl.CGRGenerator;

public class BoxCountSet {
	private static Logger logger=Logger.getLogger(BoxCFractalDimension.class.getClass());
	BoxCount boxCounts[];
	int boxSize;

	//TODO: Incluir parametros para definir diferentes puntos de inicio para el CGR
	public void countFromFile(String p_fileName, int[] segments,int size) throws IOException {
		File file = new File(p_fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        count(reader,segments,size);
        reader.close();
	}
	
	/**
	 * 
	 * @param p_string
	 * @param segments
	 * @param size
	 * @throws IOException
	 */
	//TODO: Incluir parametros para definir diferentes puntos de inicio para el CGR
	public void countFromString(String p_string, int[] segments,int size) throws IOException {
		Reader reader = new StringReader(p_string);
		BufferedReader bReader= new BufferedReader(reader);
        count(bReader,segments,size);
        bReader.close();		
	}

		
	public void count(BufferedReader reader, int[] segments, int size) throws IOException {
		boxSize = size;
		double[][] points = new double[2][2];
		points[0][0] = CGRGenerator.MID_POINT;
		points[0][1] = CGRGenerator.MID_POINT;
		String symbols = "ACTG";
		CGRGenerator cgr = new CGRGenerator();
//		cgr.setLabelsPoints(CGRGenerator.LABELS_CGTA);
		cgr.setLabelsPoints(CGRGenerator.LABELS_ACTG);
		boxCounts = new BoxCount[segments.length];
		for (int i = 0; i < segments.length; i++) {
			boxCounts[i] = new BoxCount(segments[i]);
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
				totalData = addPoints(points, cgr, boxCounts, totalData, text, i);
			}
			text = reader.readLine();
		}
	}

	private int addPoints(double[][] points, CGRGenerator cgr, BoxCount[] boxCounts, int totalData, String text,
			int i) {
		try {
			points[1]=cgr.generateNextPoint(points[0], text.substring(i, i+1), true);
			if(points[1]!=null){
			//	System.out.println("P:"+points[1][0]+" "+points[1][1]);
			for (BoxCount boxCount : boxCounts) {
				boxCount.addPoint(points[1]);
				points[0][0]=points[1][0];
				points[0][1]=points[1][1];
			}
			totalData++;
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
	
	
}
