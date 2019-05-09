package dev.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import dev.entites.Commentaires;
import dev.entites.CommentairesSansCollegue;
import dev.entites.PhotoUrl;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController
@CrossOrigin

@RequestMapping("/collegue/photo")
public class PhotoControlleur {
	@Autowired
	private CollegueService collegueService;

	@Secured("ROLE_ADMIN")
	@PatchMapping(path = "/{matricule}")
	public ResponseEntity<String> afficherModifierPhoto(@PathVariable Integer matricule, @RequestBody PhotoUrl photo)
			throws CollegueNonTrouveException {
		try {
			Collegue collegueAModifier = collegueService.rechercherParMatricule(matricule);
			collegueService.modifierPhotoUrl(matricule, photo.getPhotoUrl());
			return ResponseEntity.status(200).body("URL de la photo modifié pour " + collegueAModifier.getNom() + " "
					+ collegueAModifier.getPrenoms() + ". Nouvel Url: " + collegueAModifier.getPhotoUrl());
		}

		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}
	}

	// Renvoie un collègue entier d'après son matricule
	@GetMapping(path = "/found")
	@ResponseBody
	public CollegueSansCommentaire afficherCollegueParMatricule(@RequestParam("matricule") Integer matricule)
			throws CollegueNonTrouveException {
		Collegue collegueCommentaires = collegueService.rechercherParMatricule(matricule);
		return new CollegueSansCommentaire(collegueCommentaires.getMatricule(), collegueCommentaires.getNom(), collegueCommentaires.getPrenoms(), collegueCommentaires.getEmail(), collegueCommentaires.getDateDeNaissance(), collegueCommentaires.getPhotoUrl(), collegueCommentaires.getRoles());

	}

	@GetMapping
	@ResponseBody
	public List<CollegueSansCommentaire> afficherToutesPhoto() {

		return collegueService.list();

	}

	@PostMapping(path = "/{matricule}/commentaire")
	@ResponseBody
	public ResponseEntity<String> ajouterCommentaire(@PathVariable Integer matricule,
			@RequestBody Commentaires commentaire) {

		try {
			collegueService.ajouterCommentaire(matricule, commentaire.getCommentaire());
			return ResponseEntity.status(200).body("Commentaire ajouté pour le collègue");
		}

		catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}

	}

	@GetMapping(path = "/{matricule}/commentaires")
	@ResponseBody
	public List<CommentairesSansCollegue> afficherCommentaire(@PathVariable Integer matricule) {
		List<CommentairesSansCollegue> listeAffichee = collegueService.listeCommentairesParMatricule(matricule);
		return listeAffichee;

	}

}
