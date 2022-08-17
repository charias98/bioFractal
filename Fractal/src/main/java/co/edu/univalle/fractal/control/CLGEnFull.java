package co.edu.univalle.fractal.control;

import java.io.IOException;
import java.util.List;

import com.charias.utils.FileUtils;
import com.charias.utils.StringUtils;

import co.edu.univalle.fractal.bl.BoxCMultiFractalDimensionRange;
import co.edu.univalle.fractal.model.BoxCountSetRange;
import co.edu.univalle.fractal.model.MultifractalDimension;

public class CLGEnFull {
	
	
	/**
	 * 
	 * @param args
	 * Nombre muestra
	 * Nombre cromosoma
	 * Nombre archivo fasta
	 * Nombre archivo conteo
	 * Nombre archivo multifractal
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	String sample=args[0];
	String fileName=args[2];
//	int[] qs={-20,-10,-2,-1,1,2,10,20};
	/**		int[] segs={2,4,8,16,32,64,128,256,512};
	int size=1024;
*/
	String chr=args[1];
	String type=args[3];
	String mfFileName=args[5];
	String bcFileName=args[4];
	int zoom=Integer.parseInt(args[6]);
	int[] qs=new int[] {-20,20};
/**	int[] qs=new int[] {-20,-19,-18,-17,-16,-15,-14,-13,-12,-11,-10,
		       -9,-8,-7,-6,-5,-4,-3,-2,-1,0,
		       1,2,3,4,5,6,7,8,9,10,
		       11,12,13,14,15,16,17,18,19,20};
**/
	int[] segs={1,2,4,8,16,32,64,128,256};
	int size=512;
	int range=Integer.parseInt(args[7]);
	BoxCMultiFractalDimensionRange cal=new BoxCMultiFractalDimensionRange();
	BoxCountSetRange boxCountSetRange=new BoxCountSetRange();
	try {
		boxCountSetRange.countFromFileRange(fileName, segs, size,range);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return;
	}
	List<MultifractalDimension> mfd=cal.getMFDR(boxCountSetRange, segs,qs, size,range);
	String bcString=boxCountSetRange.getStringCountsMaxRes(zoom);  // Para que muestre los conteos por 128 segmentos
	//bcString=StringUtils.addStringStartLine(bcString, chr+"\t"+sample+"\t"+type+"\t");
	String mfdString=getStringMFD(mfd,sample,type,range,chr,qs);
	//FileUtils.writeFile(bcFileName, bcString);
	FileUtils.writeFile(mfFileName, mfdString);
	}

	public static String getStringMFD(List<MultifractalDimension> list,String sample,String type,int range,String chr,int[] qs) {
		StringBuilder sb=new StringBuilder(1024);
		int part=0;
		for (MultifractalDimension multifractalDimension : list) {
			double[][] dimensions=multifractalDimension.getDimensions();
			for(int i=0;i<qs.length;i++) {
				sb.append(chr+"\t"+sample+"\t"+type+"\t"+range+"\t"+part+"\t"+i+"\t"+qs[i]+"\t"+
						dimensions[i][MultifractalDimension.ZQPOSITION]+"\t"+dimensions[i][MultifractalDimension.R2POSITION]+"\n");
			}
			part++;
		}
		return sb.toString();
	}
}
