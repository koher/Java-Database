package org.koherent.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class DatabaseTest<I, V extends Value<I>, D extends Database<I, V>> {
	protected abstract D getDatabase();

	protected abstract V createValue();

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

	protected void assertValuesEqual(V expected, V actual) {
		assertEquals(expected, actual);
	}
}
