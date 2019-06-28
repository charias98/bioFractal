package co.edu.univalle.fractal.bl;

import co.edu.univalle.fractal.model.MultifractalDimension;

public interface IMultiFractalCalculator {
	
	public MultifractalDimension getMFDfromFile(String p_fileName,int segments[],int qs[],int size);
	public MultifractalDimension getMFDfromString(String p_string,int segments[],int qs[],int size);

}
