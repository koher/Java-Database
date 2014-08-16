package org.koherent.database;

public class IdNotFoundException extends IdException {
	private static final long serialVersionUID = 1L;

	public IdNotFoundException(Object id, Throwable cause) {
		super(id, cause);
	}

	public IdNotFoundException(Object id) {
		super(id);
	}
}