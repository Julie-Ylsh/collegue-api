package dev.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
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

	@PatchMapping(path = "/{matricule}")
	public ResponseEntity<String> afficherModifierPhoto(@PathVariable String matricule, @RequestBody PhotoUrl photo)
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

	@GetMapping(path = "/found")
	@ResponseBody
	public Collegue afficherPhoto(@RequestParam("matricule") String matricule) throws CollegueNonTrouveException {
		return collegueService.rechercherParMatricule(matricule);

	}

	@GetMapping
	@ResponseBody
	public List<Collegue> afficherToutesPhoto() {

		return collegueService.list();

	}

}
