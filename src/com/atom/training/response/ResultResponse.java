package com.atom.training.response;

import java.util.List;

import javax.ws.rs.core.Response;

public class ResultResponse {
	public static Response responseOk(Object data) {
		return Response.status(200)
				.entity(data)
				.build();
	}

	public static Response responseOk(List<Object> data) {
		return Response.status(200)
				.entity(data)
				.build();
	}

	public static Response responseError(Object message, Integer code) {
		return Response.status(code)
				.entity(message)
				.build();
	}

}
