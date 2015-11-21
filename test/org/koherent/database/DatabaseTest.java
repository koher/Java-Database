package org.koherent.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class DatabaseTest<I, V extends Value<I>, D extends Database<I, V>> {
	protected abstract D getDatabase();

	protected abstract V createValue();

	protected abstract V createValue(I id);

	@Before
	public void setUp() {
		setUpDatabase();
	}

	@After
	public void tearDown() {
		tearDownDatabase();
	}

	protected abstract void setUpDatabase();

	protected abstract void tearDownDatabase();

	@Test
	public void testPutAndGet() {
		D database = getDatabase();

		V value = createValue();
		try {
			database.put(value);
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		try {
			V gotten = database.get(value.getId());
			assertValuesEqual(value, gotten);
		} catch (IdNotFoundException | DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetAll() {
		D database = getDatabase();

		final int N = 100;

		Map<I, V> idToValue = new HashMap<I, V>();
		for (int i = 0; i < N; i++) {
			V value = createValue();
			try {
				database.put(value);
			} catch (DatabaseException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			idToValue.put(value.getId(), value);
		}

		try {
			for (V value : database.getAll()) {
				assertValuesEqual(idToValue.get(value.getId()), value);
				idToValue.remove(value.getId());
			}
			assertEquals(0, idToValue.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		D database = getDatabase();

		final V originalValue = createValue();
		final I id = originalValue.getId();
		final V newValue = createValue(originalValue.getId());

		try { // null to null -> nothing
			V updated = database.update(id, new Database.Updater<V>() {
				@Override
				public V update(V value) {
					assertNull(value);
					return null;
				}
			});
			assertNull(updated);
		} catch (IllegalUpdateException | DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		try { // null to value with a wrong id
			V updated = database.update(id, new Database.Updater<V>() {
				@Override
				public V update(V value) {
					assertNull(value);
					return createValue();
				}
			});
			fail("Different id from the original: before = " + id
					+ ", after = " + updated.getId());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalUpdateException e) {
			// OK
		}

		try { // null to originalValue -> put
			V updated = database.update(id, new Database.Updater<V>() {
				@Override
				public V update(V value) {
					assertNull(value);
					return originalValue;
				}
			});
			assertValuesEqual(originalValue, updated);
			assertValuesEqual(originalValue, database.get(id));
		} catch (IllegalUpdateException | DatabaseException
				| IdNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		try { // updating the id
			V updated = database.update(id, new Database.Updater<V>() {
				@Override
				public V update(V value) {
					assertValuesEqual(originalValue, value);
					return createValue();
				}
			});
			fail("The id has been updated: before = " + id + ", after = "
					+ updated.getId());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalUpdateException e) {
			// OK
		}

		try { // value to newValue -> normal updating
			V updated = database.update(id, new Database.Updater<V>() {
				@Override
				public V update(V value) {
					assertValuesEqual(originalValue, value);
					return newValue;
				}
			});
			assertValuesEqual(newValue, updated);
			assertValuesEqual(newValue, database.get(id));
		} catch (IllegalUpdateException | DatabaseException
				| IdNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		try { // value to null -> remove
			V updated = database.update(id, new Database.Updater<V>() {
				@Override
				public V update(V value) {
					assertValuesEqual(newValue, value);
					return null;
				}
			});
			assertNull(updated);
			assertFalse(database.exists(id));
		} catch (IllegalUpdateException | DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	protected void assertValuesEqual(V expected, V actual) {
		assertEquals(expected, actual);
	}
}
