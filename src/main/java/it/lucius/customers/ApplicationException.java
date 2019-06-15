package it.lucius.customers;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;

	public ApplicationException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
