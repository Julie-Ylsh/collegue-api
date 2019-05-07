package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Commentaires;

public interface CommentaireRepository extends JpaRepository<Commentaires, Integer>{
	
	public List<Commentaires> findByCollegueMatricule(Integer matricule); 

}
