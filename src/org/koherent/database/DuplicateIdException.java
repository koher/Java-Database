package org.koherent.database;

public class DuplicateIdException extends IdException {
	private static final long serialVersionUID = 1L;

	public DuplicateIdException(Object id, Throwable cause) {
		super(id, cause);
	}

	public DuplicateIdException(Object id) {
		super(id);
	}
}