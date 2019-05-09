package dev.repository;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.entites.Collegue;

@Component
public class StartupDataInit {

    @Autowired
    CollegueRepository collegueRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // La méthode init va être invoquée au démarrage de l'application.
    @EventListener(ContextRefreshedEvent.class)
    public void init() {

       // insérer des collègues en base de données /!\ A n'utiliser que si la base de données à été vidée
    	//PS : est-ce que ce genre de code commenté je peux le laisser ? 0:)
    	
    	 Collegue hugues = new Collegue(passwordEncoder.encode("pass1"), "Rocheau", "Hugues", "Rocheau.Hugues@socite.com",  	 
				LocalDate.of(1994, 12, 4),
				"https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi--62omdfhAhVNKBoKHcXFA6EQjRx6BAgBEAU&url=https%3A%2F%2Ftwitter.com%2Fbastienhugues&psig=AOvVaw1ejLYhf6dT4LI4nO8ONoK8&ust=1555592908226207", 
				Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
		Collegue julie = new Collegue(passwordEncoder.encode("pass2"), "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg", 
				Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
		Collegue cat = new Collegue(passwordEncoder.encode("pass3"), "Rousseau", "Catherine", "cat.rousseau@societe.com",
				LocalDate.of(1964, 10, 3), "https://st.depositphotos.com/1814084/1749/i/950/depositphotos_17495843-stock-photo-catherine-dent.jpg", 
				Arrays.asList("ROLE_USER"));
		
			
		collegueRepo.save(hugues);
		collegueRepo.save(julie);
		collegueRepo.save(cat);
		

    }
}
