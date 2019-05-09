package dev.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private CollegueRepository collegueRepository;

	public UserDetailsServiceImpl(CollegueRepository collegueRepository) {
		this.collegueRepository = collegueRepository;
	}

	// cette méthode va permettre à Spring Security d'avoir accès
	// aux informations d'un utilisateur (mot de passe, roles) à partir
	// d'un nom utilisateur
	//
	// L'interface UserDetails détaille le contrat attendu par Spring Security.

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Recherche d'utilisateur par nom utilisateur
		Collegue collegueTrouve = new Collegue();
		try {
			collegueTrouve = this.collegueRepository.findById(Integer.parseInt(username))
					.orElseThrow(CollegueNonTrouveException::new);
		} catch (NumberFormatException | CollegueNonTrouveException e) {
			e.printStackTrace();
		}

		// Création d'un objet User (implémentant UserDetails)
		return new User(collegueTrouve.getMatricule().toString(), collegueTrouve.getMotDePasse(),
				collegueTrouve.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	}
}
