package org.koherent.database;

@SuppressWarnings("serial")
public class IllegalUpdateException extends Exception {
	public IllegalUpdateException() {
		super();
	}

	public IllegalUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalUpdateException(String message) {
		super(message);
	}

	public IllegalUpdateException(Throwable cause) {
		super(cause);
	}
}
