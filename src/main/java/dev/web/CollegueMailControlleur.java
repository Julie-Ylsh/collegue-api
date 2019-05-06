package dev.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Collegue;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RestController

@RequestMapping("/collegue/mail")
@CrossOrigin
public class CollegueMailControlleur {
	@Autowired
	private CollegueService collegueService;
	
	@GetMapping
	@ResponseBody 
	public List<Collegue> afficherMail(@RequestParam("mail") String mailCollegue) throws CollegueNonTrouveException {
		return collegueService.rechercherParMail(mailCollegue);

	}

}
