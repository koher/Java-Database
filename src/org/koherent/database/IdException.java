package org.koherent.database;

public class IdException extends Exception {
	private static final long serialVersionUID = 1L;

	private Object id;

	public IdException(Object id) {
		this(id, null);
	}

	public IdException(Object id, Throwable cause) {
		super(id.toString(), cause);

		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public <I> I getId() {
		return (I) id;
	}
}