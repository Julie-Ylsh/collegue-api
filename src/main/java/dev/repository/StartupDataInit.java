package dev.repository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.entites.Collegue;

@Component
public class StartupDataInit {

    @Autowired
    CollegueRepository collegueRepo;


    // La méthode init va être invoquée au démarrage de l'application.
    @EventListener(ContextRefreshedEvent.class)
    public void init() {

       // insérer des collègues en base de données
    	Collegue hugues = new Collegue("Rocheau", "Hugues", "Rocheau.Hugues@socite.com",
				LocalDate.of(1994, 12, 4),
				"https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi--62omdfhAhVNKBoKHcXFA6EQjRx6BAgBEAU&url=https%3A%2F%2Ftwitter.com%2Fbastienhugues&psig=AOvVaw1ejLYhf6dT4LI4nO8ONoK8&ust=1555592908226207");
		Collegue julie = new Collegue("Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");
		Collegue cat = new Collegue("Rousseau", "Catherine", "cat.rousseau@societe.com",
				LocalDate.of(1964, 10, 3), "https://st.depositphotos.com/1814084/1749/i/950/depositphotos_17495843-stock-photo-catherine-dent.jpg");
		
		collegueRepo.save(hugues);
		collegueRepo.save(julie);
		collegueRepo.save(cat);

    }
}
