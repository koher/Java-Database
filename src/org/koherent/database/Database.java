package org.koherent.database;

import java.util.Iterator;

public interface Database<I, V extends Value<I>> {
	public boolean exists(I id) throws DatabaseException;

	@SuppressWarnings("unchecked")
	public Iterator<? extends I> getExistingIds(I... ids)
			throws DatabaseException;

	public Iterator<? extends I> getExistingIds(Iterable<? extends I> ids)
			throws DatabaseException;

	public Iterator<? extends I> getIds(int limit) throws DatabaseException;

	public Iterator<? extends I> getIds(int limit, int offset)
			throws DatabaseException;

	public Iterator<? extends I> getAllIds() throws DatabaseException;

	public V get(I id) throws IdNotFoundException, DatabaseException;

	@SuppressWarnings("unchecked")
	public Iterator<? extends V> get(I... ids) throws DatabaseException;

	public Iterator<? extends V> get(Iterable<? extends I> ids)
			throws DatabaseException;

	public Iterator<? extends V> get(int limit) throws DatabaseException;

	public Iterator<? extends V> get(int limit, int offset)
			throws DatabaseException;

	public Iterator<? extends V> getAll() throws DatabaseException;

	public I add(V value) throws DatabaseException, DuplicateIdException;

	public void put(V value) throws DatabaseException;

	@SuppressWarnings("unchecked")
	public void put(V... values) throws DatabaseException;

	public void put(Iterable<? extends V> values) throws DatabaseException;

	public void remove(I id) throws DatabaseException;

	@SuppressWarnings("unchecked")
	public void remove(I... ids) throws DatabaseException;

	public void remove(Iterable<? extends I> ids) throws DatabaseException;

	public void removeAll() throws DatabaseException;
}
