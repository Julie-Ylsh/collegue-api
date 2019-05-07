package dev.entites;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTAIRES")
public class Commentaires {

	public Commentaires(String commentaire, Collegue collegue) {
		super();
		this.commentaire = commentaire;
		this.collegue = collegue;
	}

	public Commentaires() {

	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Id // obligatoire
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "COMMENTAIRE")
	private String commentaire;
	
	@Column(name="DATE")
	private LocalDateTime dateCommentaire = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "COLLEGUE_ASSOCIE")
	private Collegue collegue;

	public Collegue getCollegue() {
		return collegue;
	}

	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}

	public LocalDateTime getDateCommentaire() {
		return dateCommentaire;
	}

	public void setDateCommentaire(LocalDateTime dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}

}
