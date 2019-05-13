package dev.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.entites.CollegueSansCommentaire;
import dev.entites.InfosAuthentification;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;
import io.jsonwebtoken.Jwts;

@RestController

public class AuthentificationCtrl {
	@Value("${jwt.expires_in}")
	private Integer EXPIRES_IN;

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Value("${jwt.secret}")
	private String SECRET;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CollegueService collegueService;

	@PostMapping(value = "/login")
	public ResponseEntity setCookie(@RequestBody  InfosAuthentification authenticationRequest,
			HttpServletResponse response) {
		System.out.println("hello");
		// encapsulation des informations de connexion
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						authenticationRequest.getMatriculeCollegue(), authenticationRequest.getMotDePasse());

				// vérification de l'authentification
				// une exception de type `BadCredentialsException` en cas d'informations
				// non valides
				Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

				User user = (User) authentication.getPrincipal();

				String rolesList = user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));

				Map<String, Object> infosSupplementaireToken = new HashMap<>();
				infosSupplementaireToken.put("roles", rolesList);

				String jetonJWT = Jwts.builder().setSubject(user.getUsername()).addClaims(infosSupplementaireToken)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN * 1000))
						.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET).compact();

				Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
				authCookie.setHttpOnly(true);
				authCookie.setMaxAge(EXPIRES_IN * 1000);
				authCookie.setPath("/");
				response.addCookie(authCookie);

				return ResponseEntity.ok().build();

	}

	@GetMapping(path = "/me")
	@ResponseBody
	public ResponseEntity afficherCollegueCookie(HttpServletRequest req) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Collegue collegueTrouve;
		CollegueSansCommentaire collegueAAfficher;
		try {
			collegueTrouve = collegueService.rechercherParMatricule(Integer.parseInt(username));
			collegueAAfficher = new CollegueSansCommentaire(collegueTrouve.getMatricule(), collegueTrouve.getNom(),
					collegueTrouve.getPrenoms(), collegueTrouve.getEmail(), collegueTrouve.getDateDeNaissance(),
					collegueTrouve.getPhotoUrl(), collegueTrouve.getRoles());
			return ResponseEntity.status(200).body(collegueAAfficher);
		} catch (NumberFormatException | CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");
		}

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
