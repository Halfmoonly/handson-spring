package org.lyflexi.framework.batis;

import org.lyflexi.framework.jdbc.core.JdbcTemplate;
import org.lyflexi.framework.jdbc.core.PreparedStatementCallback;

public interface SqlSession {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
	Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback);
}
