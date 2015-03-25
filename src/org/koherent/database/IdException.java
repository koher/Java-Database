package org.koherent.database;

@SuppressWarnings("serial")
public class IdException extends Exception {
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