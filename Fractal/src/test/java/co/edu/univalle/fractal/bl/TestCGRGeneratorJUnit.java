package co.edu.univalle.fractal.bl;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import co.edu.univalle.fractal.model.UnknownSymbol;


public class TestCGRGeneratorJUnit {
	
	String string;
	int[] segs;
	int size;
	
	@Before
	public void setUp() throws Exception {
		string="ACTGACTGACTGACTG";
		segs=new  int[]{1,2,4,8,16,32};
		size=32;
	}

	@Ignore
	@Test
	public void test() {
		BoxCFractalDimension fd=new BoxCFractalDimension();
		double res[]=fd.getFDfromString(string, segs, size);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}

	@Ignore
	@Test
	public void testA() {
		BoxCFractalDimension fd=new BoxCFractalDimension();
		string="A";
		double res[]=fd.getFDfromString(string, segs, size);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}

	@Ignore
	@Test
	public void testACT() {
		BoxCFractalDimension fd=new BoxCFractalDimension();
		string="ACT";
		double res[]=fd.getFDfromString(string, segs, size);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}

	@Ignore
	@Test
	public void testACACACAC() {
		BoxCFractalDimension fd=new BoxCFractalDimension();
		string="ACACACAC";
		double res[]=fd.getFDfromString(string, segs, size);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}

	@Test
	public void testATTC() {
		BoxCFractalDimension fd=new BoxCFractalDimension();
		string="ATTCG";
		double res[]=fd.getFDfromString(string, segs, size);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}
	
	@Test
	public void testACTG_FASTA() {
		BoxCFractalDimension fd=new BoxCFractalDimension();
		string="ATTCG";
		double res[]=fd.getFDfromString(string, segs, size);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}
	
	@Test
	public void testNValue() throws UnknownSymbol {
		CGRGenerator cg=new CGRGenerator();
		double[] px_1=new double[] {0.5d,0.5d};
		char label='N';
		double[]res;
		res=cg.generateNextPoint(px_1, label, true);
		assertNull(res);
	}
	

}
