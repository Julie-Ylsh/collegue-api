package dev.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartupDataInit {

    @Autowired
    CollegueRepository collegueRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // La méthode init va être invoquée au démarrage de l'application.
    @EventListener(ContextRefreshedEvent.class)
    public void init() {

       /** insérer des collègues en base de données /!\ A n'utiliser que si la base de données à été vidée
    	//PS : est-ce que ce genre de code commenté je peux le laisser ? 0:)
    	
    	 Collegue hugues = new Collegue(passwordEncoder.encode("pass1"), "Rocheau", "Hugues", "Rocheau.Hugues@socite.com",  	 
				LocalDate.of(1994, 12, 4),
				"https://miro.medium.com/max/722/0*vwtmE6kZFO0rIq9o.", 
				Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
		Collegue julie = new Collegue(passwordEncoder.encode("pass2"), "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fcdn-image.foodandwine.com%2Fsites%2Fdefault%2Ffiles%2F.%2Ffwx-partner-instyle-miss-piggy-main.jpg&w=450&c=sc&poi=face&q=85", 
				Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
		Collegue cat = new Collegue(passwordEncoder.encode("pass3"), "Rousseau", "Catherine", "cat.rousseau@societe.com",
				LocalDate.of(1964, 10, 3), "https://toutpourecrirecom.files.wordpress.com/2019/01/kermit-frog.jpg", 
				Arrays.asList("ROLE_USER"));
		
			
		collegueRepo.save(hugues);
		collegueRepo.save(julie);
		collegueRepo.save(cat);
		*/

    }
}
