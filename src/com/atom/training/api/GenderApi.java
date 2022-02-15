package com.atom.training.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.atom.training.beans.Gender;
import com.atom.training.utils.GenderUtils;
import com.atom.training.utils.MyUtils;

@Path("/genders")
public class GenderApi {
/**
 * get all genders
 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Gender> getAllGenders(){
		Connection conn = MyUtils.getStoredConnection();
		List<Gender> result = new ArrayList<>();
		try {
			result = GenderUtils.findAllGenders(conn);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			MyUtils.closeConnection(conn);
		}

		return result;
	}
}
