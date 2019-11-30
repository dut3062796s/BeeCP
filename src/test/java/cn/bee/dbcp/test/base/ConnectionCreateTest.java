package cn.bee.dbcp.test.base;

import java.sql.Connection;

import cn.bee.dbcp.BeeDataSource;
import cn.bee.dbcp.BeeDataSourceConfig;
import cn.bee.dbcp.test.JdbcConfig;
import cn.bee.dbcp.test.TestCase;

public class ConnectionCreateTest extends TestCase {
	private BeeDataSource ds;

	public void setUp() throws Throwable {
		BeeDataSourceConfig config = new BeeDataSourceConfig();
		config.setJdbcUrl(JdbcConfig.JDBC_URL);
		config.setDriverClassName(JdbcConfig.JDBC_DRIVER);
		config.setUsername(JdbcConfig.JDBC_USER);
		config.setPassword(JdbcConfig.JDBC_PASSWORD);
		ds = new BeeDataSource(config);
	}

	public void tearDown() throws Throwable {
		ds.close();
	}

	public void test() throws InterruptedException, Exception {
		Connection connection = ds.getConnection();
		if(connection==null)
			throw new java.lang.AssertionError("connection is null");
			
		connection.close();
	}
}