package dev.tests_unitaires;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Test;

import dev.entites.Collegue;
import dev.service.CollegueInvalideException;
import dev.service.CollegueService;

public class ClasseTest {
	String matricule = UUID.randomUUID().toString();
	Collegue collegueTest = new Collegue(matricule, "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
			LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisNom() throws CollegueInvalideException {
		
		CollegueService collegueService = new CollegueService();
		collegueTest.setNom("J");
		collegueService.ajouterUnCollegue(collegueTest);
	}
	
	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisPrenom() throws CollegueInvalideException {
		
		CollegueService collegueService = new CollegueService();
		collegueTest.setPrenoms("J");
		collegueService.ajouterUnCollegue(collegueTest);
	}
	
	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisEmailCourt() throws CollegueInvalideException {
		
		CollegueService collegueService = new CollegueService();
		collegueTest.setEmail("a@a");
		collegueService.ajouterUnCollegue(collegueTest);
	}
	
	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisEmailpasBon() throws CollegueInvalideException {
		
		CollegueService collegueService = new CollegueService();
		collegueTest.setEmail("afuejgpa");
		collegueService.ajouterUnCollegue(collegueTest);
	}
	
	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisURLPhoto() throws CollegueInvalideException {
		
		CollegueService collegueService = new CollegueService();
		collegueTest.setPhotoUrl("afuejgpa");
		collegueService.ajouterUnCollegue(collegueTest);
	}
	
	@Test(expected = CollegueInvalideException.class)
	public void testDateNaissance() throws CollegueInvalideException {
		
		CollegueService collegueService = new CollegueService();
		collegueTest.setDateDeNaissance(LocalDate.of(2004, 12, 4));
		collegueService.ajouterUnCollegue(collegueTest);
	}

}
