package customExceptions;

public class InvalidEmailException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidEmailException(String message) {
		super(message);
	}

	public InvalidEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
