package dev.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.entites.Collegue;
import dev.web.CollegueNonTrouveException;

public class CollegueService {
	private Map<String, Collegue> data = new HashMap<>();

	public CollegueService() {
		// TODO alimenter data avec des données fictives
		// Pour générer un matricule : `UUID.randomUUID().toString()`
		String matriculeHugues = UUID.randomUUID().toString();
		String matriculeJulie = UUID.randomUUID().toString();
		Collegue hugues = new Collegue(matriculeHugues, "Rocheau", "Hugues", "Rocheau.Hugues@socite.com", "04/12/1994",
				"https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi--62omdfhAhVNKBoKHcXFA6EQjRx6BAgBEAU&url=https%3A%2F%2Ftwitter.com%2Fbastienhugues&psig=AOvVaw1ejLYhf6dT4LI4nO8ONoK8&ust=1555592908226207");
		Collegue julie = new Collegue(matriculeJulie, "Jeltsch", "Julie", "Jeltsch.julie@socite.com", "04/12/1994",
				"https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");
		data.put(matriculeHugues, hugues);
		data.put(matriculeJulie, julie);

	}

	public List<String> rechercherParNom(String nomRecherche) {
		// TODO retourner une liste de collègues dont le nom est fourni
		List<String> listeNoms = data.values().stream().filter(p -> p.getNom().equals(nomRecherche)).map(p -> p.getMatricule()).collect(Collectors.toList());
		return listeNoms;
	}
	
	public Collegue rechercherParMatricule(String matriculeRecherche) throws CollegueNonTrouveException {
		Collegue collegueRecherche = data.get(matriculeRecherche);
		if (collegueRecherche == null)
		{
			throw new CollegueNonTrouveException ();
		}
		return collegueRecherche;
	}
}
