package dev.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.entites.CollegueSansCommentaire;
import dev.entites.Commentaires;
import dev.entites.CommentairesSansCollegue;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;
import dev.repository.CommentaireRepository;

@Service
public class CollegueService {
	@Autowired
	CollegueRepository cRepo;
	
	@Autowired
	CommentaireRepository cmRepo;
	
	
	public List<CollegueSansCommentaire> list() {
		return cRepo.findAll().stream().map(collegue -> new CollegueSansCommentaire (collegue.getMatricule(), collegue.getNom(), collegue.getPrenoms(), collegue.getEmail(), collegue.getDateDeNaissance(), collegue.getPhotoUrl())).collect(Collectors.toList());
	}
	
	public List<CommentairesSansCollegue> listeCommentairesParMatricule(Integer matricule){
		return cmRepo.findByCollegueMatricule(matricule).stream().map(commentaire -> new CommentairesSansCollegue(commentaire.getCommentaire(), commentaire.getDateCommentaire())).collect(Collectors.toList());			
		
	}

	public void save(Collegue collegueAAjouter) throws CollegueInvalideException {
		if (collegueAAjouter.getNom().length() < 2)
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
			cRepo.save(collegueAAjouter);
		}
	}

	public List<Collegue> rechercherParNom(String nomRecherche) {
		// retourner une liste de collègues dont le nom est fourni
		return cRepo.findAll().stream().filter(p -> p.getNom().equals(nomRecherche)).collect(Collectors.toList());
	}

	public Collegue rechercherParMatricule(Integer matriculeRecherche) throws CollegueNonTrouveException {
		return cRepo.findById(matriculeRecherche).orElseThrow(CollegueNonTrouveException::new); 
		
	}

	public List<Collegue> rechercherParMail(String mailRecherche) throws CollegueNonTrouveException {
		// retourner une liste de collègues dont le nom est fourni
		return cRepo.findAll().stream().filter(p -> p.getEmail().equals(mailRecherche)).collect(Collectors.toList());
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
		if (collegueAAjouter.getNom().length() < 2)
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
			cRepo.save(nouveauCollegue);
		}

		return nouveauCollegue;
	}

	public Collegue modifierDateNaissance(Integer matricule, LocalDate nouveauDateNaissance)
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
		cRepo.save(collegueModif);
		return collegueModif;

	}

	public Collegue modifierEmail(Integer matricule, String email)
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
		cRepo.save(collegueModif);
		return collegueModif;
	}

	public Collegue modifierPhotoUrl(Integer matricule, String photoUrl)
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
		cRepo.save(collegueModif);
		return collegueModif;
	}
	
	public void ajouterCommentaire(Integer matricule, String commentaire) throws CollegueNonTrouveException {
		Collegue collegueTrouve= rechercherParMatricule(matricule);
		if (collegueTrouve == null) {
			throw new CollegueNonTrouveException();
		}
		Commentaires nouveauCommentaire = new Commentaires(commentaire, collegueTrouve);
		collegueTrouve.getListeCommentaires().add(nouveauCommentaire);
		cRepo.save(collegueTrouve);
		cmRepo.save(nouveauCommentaire);
		
		
		}

	public CollegueRepository getpRepo() {
		return cRepo;
	}

	public void setpRepo(CollegueRepository pRepo) {
		this.cRepo = pRepo;
	}

}
