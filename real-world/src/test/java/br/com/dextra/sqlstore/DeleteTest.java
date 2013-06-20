package br.com.dextra.sqlstore;

import static br.com.dextra.sqlstore.QueryTest.Constants.FIELD2;
import static br.com.dextra.sqlstore.QueryTest.Constants.FIELD3;
import static br.com.dextra.sqlstore.QueryTest.Constants.TABLE1;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeleteTest {

	@Test
	public void testDelete() {
		Delete delete = buildDelete(TABLE1);

		String sql = delete.where(FIELD3).equalsTo("123").and(FIELD2).isNull().toString();

		assertEquals("DELETE FROM TABLE1 WHERE (FIELD3 = ? AND FIELD2 IS NULL)", sql);
		assertEquals(1, delete.getParameters().length);
		assertEquals("123", delete.getParameters()[0]);
	}

	private Delete buildDelete(TableName table) {
		return new Delete(table, null) {

			@Override
			public void execute() {
			}
		};
	}
}
