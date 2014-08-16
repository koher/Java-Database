package org.koherent.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractDatabase<I, V extends Value<I>> implements
		Database<I, V> {
	@Override
	public List<I> getIds(int limit) throws InterruptedTransactionException {
		return getIds(limit, 0);
	}

	@Override
	public List<I> getIds(int limit, int offset)
			throws InterruptedTransactionException {
		List<I> ids = new ArrayList<I>();

		Iterator<I> iterator = getAllIds();
		for (int count = 0; count < offset; count++) {
			if (iterator.hasNext()) {
				iterator.next();
			} else {
				break;
			}
		}
		for (int count = 0; count < limit; count++) {
			if (iterator.hasNext()) {
				ids.add(iterator.next());
			} else {
				break;
			}
		}

		return ids;
	}

	@Override
	public List<V> get(I... ids) throws InterruptedTransactionException {
		List<V> values = new ArrayList<V>();

		for (I id : ids) {
			try {
				values.add(get(id));
			} catch (IdNotFoundException e) {
				throw new InterruptedTransactionException(e);
			}
		}

		return values;
	}

	@Override
	public List<V> get(Iterable<I> ids) throws InterruptedTransactionException {
		List<V> values = new ArrayList<V>();

		for (I id : ids) {
			try {
				values.add(get(id));
			} catch (IdNotFoundException e) {
				throw new InterruptedTransactionException(e);
			}
		}

		return values;
	}

	@Override
	public List<V> get(int limit) throws InterruptedTransactionException {
		return get(limit, 0);
	}

	@Override
	public List<V> get(int limit, int offset)
			throws InterruptedTransactionException {
		List<V> values = new ArrayList<V>();

		Iterator<V> iterator = getAll();
		for (int count = 0; count < offset; count++) {
			if (iterator.hasNext()) {
				iterator.next();
			} else {
				break;
			}
		}
		for (int count = 0; count < limit; count++) {
			if (iterator.hasNext()) {
				values.add(iterator.next());
			} else {
				break;
			}
		}

		return values;
	}

	@Override
	public void put(Iterable<V> values) throws InterruptedTransactionException {
		for (V value : values) {
			put(value);
		}
	}

	@Override
	public void remove(Iterable<I> ids) throws InterruptedTransactionException {
		for (I id : ids) {
			remove(id);
		}
	}
}
