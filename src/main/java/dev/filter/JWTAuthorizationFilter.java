package dev.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Configuration
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Value("${jwt.secret}")
	private String SECRET;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// Recherche du jeton par Cookie
		if (req.getCookies() != null) {
			Stream.of(req.getCookies()).filter(cookie -> cookie.getName().equals(TOKEN_COOKIE))
					.map(cookie -> cookie.getValue()).forEach(token -> {
						Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

						String username = body.getSubject();

						List<SimpleGrantedAuthority> roles = Stream.of(body.get("roles", String.class).split(","))
								.map(roleString -> new SimpleGrantedAuthority(roleString)).collect(Collectors.toList());

						Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, roles);
						SecurityContextHolder.getContext().setAuthentication(authentication);

					});
		}

		chain.doFilter(req, res);

	}

}
