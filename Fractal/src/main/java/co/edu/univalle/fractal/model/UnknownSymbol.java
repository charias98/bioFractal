package co.edu.univalle.fractal.model;

public class UnknownSymbol extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6114252415768042103L;
	private char symbol;

	public UnknownSymbol(char symbol) {
		super();
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	

}
