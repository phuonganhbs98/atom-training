package com.atom.training.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.atom.training.beans.Role;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.RoleUtils;

@Path("/roles")
public class RoleApi {

	/**
	 * get all roles
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Role> getAllRole() {
		Connection conn = MyUtils.getStoredConnection();
		List<Role> result = new ArrayList<>();
		try {
			result = RoleUtils.findAllRoles(conn);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			MyUtils.closeConnection(conn);
		}

		return result;
	}
}
