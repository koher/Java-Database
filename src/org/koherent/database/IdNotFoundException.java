package org.koherent.database;

@SuppressWarnings("serial")
public class IdNotFoundException extends IdException {
	public IdNotFoundException(Object id, Throwable cause) {
		super(id, cause);
	}

	public IdNotFoundException(Object id) {
		super(id);
	}
}