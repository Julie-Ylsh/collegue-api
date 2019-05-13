package dev.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.entites.CollegueSansCommentaire;
import dev.entites.Email;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController

@RequestMapping("/collegue")
@CrossOrigin
public class CollegueControlleur {

	@Autowired
	private CollegueService collegueService;

	@GetMapping
	@ResponseBody
	public List<CollegueSansCommentaire> afficherNom(@RequestParam("nomClient") String nomClient) {
		List<Collegue> listeDeNoms = collegueService.rechercherParNom(nomClient);
		return listeDeNoms.stream()
				.map(collegue -> new CollegueSansCommentaire(collegue.getMatricule(), collegue.getNom(),
						collegue.getPrenoms(), collegue.getEmail(), collegue.getDateDeNaissance(),
						collegue.getPhotoUrl(), collegue.getRoles()))
				.collect(Collectors.toList());

	}

	// Renvoie un collègue entier (avec mot de passe) d'après son matricule
	@GetMapping(path = "/found")
	@ResponseBody
	public CollegueSansCommentaire afficherCollegueParMatricule(@RequestParam("matricule") Integer matricule)
			throws CollegueNonTrouveException {
		Collegue collegueCommentaires = collegueService.rechercherParMatricule(matricule);
		return new CollegueSansCommentaire(collegueCommentaires.getMotDePasse(), collegueCommentaires.getMatricule(), collegueCommentaires.getNom(),
				collegueCommentaires.getPrenoms(), collegueCommentaires.getEmail(),
				collegueCommentaires.getDateDeNaissance(), collegueCommentaires.getPhotoUrl(),
				collegueCommentaires.getRoles());

	}

	@GetMapping(path = "/{matricule}")
	@ResponseBody
	public ResponseEntity<String> afficherMatricule(@PathVariable Integer matricule) {

		try {
			Collegue collegueTouve = collegueService.rechercherParMatricule(matricule);
			return ResponseEntity.status(200).body(collegueTouve.getNom());
		} catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collègue non trouvé");

		}

	}

	@Secured("ROLE_ADMIN")
	@PostMapping
	@ResponseBody
	public ResponseEntity<String> creerCollegue(@RequestBody Collegue collegueAAjouter) {

		try {
			collegueService.ajouterUnCollegue(collegueAAjouter);
			return ResponseEntity.status(200)
					.body("Collègue ajouté : " + collegueAAjouter.getNom() + " " + collegueAAjouter.getPrenoms());
		}

		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}

	}

	@Secured("ROLE_ADMIN")
	@PatchMapping(path = "/{matricule}")
	public ResponseEntity<String> afficherModifierMail(@PathVariable Integer matricule, @RequestBody Email email)
			throws CollegueNonTrouveException {
		try {
			Collegue collegueAModifier = collegueService.rechercherParMatricule(matricule);
			collegueService.modifierEmail(matricule, email.getEmail());
			return ResponseEntity.status(200).body("Mail modifié pour " + collegueAModifier.getNom() + " "
					+ collegueAModifier.getPrenoms() + ". Nouveau mail : " + collegueAModifier.getEmail());
		}

		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}
	}

}
