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
		// alimenter data avec des données fictives
		// Pour générer un matricule : `UUID.randomUUID().toString()`
		String matriculeHugues = UUID.randomUUID().toString();
		String matriculeJulie = UUID.randomUUID().toString();
		String matriculeCat = "043";
		Collegue hugues = new Collegue(matriculeHugues, "Rocheau", "Hugues", "Rocheau.Hugues@socite.com",
				LocalDate.of(1994, 12, 4),
				"https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi--62omdfhAhVNKBoKHcXFA6EQjRx6BAgBEAU&url=https%3A%2F%2Ftwitter.com%2Fbastienhugues&psig=AOvVaw1ejLYhf6dT4LI4nO8ONoK8&ust=1555592908226207");
		Collegue julie = new Collegue(matriculeJulie, "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");
		Collegue cat = new Collegue("043", "Rousseau", "Catherine", "cat.rousseau@societe.com",
				LocalDate.of(1964, 10, 3), "https://st.depositphotos.com/1814084/1749/i/950/depositphotos_17495843-stock-photo-catherine-dent.jpg");
		
		data.put(matriculeHugues, hugues);
		data.put(matriculeJulie, julie);
		data.put(matriculeCat, cat);

	}

	public List<String> rechercherParNom(String nomRecherche) {
		// retourner une liste de collègues dont le nom est fourni
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

		// Vérifier que le nom et les prenoms ont chacun au moins 2
		// caractères
		// Vérifier que l'email a au moins 3 caractères et contient `@`
		// Vérifier que la photoUrl commence bien par `http`
		// Vérifier que la date de naissance correspond à un age >= 18
		// Si une des règles ci-dessus n'est pas valide, générer une
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
			// générer un matricule pour ce collègue
			// (`UUID.randomUUID().toString()`)
			String matriculeNouveau = UUID.randomUUID().toString();

			// Création de ce nouveau collègue à ajouter avec son matricule
			nouveauCollegue = new Collegue(matriculeNouveau, collegueAAjouter.getNom(), collegueAAjouter.getPrenoms(),
					collegueAAjouter.getEmail(), collegueAAjouter.getDateDeNaissance(), collegueAAjouter.getPhotoUrl());

			// Sauvegarder le collègue
			data.put(matriculeNouveau, nouveauCollegue);
		}

		return nouveauCollegue;
	}

	public Collegue modifierDateNaissance(String matricule, LocalDate nouveauDateNaissance)
			throws CollegueInvalideException, CollegueNonTrouveException {
		Collegue collegueModif = rechercherParMatricule(matricule);

		// retourner une exception `CollegueNonTrouveException`
		// si le matricule ne correspond à aucun collègue
		if (collegueModif == null) {
			throw new CollegueNonTrouveException();
		}

		// Vérifier que la date de naissance correspond à un age >= 18
		// Si la règle ci-dessus n'est pas valide, générer une exception :
		// `CollegueInvalideException`. avec un message approprié.
		else if ((Period.between(nouveauDateNaissance, LocalDate.now()).getYears() < 18))
			throw new CollegueInvalideException();

		// Modifier le collègue
		collegueModif.setDateDeNaissance(nouveauDateNaissance);
		data.put(matricule, collegueModif);
		return collegueModif;

	}

	public Collegue modifierEmail(String matricule, String email)
			throws CollegueNonTrouveException, CollegueInvalideException {
		Collegue collegueModif = rechercherParMatricule(matricule);

		// retourner une exception `CollegueNonTrouveException`
		// si le matricule ne correspond à aucun collègue
		if (collegueModif == null) {
			throw new CollegueNonTrouveException();
		}

		// Vérifier que l'email a bien au moins 3 caractère et contient un
		// @
		// Si la règle ci-dessus n'est pas valide, générer une exception :
		// `CollegueInvalideException`. avec un message approprié.
		else if (!(email.length() > 3 && email.contains("@")))
			throw new CollegueInvalideException();

		// Modifier le collègue
		collegueModif.setEmail(email);
		data.put(matricule, collegueModif);
		return collegueModif;
	}

	public Collegue modifierPhotoUrl(String matricule, String photoUrl)
			throws CollegueNonTrouveException, CollegueInvalideException {
		Collegue collegueModif = rechercherParMatricule(matricule);

		// retourner une exception `CollegueNonTrouveException`
		// si le matricule ne correspond à aucun collègue
		if (collegueModif == null) {
			throw new CollegueNonTrouveException();
		}

		// Vérifier que l'URL conmmence bien par HTTP
		// Si la règle ci-dessus n'est pas valide, générer une exception :
		// `CollegueInvalideException`. avec un message approprié.
		else if (!photoUrl.startsWith("http"))
			throw new CollegueInvalideException();

		// Modifier le collègue
		collegueModif.setPhotoUrl(photoUrl);
		data.put(matricule, collegueModif);
		return collegueModif;
	}

}
