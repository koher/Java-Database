package org.koherent.database;

@SuppressWarnings("serial")
public class DuplicateIdException extends IdException {
	public DuplicateIdException(Object id, Throwable cause) {
		super(id, cause);
	}

	public DuplicateIdException(Object id) {
		super(id);
	}
}