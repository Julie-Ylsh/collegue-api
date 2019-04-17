package dev.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.service.CollegueService;

@RestController

@RequestMapping("/collegue")
public class CollegueControlleur {
	CollegueService collegueService = new CollegueService();
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody // parser l'objet Client
    public List<String> AfficherNom(@RequestParam ("nomClient") String nomClient) {

		return collegueService.rechercherParNom(nomClient);
		
         	
    }

}
