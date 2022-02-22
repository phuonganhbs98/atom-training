package com.atom.training.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import com.atom.training.entity.CurrentUser;
import com.atom.training.entity.User;
import com.atom.training.security.JWTokenHelper;

@WebFilter(filterName = "requestFilter", urlPatterns = { "/*" })
public class RequestFilter implements Filter {

	private static final String REALM = "pa";
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		if (((HttpServletRequest) request).getServletPath().equals("/api")) {
			String path = ((HttpServletRequest) request).getPathInfo();
			if (!path.equals("/users/login")) {
				String authorizationHeader = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);

				if (!isTokenBasedAuthentication(authorizationHeader)) {
					this.abortRequest(response, "ユーザがログインしませんでした");
					return;
				}

				//extract token
				String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
				System.out.println(">>>>>>token: " + token);
				try {
					if (JWTokenHelper.isTokenExpired(token)) {
						this.abortRequest(response, null);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					this.abortRequest(response, e.getMessage());
					return;
				}
				User user = JWTokenHelper.getUserFromToken(token);
				CurrentUser.getInstance().setUser(user);

			}
		}

		chain.doFilter(request, response);

	}

	private boolean isTokenBasedAuthentication(String authorizationHeader) {
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	private void abortRequest(ServletResponse res, String message) {
		System.out.println("..... dag abort request...");
		HttpServletResponse response = (HttpServletResponse) res;
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		try {
			response.getWriter().write(message);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}

}
