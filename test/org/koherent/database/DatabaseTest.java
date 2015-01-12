package org.koherent.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
			assertEquals(value, gotten);
		} catch (IdNotFoundException | DatabaseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
