package co.edu.univalle.fractal.bl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.charias.utils.MathUtils;
import com.charias.utils.MatrixUtils;

public class TestLogSlope {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		double[][] countingBySize=new double[][] {{1d,1d/2d,1d/3d,1d/5d,1d/10d,1d/15d,1d/30d,1d/40d,1d/50d},
			{7181d,951d,136d, 74d,  20d,   6d,    3d,    2d,    1d}};
		double[] res=MathUtils.log10AndSlope(countingBySize);
		System.out.println(res[0]);
		System.out.println(-1/res[0]);
	}
	
	
	@Test
	public void testLog10_2() {
		double[][] countingBySize=new double[][] {{1d,1d/2d,1d/3d,1d/5d,1d/10d,1d/15d,1d/30d,1d/40d,1d/50d},
												{7181d,951d,136d, 74d,  20d,   6d,    3d,    2d,    1d}};
			System.out.println(MatrixUtils.matrixToTab(countingBySize));
			double[][] matrix=new double[2][];
		matrix[0]=MathUtils.log10(countingBySize[0]);
		matrix[1]=MathUtils.log10(countingBySize[1]);
		System.out.println(MatrixUtils.matrixToString(matrix));
		double[] res=MathUtils.log10AndSlope(countingBySize);
		System.out.println(res[0]);
		System.out.println(-1/res[0]);

	}

}
