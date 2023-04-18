package customExceptions;

public class InvalidLoginException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidLoginException(String message) {
		super(message);
	}

	public InvalidLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
