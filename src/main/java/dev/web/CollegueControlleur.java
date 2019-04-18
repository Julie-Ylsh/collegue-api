package dev.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import dev.entites.Email;
import dev.service.CollegueInvalideException;
import dev.service.CollegueService;

@RestController

@RequestMapping("/collegue")
public class CollegueControlleur {
	CollegueService collegueService = new CollegueService();

	@GetMapping
	@ResponseBody 
	public List<String> AfficherNom(@RequestParam("nomClient") String nomClient) {

		return collegueService.rechercherParNom(nomClient);

	}

	@GetMapping(path="/{matricule}")
	@ResponseBody 
	public ResponseEntity<String> AfficherMatricule(@PathVariable String matricule) {
		
		try {
			Collegue collegueTouve = collegueService.rechercherParMatricule(matricule);
			return ResponseEntity.status(200).body(collegueTouve.getNom());
		} catch (CollegueNonTrouveException e) {
			return ResponseEntity.status(404).body("Collègue non trouvé");
			 
		}

	}
	
	@PostMapping
	@ResponseBody 
	public ResponseEntity<String> CreerCollegue(@RequestBody Collegue collegueAAjouter) {
		
		try{
		collegueService.ajouterUnCollegue(collegueAAjouter);
		return ResponseEntity.status(200).body("Collègue ajouté : " + collegueAAjouter.getNom() + " " + collegueAAjouter.getPrenoms());
		}
		
		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}
				
	}
	
	@PatchMapping(path="/{matricule}")
	public ResponseEntity<String> afficherModifierMail(@PathVariable String matricule, @RequestBody Email email) throws CollegueNonTrouveException {
		try {
			Collegue collegueAModifier = collegueService.rechercherParMatricule(matricule);
			collegueService.modifierEmail(matricule, email.getEmail());
			return ResponseEntity.status(200).body("Mail modifié pour " + collegueAModifier.getNom() + " " + collegueAModifier.getPrenoms() + ". Nouveau mail : " + collegueAModifier.getEmail());
		}
		
		catch (CollegueInvalideException e) {
			return ResponseEntity.status(404).body("Vous n'avez pas entré les bons paramètres");
		}
	}
}
