package dev.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.entites.CollegueSansCommentaire;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController

@RequestMapping("/collegue/mail")

public class CollegueMailControlleur {
	@Autowired
	private CollegueService collegueService;
	
	@GetMapping
	@ResponseBody 
	public List<CollegueSansCommentaire> afficherMail(@RequestParam("mail") String mailCollegue) throws CollegueNonTrouveException {
		List<Collegue> listeAvecCommentaires = collegueService.rechercherParMail(mailCollegue);
		return listeAvecCommentaires.stream().map(collegue -> new CollegueSansCommentaire (collegue.getMatricule(), collegue.getNom(), collegue.getPrenoms(), collegue.getEmail(), collegue.getDateDeNaissance(), collegue.getPhotoUrl(), collegue.getRoles())).collect(Collectors.toList());	
		

	}

}
