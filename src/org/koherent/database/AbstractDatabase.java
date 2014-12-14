package org.koherent.database;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractDatabase<I, V extends Value<I>> implements
		Database<I, V> {
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<? extends I> getExistingIds(I... ids)
			throws DatabaseException {
		return getExistingIds(asList(ids));
	}

	@Override
	public Iterator<? extends I> getIds(int limit) throws DatabaseException {
		return getIds(limit, 0);
	}

	@Override
	public Iterator<? extends I> getIds(int limit, int offset)
			throws DatabaseException {
		Iterator<? extends I> iterator = getAllIds();
		for (int count = 0; count < offset; count++) {
			if (iterator.hasNext()) {
				iterator.next();
			} else {
				break;
			}
		}

		return iterator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<? extends V> get(I... ids) throws DatabaseException {
		return get(asList(ids));
	}

	@Override
	public Iterator<? extends V> get(Iterable<? extends I> ids)
			throws DatabaseException {
		List<V> values = new ArrayList<V>();

		for (I id : ids) {
			try {
				values.add(get(id));
			} catch (IdNotFoundException e) {
			}
		}

		return values.iterator();
	}

	@Override
	public Iterator<? extends V> get(int limit) throws DatabaseException {
		return get(limit, 0);
	}

	@Override
	public Iterator<? extends V> get(int limit, int offset)
			throws DatabaseException {
		Iterator<? extends V> iterator = getAll();
		for (int count = 0; count < offset; count++) {
			if (iterator.hasNext()) {
				iterator.next();
			} else {
				break;
			}
		}

		return iterator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(V... values) throws DatabaseException {
		put(asList(values));
	}

	@Override
	public void put(Iterable<? extends V> values) throws DatabaseException {
		for (V value : values) {
			put(value);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(I... ids) throws DatabaseException {
		remove(asList(ids));
	}

	@Override
	public void remove(Iterable<? extends I> ids) throws DatabaseException {
		for (I id : ids) {
			remove(id);
		}
	}
}
