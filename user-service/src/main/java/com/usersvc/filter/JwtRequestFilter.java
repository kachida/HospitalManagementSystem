package com.usersvc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.usersvc.security.JwtUtil;
import com.usersvc.service.MyUserDetailService;


// TODO: Auto-generated Javadoc
/**
 * The Class JwtRequestFilter.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	/** The user details service. */
	private final MyUserDetailService userDetailsService;
	
	/** The jwt util. */
	private final JwtUtil jwtUtil;

	/**
	 * Instantiates a new jwt request filter.
	 *
	 * @param userDetailsService the user details service
	 * @param jwtUtil the jwt util
	 */
	public JwtRequestFilter(MyUserDetailService userDetailsService, JwtUtil jwtUtil) {

		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;

	}

	/**
	 * Do filter internal.
	 *
	 * @param request the request
	 * @param response the response
	 * @param filterChain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
		}

		filterChain.doFilter(request, response);

	}

}
