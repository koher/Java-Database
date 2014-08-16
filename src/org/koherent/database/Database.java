package org.koherent.database;

import java.util.Iterator;
import java.util.List;

public interface Database<I, V extends Value<I>> {
	public boolean exists(I id) throws InterruptedTransactionException;

	public List<I> getIds(int limit) throws InterruptedTransactionException;

	public List<I> getIds(int limit, int offset)
			throws InterruptedTransactionException;

	public Iterator<I> getAllIds() throws InterruptedTransactionException;

	public V get(I id) throws InterruptedTransactionException,
			IdNotFoundException;

	public List<V> get(I... ids) throws InterruptedTransactionException,
			IdNotFoundException;

	public List<V> get(Iterable<I> ids) throws InterruptedTransactionException,
			IdNotFoundException;

	public List<V> get(int limit) throws InterruptedTransactionException;

	public List<V> get(int limit, int offset)
			throws InterruptedTransactionException;

	public Iterator<V> getAll() throws InterruptedTransactionException;

	public I add(V value) throws InterruptedTransactionException,
			DuplicateIdException;

	public void put(V value) throws InterruptedTransactionException;

	public void put(Iterable<V> values) throws InterruptedTransactionException;

	public void remove(I id) throws InterruptedTransactionException;

	public void remove(Iterable<I> ids) throws InterruptedTransactionException;

	public void remove(int limit) throws InterruptedTransactionException;

	public void removeAll() throws InterruptedTransactionException;
}
