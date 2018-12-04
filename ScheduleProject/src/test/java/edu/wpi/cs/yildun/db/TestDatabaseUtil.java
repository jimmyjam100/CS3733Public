package edu.wpi.cs.yildun.db;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yidun.db.DatabaseUtil;

public class TestDatabaseUtil {

	@Test
	public void testConnection() {
		try {
			Assert.assertNotNull(DatabaseUtil.connect());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
