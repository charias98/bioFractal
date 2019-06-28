package co.edu.univalle.fractal.model;

public class UnknownSymbol extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6114252415768042103L;
	private String symbol;

	public UnknownSymbol(String symbol) {
		super();
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	

}
