package br.com.dextra.sqlstore;

import static br.com.dextra.sqlstore.QueryTest.Constants.FIELD1;
import static br.com.dextra.sqlstore.QueryTest.Constants.FIELD2;
import static br.com.dextra.sqlstore.QueryTest.Constants.FIELD3;
import static br.com.dextra.sqlstore.QueryTest.Constants.TABLE1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UpdateTest {

	@Test
	public void testUpdate() {
		Update update = buildUpdate(TABLE1);

		String sql = update.set(FIELD1, 10).set(FIELD2, FIELD3).setNull(FIELD3).setCurrentTimestamp(FIELD3)
				.where(FIELD3).equalsTo("123").toString();

		assertEquals(
				"UPDATE TABLE1 SET FIELD1 = ?, FIELD2 = FIELD3, FIELD3 = ?, FIELD3 = current_timestamp WHERE FIELD3 = ?",
				sql);
		assertEquals(3, update.getParameters().length);
		assertEquals(10, update.getParameters()[0]);
		assertNull(update.getParameters()[1]);
		assertEquals("123", update.getParameters()[2]);
	}

	private Update buildUpdate(TableName table) {
		return new Update(table, null) {

			@Override
			public void execute() {
			}
		};
	}
}
