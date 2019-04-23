package dev.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;

@Service
public class CollegueService {
	@Autowired
	CollegueRepository pRepo;

	public List<Collegue> list() {
		return pRepo.findAll();
	}

	public void save(Collegue collegueAAjouter) throws CollegueInvalideException {
		if (collegueAAjouter.getNom().length() <= 2)
			throw new CollegueInvalideException();

		else if (collegueAAjouter.getPrenoms().length() < 2)
			throw new CollegueInvalideException();

		else if (!(collegueAAjouter.getEmail().length() > 3 && collegueAAjouter.getEmail().contains("@")))
			throw new CollegueInvalideException();

		else if (!collegueAAjouter.getPhotoUrl().startsWith("http"))
			throw new CollegueInvalideException();

		else if (Period.between(collegueAAjouter.getDateDeNaissance(), LocalDate.now()).getYears() < 18)
			throw new CollegueInvalideException();

		else {
			pRepo.save(collegueAAjouter);
		}
	}

	public List<String> rechercherParNom(String nomRecherche) {
		// retourner une liste de collègues dont le nom est fourni
		return pRepo.findAll().stream().filter(p -> p.getNom().equals(nomRecherche)).collect(Collectors.toList());
	}

	public Collegue rechercherParMatricule(String matriculeRecherche) throws CollegueNonTrouveException {
		Collegue collegueRecherche = pRepo.getOne(matriculeRecherche);
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
		if (collegueAAjouter.getNom().length() <= 2)
			throw new CollegueInvalideException();

		else if (collegueAAjouter.getPrenoms().length() < 2)
			throw new CollegueInvalideException();

		else if (!(collegueAAjouter.getEmail().length() > 3 && collegueAAjouter.getEmail().contains("@")))
			throw new CollegueInvalideException();

		else if (!collegueAAjouter.getPhotoUrl().startsWith("http"))
			throw new CollegueInvalideException();

		else if (Period.between(collegueAAjouter.getDateDeNaissance(), LocalDate.now()).getYears() < 18)
			throw new CollegueInvalideException();

		else {
			

			// Création de ce nouveau collègue à ajouter avec son matricule
			nouveauCollegue = new Collegue(collegueAAjouter.getNom(), collegueAAjouter.getPrenoms(),
					collegueAAjouter.getEmail(), collegueAAjouter.getDateDeNaissance(), collegueAAjouter.getPhotoUrl());

			// Sauvegarder le collègue
			pRepo.save(nouveauCollegue);
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
		pRepo.save(collegueModif);
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
		pRepo.save(collegueModif);
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
		pRepo.save(collegueModif);
		return collegueModif;
	}

	public CollegueRepository getpRepo() {
		return pRepo;
	}

	public void setpRepo(CollegueRepository pRepo) {
		this.pRepo = pRepo;
	}

}
