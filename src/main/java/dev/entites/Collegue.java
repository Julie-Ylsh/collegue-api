package dev.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COLLEGUE")
public class Collegue {

	public Collegue(String motDePasse, String nom, String prenoms, String email, LocalDate dateDeNaissance,
			String photoUrl, List<String> roles) {
		super();
		this.nom = nom;
		this.prenoms = prenoms;
		this.email = email;
		this.dateDeNaissance = dateDeNaissance;
		this.photoUrl = photoUrl;
		this.roles = roles;
		this.motDePasse = motDePasse;
	}

	public Collegue(Integer matricule, String nom, String prenoms, String email, LocalDate dateDeNaissance,
			String photoUrl, List<Commentaires> listeCommentaires) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenoms = prenoms;
		this.email = email;
		this.dateDeNaissance = dateDeNaissance;
		this.photoUrl = photoUrl;
		this.listeCommentaires = listeCommentaires;
	}

	public Collegue(Integer matricule, String nom, String prenoms, String email, LocalDate dateDeNaissance,
			String photoUrl) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenoms = prenoms;
		this.email = email;
		this.dateDeNaissance = dateDeNaissance;
		this.photoUrl = photoUrl;

	}

	public Collegue(String nom) {
		super();
		this.nom = nom;
	}

	public Collegue() {
		super();
	}

	@Id // obligatoire
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer matricule;

	@Column(name = "MOT_DE_PASSE")
	private String motDePasse;

	@Column(name = "NOM")
	private String nom;

	@Column(name = "PRENOMS")
	private String prenoms;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "DATEDENAISSANCE")
	private LocalDate dateDeNaissance;

	@Column(name = "PHOTOURL")
	private String photoUrl;

	@OneToMany(mappedBy = "collegue")
	private List<Commentaires> listeCommentaires;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

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

	public List<Commentaires> getListeCommentaires() {
		return listeCommentaires;
	}

	public void setListeCommentaires(List<Commentaires> listeCommentaires) {
		this.listeCommentaires = listeCommentaires;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
