package org.koherent.database;

public class InterruptedTransactionException extends DatabaseException {
	private static final long serialVersionUID = 1L;

	public InterruptedTransactionException() {
		super();
	}

	public InterruptedTransactionException(String message) {
		super(message);
	}

	public InterruptedTransactionException(Throwable cause) {
		super(cause);
	}
}