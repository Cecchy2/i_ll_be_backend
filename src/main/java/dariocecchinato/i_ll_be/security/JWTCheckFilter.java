package dariocecchinato.i_ll_be.security;


import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.exceptions.UnauthorizedException;
import dariocecchinato.i_ll_be.services.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {
	@Autowired
	private JWTTools jwtTools;
	@Autowired
	private UtentiService utentiService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore inserisci correttamente il token nell'Authorization Header");

		String accessToken = authHeader.substring(7);

		jwtTools.verifyToken(accessToken);

		String id = jwtTools.extractIdFromToken(accessToken);
		Utente currentUtente = this.utentiService.findUtenteById(UUID.fromString(id));


		Authentication authentication = new UsernamePasswordAuthenticationToken(currentUtente, null, currentUtente.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().match("/authorization/**", request.getServletPath());
	}
}
