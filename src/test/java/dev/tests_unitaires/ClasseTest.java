package dev.tests_unitaires;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Collegue;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.CollegueService;

@RunWith(SpringRunner.class )
@SpringBootTest
public class ClasseTest {
	@Autowired
	private CollegueService collegueService;
	
	Integer matricule = (int) Math.random();
	Collegue collegueTest = new Collegue(matricule, "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
			LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");

	

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisNom() throws CollegueInvalideException {

		collegueTest.setNom("J");
		collegueService.ajouterUnCollegue(collegueTest);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisPrenom() throws CollegueInvalideException {

		collegueTest.setPrenoms("J");
		collegueService.ajouterUnCollegue(collegueTest);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisEmailCourt() throws CollegueInvalideException {

		collegueTest.setEmail("a@a");
		collegueService.ajouterUnCollegue(collegueTest);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisEmailpasBon() throws CollegueInvalideException {

		collegueTest.setEmail("afuejgpa");
		collegueService.ajouterUnCollegue(collegueTest);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisURLPhoto() throws CollegueInvalideException {

		collegueTest.setPhotoUrl("afuejgpa");
		collegueService.ajouterUnCollegue(collegueTest);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testDateNaissance() throws CollegueInvalideException {

		collegueTest.setDateDeNaissance(LocalDate.of(2004, 12, 4));
		collegueService.ajouterUnCollegue(collegueTest);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testModifierMauvaisEmailCourt() throws CollegueInvalideException, CollegueNonTrouveException {
		collegueService.modifierEmail(43, "j@");
	}

	@Test(expected = CollegueInvalideException.class)
	public void testModifierMauvaisEmailpasBon() throws CollegueInvalideException, CollegueNonTrouveException {
		collegueService.modifierEmail(43, "jaju");
	}

	@Test(expected = CollegueInvalideException.class)
	public void testModifierMauvaisURLPhoto() throws CollegueInvalideException, CollegueNonTrouveException {

		collegueService.modifierPhotoUrl(43, "photoUrl");
	}

}
