package org.lyflexi.frameworktest.service;

import org.lyflexi.framework.jdbc.core.OldJdbcTemplate;
import org.lyflexi.frameworktest.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJdbcImpl extends OldJdbcTemplate {

	@Override
	protected Object doInStatement(ResultSet rs) {
		User rtnUser = null;
		
		try {
			if (rs.next()) {
				rtnUser = new User();
				rtnUser.setId(rs.getInt("id"));
				rtnUser.setName(rs.getString("name"));
				rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rtnUser;
	}

}
