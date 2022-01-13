package com.atom.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.atom.training.beans.Role;
import com.atom.training.beans.Statistic;
import com.atom.training.beans.Statistic2;

public class StatisticUtils {
	public static List<Statistic> getStatistics(Connection conn) throws SQLException {
		List<Statistic> result = new ArrayList<>();
		Map<Integer, Statistic> mapResult = new HashMap<>();
		List<Role> roles = RoleUtils.findAllRoles(conn);
		for (Role r : roles) {
			Statistic s = new Statistic();
			s.setAuthorityId(r.getAuthorityId());
			s.setAuthorityName(r.getAuthorityName());
			mapResult.put(r.getAuthorityId(), s);
		}
		mapResult.put(null, new Statistic());

		//性別で集計
		List<Statistic2> statisticArr = statisticByGender(conn, 0);
		assignResult(statisticArr, mapResult, "male");
		statisticArr = statisticByGender(conn, 1);
		assignResult(statisticArr, mapResult, "female");
		statisticArr = statisticByGender(conn, null);
		assignResult(statisticArr, mapResult, "unknownGender");

		//		年齢で集計
		statisticArr = statisticByAge(conn, true, null, 19);
		assignResult(statisticArr, mapResult, "smaller19");
		statisticArr = statisticByAge(conn, true, 20, null);
		assignResult(statisticArr, mapResult, "greater20");
		statisticArr = statisticByAge(conn, false, null, null);
		assignResult(statisticArr, mapResult, "unknownAge");

		System.out.println("---------");
		result = new ArrayList<>(mapResult.values());
		result = result.stream().map(x -> {
			if (x.getAuthorityId() == null) {
				x.setAuthorityId(-1);
				x.setAuthorityName("未登録");
			}
			return x;
		}).sorted((x1, x2) -> x2.getAuthorityId() - x1.getAuthorityId()).collect(Collectors.toList());

		return result;
	}

	public static void assignResult(List<Statistic2> arr, Map<Integer, Statistic> map, String type) {
		arr.forEach(x -> {
			Statistic s = map.get(x.getAuthorityId());
			if (type == "male") {
				s.setTotalMale(x.getTotal());
			} else if (type == "female") {
				s.setTotalFemale(x.getTotal());
			} else if (type == "unknownGender") {
				s.setTotalUnknown(x.getTotal());
			} else if (type == "smaller19") {
				s.setTotalAgeSmaller19(x.getTotal());
			} else if (type == "greater20") {
				s.setTotalAgeGreater20(x.getTotal());
			} else if (type == "unknownAge") {
				s.setTotalUnknownAge(x.getTotal());
			}
		});
	}

	public static List<Statistic2> statisticByGender(Connection conn, Integer genderId) throws SQLException {
		List<Statistic2> result = new ArrayList<>();
		String condition = genderId == null ? " where u.gender_id is null " : " where u.gender_id =? ";
		String sql = "select u.authority_id authorityId, authority_name roleName, \r\n" +
				"gender_name genderName,\r\n" +
				"count(*) total from mst_user u \r\n" +
				"left join mst_role r on u.authority_id = r.authority_id  \r\n" +
				"left join mst_gender g on u.gender_id = g.gender_id \r\n" + condition +
				"group by u.authority_id, authority_name, gender_name ORDER by roleName, genderName;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		if (genderId != null) {
			pstm.setInt(1, genderId);
		}
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			Statistic2 s = new Statistic2();
			Object authorityId =rs.getObject("authorityId");
			s.setAuthorityId(authorityId==null?null:(int) authorityId);
			s.setTotal(rs.getInt("total"));
			s.setAuthorityName(rs.getString("roleName"));
			result.add(s);
		}
		result.forEach(x-> System.out.println(x.toString()));


		return result;
	}

	public static List<Statistic2> statisticByAge(Connection conn, boolean filled, Integer ageFrom, Integer ageTo)
			throws SQLException {
		List<Statistic2> result = new ArrayList<>();
		String condition = "";
		if (!filled) {
			condition = " and u.age is null ";
		} else {
			if (ageFrom != null) {
				condition = " and u.age>=?";
			}
			if (ageTo != null) {
				condition = condition + " and u.age<=?";
			}
		}
		String sql = "select u.authority_id authorityId, authority_name roleName,\r\n" +
				"count(*) total from mst_user u \r\n" +
				"left join mst_role r on u.authority_id = r.authority_id  \r\n where 1=1 " +
				condition +
				"group by u.authority_id, authority_name ORDER by roleName;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		if (filled) {
			int i = 0;
			if (ageFrom != null) {
				pstm.setInt(++i, ageFrom);
			}
			if (ageTo != null) {
				pstm.setInt(++i, ageTo);
			}
		}

		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			Statistic2 s = new Statistic2();
			Object authorityId =rs.getObject("authorityId");
			s.setAuthorityId(authorityId==null?null:(int) authorityId);
			s.setTotal(rs.getInt("total"));
			result.add(s);
		}

		return result;
	}

}
