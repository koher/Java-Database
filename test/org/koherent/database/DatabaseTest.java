package org.koherent.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class DatabaseTest<I, V extends Value<I>> {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	protected abstract Database<I, V> getDatabase();

	protected abstract V createNewValue();

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testPutAndGet() {
		Database<I, V> database = getDatabase();

		V value = createNewValue();
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
