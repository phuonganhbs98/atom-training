package com.atom.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atom.training.beans.Role;

public class RoleUtils {
	public static List<Role> findAllRoles(Connection conn) throws SQLException {
		String sql = "SELECT * FROM MST_ROLE";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Role> list = new ArrayList<Role>();
		while (rs.next()) {
			Role r = new Role();
			r.setAuthorityId(rs.getInt("authority_id"));
			r.setAuthorityName(rs.getString("authority_name"));
			list.add(r);
		}
		return list;
	}
}
