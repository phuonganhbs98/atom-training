package com.atom.training.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atom.training.entity.Role;
import com.atom.training.response.ResultResponse;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.RoleUtils;

@Path("/roles")
public class RoleApi {

	/**
	 * get all roles
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllRole() {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		List<Role> result = new ArrayList<>();
		try {
			result = RoleUtils.findAllRoles(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (err != null) {
			return ResultResponse.responseError(err, 500);
		}

		return ResultResponse.responseOk(new GenericEntity<List<Role>>(result) {});
	}


	/**
	 * get role by id
	 */
	@GET
	@Path("/{authorityId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRoleById(@PathParam("authorityId") Integer authorityId) {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		Role r = null;
		try {
			r = RoleUtils.findByAuthorityId(conn, authorityId);
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (err != null) {
			return ResultResponse.responseError(err, 500);
		}

		return ResultResponse.responseOk(r);
	}

}
