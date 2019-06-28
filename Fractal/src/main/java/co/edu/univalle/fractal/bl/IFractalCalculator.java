package co.edu.univalle.fractal.bl;

public interface IFractalCalculator {
	
	public double[] getFDfromFile(String p_fileName,int segments[],int size);
	public double[] getFDfromString(String p_string,int segments[],int size);

}
