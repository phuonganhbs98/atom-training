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

import com.atom.training.entity.Gender;
import com.atom.training.response.ResultResponse;
import com.atom.training.utils.GenderUtils;
import com.atom.training.utils.MyUtils;

@Path("/genders")
public class GenderApi {
	/**
	 * get all genders
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllGenders() {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		List<Gender> result = new ArrayList<>();
		try {
			result = GenderUtils.findAllGenders(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (err != null) {
			return ResultResponse.responseError(err, 500);
		}

		return ResultResponse.responseOk(new GenericEntity<List<Gender>>(result) {});
	}

	/**
	 * get by id
	 */
	@GET
	@Path("/{genderId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getByGenderId(@PathParam("genderId") Integer genderId) {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		Gender g = null;
		try {
			g = GenderUtils.findByGenderId(conn, genderId);
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (err != null) {
			return ResultResponse.responseError(err, 500);
		}

		return ResultResponse.responseOk(g);
	}
}
