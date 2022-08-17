package co.edu.univalle.fractal.bl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.charias.utils.MatrixUtils;

import co.edu.univalle.fractal.model.MultifractalDimension;

public class TestMultiFractalRangeJUnit {
	
	String string;
	int[] segs;
	int[] qs;
	int size;

	
	@Before
	public void setUp() throws Exception {
		qs=new int[] {-20,-19,-18,-17,-16,-15,-14,-13,-12,-11,-10,
				       -9,-8,-7,-6,-5,-4,-3,-2,-1,0,
				       1,2,3,4,5,6,7,8,9,10,
				       11,12,13,14,15,16,17,18,19,20};
		segs=new  int[]{1,2,4,8,16,32,64,128,256};
		size=512;
	}

	@Ignore
	@Test
	public void test() {
		string="E:\\Cgai\\Documentos\\Dropbox\\doctorado\\tesis\\data\\prueba_1.fasta";
		BoxCMultiFractalDimension cal=new BoxCMultiFractalDimension();
		MultifractalDimension mfd=cal.getMFDfromFile(string,segs,qs,size);
		
		System.out.println(MatrixUtils.matrixToTab(mfd.getDimensions()));
	}
	
	@Ignore
	@Test
	public void testTxT() {
		string="E:\\Cgai\\Documentos\\Dropbox\\doctorado\\tesis\\data\\prueba.txt";
		BoxCMultiFractalDimension cal=new BoxCMultiFractalDimension();
		MultifractalDimension mfd=cal.getMFDfromFile(string,segs,qs,size);
		
		System.out.println(MatrixUtils.matrixToTab(mfd.getDimensions()));
	}
	
	@Test
	public void testPaternal() {
		string="E:\\Cgai\\Documentos\\Dropbox\\doctorado\\tesis\\data\\HG00137_paternal.fasta";
		int range=1000000;
		BoxCMultiFractalDimensionRange cal=new BoxCMultiFractalDimensionRange();
		List<MultifractalDimension> mfd=cal.getMFDRfromFile(string,segs,qs,size,range);
		for (MultifractalDimension multifractalDimension : mfd) {
			System.out.println(MatrixUtils.matrixToTab(multifractalDimension.getDimensions()));			
		}
	}


}
