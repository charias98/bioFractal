package co.edu.univalle.fractal.control;

import java.io.IOException;
import java.util.List;

import com.charias.utils.FileUtils;

import co.edu.univalle.fractal.bl.BoxCMultiFractalDimensionRange;
import co.edu.univalle.fractal.model.MultifractalDimension;
import co.edu.univalle.fractal.model.UnknownSymbol;

public class CLTesis {

	/*
	public static void main1(String[] args) throws UnknownSymbol, InterruptedException {
		String[] labels=CGR.LABELS_ACTG;
		int[] qs={-20,-10,-2,-1,1,2,10,20};
		long initTime=System.currentTimeMillis();
		String ret;
//		long initTime2;
//		long endTime2;
//			initTime2=System.currentTimeMillis();
		/* Argumetnos: Directorio, muestra,  chromosoma 
//			String name=args[0]+"/"+args[1]+"_"+args[2]+".fasta";
			String name=args[0]+"/"+args[1]+"_"+args[2]+".fasta";
	//		System.out.println(name);
	    //String name="/home/charias98/db/Chr1.fna";
		CGR cgr=CGRFactory.createDNACGRfromFile(name,labels);
		if (cgr.getChain()==null || cgr.getChain().isEmpty()) return;
			double[][] dots=cgr.calculateDots();
		//	System.out.println(ArrayUtils.arrayToString(labels)+":"+ArrayUtils.arrayToString(BoxCDimension.getInstance().getDimensionQ(dots, 512, qs)));
			{
			//int[] segs={1,2,4,8,16,32,64,128,256,512};
				/**
				 * Cantidad de segmentos
				 
			int[] segs={2,4,8,16,32,64,128,256,512};
			MultifractalDimension md1=BoxCDimension.getInstance().getMFDimension(dots, segs, qs,1024);
			//System.out.println(md1.getDq());
			//System.out.println(md1.getTabQs());
//			endTime2=System.currentTimeMillis();
//			System.out.println("Tiempo sin hilos chr"+i+":"+(endTime2-initTime2));
			ret=args[1]+"\t"+args[2]+"\t"+args[3]+"\t"+md1.getDq()+"\t"+md1.getTabQs()+"\t";
			}
				BoxCounting boxCounting=new BoxCounting(dots);
			boxCounting.setFullSize(4096d);
			boxCounting.setBoxSizes(new double[]{2048d,1024d,512d,256d,128d,64d,32d,16d,8d,4d});
			boxCounting.setInputType(AFractalDimensionCalculator.DOTS);
			double res[]=boxCounting.getFD();
//			System.out.println(res[0]);
//			System.out.println(res[1]);
			System.out.println((ret+res[0]+"\t"+res[1]).replace("\n", "\t"));
	 }
	*/
	public static void main(String[] args) throws IOException {
		String ret=args[0]+"\t"+args[1]+"\t"+args[2];
		String fileName=args[0]+"/"+args[1]+"_"+args[2]+".fasta";
		String fileOut=args[3];
//		int[] qs={-20,-10,-2,-1,1,2,10,20};
		/**		int[] segs={2,4,8,16,32,64,128,256,512};
		int size=1024;
*/
		String chr=args[0].substring(args[0].indexOf("chr",37)+3);
		int[] qs=new int[] {-20,-19,-18,-17,-16,-15,-14,-13,-12,-11,-10,
			       -9,-8,-7,-6,-5,-4,-3,-2,-1,0,
			       1,2,3,4,5,6,7,8,9,10,
			       11,12,13,14,15,16,17,18,19,20};

		int[] segs={1,2,4,8,16,32,64,128,256};
		int size=512;
		int range=1000000;
		BoxCMultiFractalDimensionRange cal=new BoxCMultiFractalDimensionRange();
		List<MultifractalDimension> mfd=cal.getMFDRfromFile(fileName,segs,qs,size,range);
		ret=print(mfd,args[1],args[2],chr,qs);
		System.out.println(ret);
		FileUtils.writeFile(fileOut, ret);
	}
	
	public static String print(List<MultifractalDimension> list,String sample,String type,String chr,int[] qs) {
		StringBuilder sb=new StringBuilder(1024);
		int part=0;
		for (MultifractalDimension multifractalDimension : list) {
			double[][] dimensions=multifractalDimension.getDimensions();
			for(int i=0;i<qs.length;i++) {
				sb.append(chr+"\t"+sample+"\t"+type+"\t"+part+"\t"+i+"\t"+qs[i]+"\t"+
						dimensions[i][MultifractalDimension.ZQPOSITION]+"\t"+dimensions[i][MultifractalDimension.R2POSITION]+"\n");
			}
			part++;
		}
		return sb.toString();
	}
		
}
