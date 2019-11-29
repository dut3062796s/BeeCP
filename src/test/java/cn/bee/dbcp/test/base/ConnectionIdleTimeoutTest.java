package cn.bee.dbcp.test.base;

import java.sql.Connection;
import cn.bee.dbcp.BeeDataSource;
import cn.bee.dbcp.BeeDataSourceConfig;
import cn.bee.dbcp.pool.FastConnectionPool;
import cn.bee.dbcp.test.JdbcConfig;
import cn.bee.dbcp.test.TestCase;
import cn.bee.dbcp.test.TestUtil;

public class ConnectionIdleTimeoutTest extends TestCase {
	private BeeDataSource ds;

	public void setUp() throws Throwable {
		BeeDataSourceConfig config = new BeeDataSourceConfig();
		config.setJdbcUrl(JdbcConfig.JDBC_URL);
		config.setDriverClassName(JdbcConfig.JDBC_DRIVER);
		config.setUsername(JdbcConfig.JDBC_USER);
		config.setPassword(JdbcConfig.JDBC_PASSWORD);
		config.setInitialSize(5);
		config.setConnectionTestSQL("SELECT 1 from dual");
		config.setIdleTimeout(3000);
		config.setIdleCheckTimeInitDelay(10);
		ds = new BeeDataSource(config);
	}

	public void tearDown() throws Throwable {
		ds.close();
	}

	public void test() throws InterruptedException, Exception {
		FastConnectionPool pool = (FastConnectionPool) TestUtil.getPool(ds);
		
		if(pool.getConnTotalSize()!=5)throw new java.lang.AssertionError("Total connections not as expected 5");
		if(pool.getConnIdleSize()!=5)throw new java.lang.AssertionError("Idle connections not as expected 5");
		
		Connection connection = ds.getConnection();
		if(pool.getConnUsingSize()!=1)throw new java.lang.AssertionError("Idle connections not as expected 4");
		connection.close();

		if(pool.getConnTotalSize()!=5)throw new java.lang.AssertionError("Total connections not as expected 5");
		if(pool.getConnIdleSize()!=5)throw new java.lang.AssertionError("Idle connections not as expected 5");
	}
}
