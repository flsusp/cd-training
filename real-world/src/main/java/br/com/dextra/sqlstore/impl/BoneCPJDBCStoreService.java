package br.com.dextra.sqlstore.impl;

import java.sql.SQLException;

import br.com.dextra.sqlstore.SQLStore;
import br.com.dextra.sqlstore.SQLStoreService;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class BoneCPJDBCStoreService implements SQLStoreService {

	private final BoneCP pool;

	public BoneCPJDBCStoreService() {
		super();
		BoneCPConfig config = new BoneCPConfig();
		config.setDefaultAutoCommit(true);
		config.setStatisticsEnabled(true);
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/continuous-delivery");
		config.setUsername("dextraining");
		config.setPassword("dextraining");
		config.setPartitionCount(2);
		config.setMaxConnectionsPerPartition(2);
		config.setMinConnectionsPerPartition(1);
		config.setDisableConnectionTracking(true);
		try {
			this.pool = new BoneCP(config);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public SQLStore get() {
		try {
			return new JDBCStore(pool.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
