package org.koherent.database;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractDatabase<I, V extends Value<I>> implements
		Database<I, V> {
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<? extends I> getExistingIds(I... ids)
			throws DatabaseException {
		return getExistingIds(asList(ids));
	}

	@Override
	public Iterable<? extends I> getIds(int limit) throws DatabaseException {
		return getIds(limit, 0);
	}

	@Override
	public Iterable<? extends I> getIds(int limit, int offset)
			throws DatabaseException {
		return new LimitedAndOffsetIterable<I>(getAllIds(), limit, offset);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<? extends V> get(I... ids) throws DatabaseException {
		return get(asList(ids));
	}

	@Override
	public Iterable<? extends V> get(Iterable<? extends I> ids)
			throws DatabaseException {
		List<V> values = new ArrayList<V>();

		for (I id : ids) {
			try {
				values.add(get(id));
			} catch (IdNotFoundException e) {
			}
		}

		return values;
	}

	@Override
	public Iterable<? extends V> get(int limit) throws DatabaseException {
		return get(limit, 0);
	}

	@Override
	public Iterable<? extends V> get(int limit, int offset)
			throws DatabaseException {
		return new LimitedAndOffsetIterable<V>(getAll(), limit, offset);
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

	private static class LimitedAndOffsetIterable<T> implements Iterable<T> {
		private Iterable<? extends T> original;
		private int limit;
		private int offset;

		public LimitedAndOffsetIterable(Iterable<? extends T> original,
				int limit, int offset) {
			this.original = original;
			this.limit = limit;
			this.offset = offset;
		}

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {
				private int count = 0;
				private Iterator<? extends T> originalIterator = original
						.iterator();

				{
					for (int count = 0; count < offset
							&& originalIterator.hasNext(); count++) {
						originalIterator.next();
					}
				}

				@Override
				public boolean hasNext() {
					return count < limit && originalIterator.hasNext();
				}

				@Override
				public T next() throws NoSuchElementException {
					if (count >= limit) {
						throw new NoSuchElementException();
					}

					return originalIterator.next();
				}

				@Override
				public void remove() {
					originalIterator.remove();
				}
			};
		}
	}
}
