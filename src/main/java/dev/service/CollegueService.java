package dev.service;

import java.time.LocalDate;
import java.time.Period;
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
		Collegue hugues = new Collegue(matriculeHugues, "Rocheau", "Hugues", "Rocheau.Hugues@socite.com",
				LocalDate.of(1994, 12, 4),
				"https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi--62omdfhAhVNKBoKHcXFA6EQjRx6BAgBEAU&url=https%3A%2F%2Ftwitter.com%2Fbastienhugues&psig=AOvVaw1ejLYhf6dT4LI4nO8ONoK8&ust=1555592908226207");
		Collegue julie = new Collegue(matriculeJulie, "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");
		data.put(matriculeHugues, hugues);
		data.put(matriculeJulie, julie);

	}

	public List<String> rechercherParNom(String nomRecherche) {
		// TODO retourner une liste de collègues dont le nom est fourni
		List<String> listeNoms = data.values().stream().filter(p -> p.getNom().equals(nomRecherche))
				.map(p -> p.getMatricule()).collect(Collectors.toList());
		return listeNoms;
	}

	public Collegue rechercherParMatricule(String matriculeRecherche) throws CollegueNonTrouveException {
		Collegue collegueRecherche = data.get(matriculeRecherche);
		if (collegueRecherche == null) {
			throw new CollegueNonTrouveException();
		}
		return collegueRecherche;
	}

	public Collegue ajouterUnCollegue(Collegue collegueAAjouter) throws CollegueInvalideException {
		Collegue nouveauCollegue = null;

		// TODO Vérifier que le nom et les prenoms ont chacun au moins 2
		// caractères
		// TODO Vérifier que l'email a au moins 3 caractères et contient `@`
		// TODO Vérifier que la photoUrl commence bien par `http`
		// TODO Vérifier que la date de naissance correspond à un age >= 18
		// TODO Si une des règles ci-dessus n'est pas valide, générer une
		// exception :
		// `CollegueInvalideException`.
		if (!(collegueAAjouter.getNom().length() > 2)) 
			throw new CollegueInvalideException();
		
		else if (collegueAAjouter.getPrenoms().length() < 2) 
			throw new CollegueInvalideException();
		
		else if (!(collegueAAjouter.getEmail().length() > 3 && collegueAAjouter.getEmail().contains("@")))
			throw new CollegueInvalideException();

		else if (!collegueAAjouter.getPhotoUrl().startsWith("http"))
			throw new CollegueInvalideException();

		else if (!(Period.between(collegueAAjouter.getDateDeNaissance(), LocalDate.now()).getYears() >= 18))
			throw new CollegueInvalideException();
		
		else {
			// TODO générer un matricule pour ce collègue
			// (`UUID.randomUUID().toString()`)
			String matriculeNouveau = UUID.randomUUID().toString();

			// Création de ce nouveau collègue à ajouter avec son matricule
			nouveauCollegue = new Collegue(matriculeNouveau, collegueAAjouter.getNom(), collegueAAjouter.getPrenoms(),
					collegueAAjouter.getEmail(), collegueAAjouter.getDateDeNaissance(), collegueAAjouter.getPhotoUrl());

			// TODO Sauvegarder le collègue
			data.put(matriculeNouveau, nouveauCollegue);
		}

		return nouveauCollegue;
	}
}
