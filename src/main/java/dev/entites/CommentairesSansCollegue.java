package dev.entites;

import java.time.LocalDateTime;

public class CommentairesSansCollegue {

	public CommentairesSansCollegue(String commentaire,LocalDateTime dateCommentaire) {
		super();
		this.commentaire = commentaire;
		this.dateCommentaire = dateCommentaire;
	}

	public CommentairesSansCollegue() {

	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	private String commentaire;
	private LocalDateTime dateCommentaire = LocalDateTime.now();
	
	
	public LocalDateTime getDateCommentaire() {
		return dateCommentaire;
	}

	public void setDateCommentaire(LocalDateTime dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}

}
