package dev.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CollegueSansCommentaire {

	// Sans matricule ni mot de passe
	public CollegueSansCommentaire(String nom, String prenoms, String email, LocalDate dateDeNaissance, String photoUrl,
			List<String> roles) {
		super();
		this.nom = nom;
		this.prenoms = prenoms;
		this.email = email;
		this.dateDeNaissance = dateDeNaissance;
		this.photoUrl = photoUrl;
		this.roles = roles;
	}

	// Avec matricule sans mot de passe
	public CollegueSansCommentaire(Integer matricule, String nom, String prenoms, String email,
			LocalDate dateDeNaissance, String photoUrl, List<String> roles) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenoms = prenoms;
		this.email = email;
		this.dateDeNaissance = dateDeNaissance;
		this.photoUrl = photoUrl;
		this.roles = roles;
	}

	// Avec matricule et mot de passe
	public CollegueSansCommentaire(String motDePasse, Integer matricule, String nom, String prenoms, String email,
			LocalDate dateDeNaissance, String photoUrl, List<String> roles) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenoms = prenoms;
		this.email = email;
		this.dateDeNaissance = dateDeNaissance;
		this.photoUrl = photoUrl;
		this.roles = roles;
		this.motDePasse = motDePasse;
	}

	// Pourl'inscription, avec matricule, mot de passe et photo
	public CollegueSansCommentaire(String motDePasse, Integer matricule, String photoUrl) {
		super();
		this.matricule = matricule;
		this.photoUrl = photoUrl;
		this.motDePasse = motDePasse;
	}

	public CollegueSansCommentaire(String nom) {
		super();
		this.nom = nom;
	}

	public CollegueSansCommentaire() {
		super();
	}

	private Integer matricule;
	private String nom;
	private String prenoms;
	private String email;
	private LocalDate dateDeNaissance;
	private String photoUrl;
	private List<String> roles = new ArrayList<>();
	private String motDePasse;

	public Integer getMatricule() {
		return matricule;
	}

	public void setMatricule(Integer matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

}
