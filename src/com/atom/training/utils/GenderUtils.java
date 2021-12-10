package com.atom.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atom.training.beans.Gender;

public class GenderUtils {

	public static List<Gender> findAllGenders(Connection conn) throws SQLException {
		String sql = "SELECT * FROM MST_GENDER";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Gender> list = new ArrayList<Gender>();
		while (rs.next()) {
			Gender g = new Gender();
			g.setGenderId(rs.getInt("gender_id"));
			g.setGenderName(rs.getString("gender_name"));
			list.add(g);
		}
		return list;
	}

}
