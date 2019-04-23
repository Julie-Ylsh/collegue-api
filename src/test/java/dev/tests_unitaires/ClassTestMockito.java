package dev.tests_unitaires;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dev.entites.Collegue;
import dev.exceptions.CollegueInvalideException;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;
import dev.service.CollegueService;

public class ClassTestMockito {
	Collegue collegueTest = new Collegue("43", "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
			LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg");

	CollegueRepository repoMock = Mockito.mock(CollegueRepository.class);

	@InjectMocks
	private CollegueService collegueService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		collegueService.setpRepo(repoMock);
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisNom() throws CollegueInvalideException {
		collegueTest.setNom("J");
		Mockito.when(collegueService.ajouterUnCollegue(collegueTest)).thenThrow(new CollegueInvalideException());

	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisPrenom() throws CollegueInvalideException {
		collegueTest.setPrenoms("J");
		Mockito.when(collegueService.ajouterUnCollegue(collegueTest)).thenThrow(new CollegueInvalideException());
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisEmailCourt() throws CollegueInvalideException {

		collegueTest.setEmail("a@a");
		Mockito.when(collegueService.ajouterUnCollegue(collegueTest)).thenThrow(new CollegueInvalideException());
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisEmailpasBon() throws CollegueInvalideException {

		collegueTest.setEmail("afuejgpa");
		Mockito.when(collegueService.ajouterUnCollegue(collegueTest)).thenThrow(new CollegueInvalideException());
	}

	@Test(expected = CollegueInvalideException.class)
	public void testMauvaisURLPhoto() throws CollegueInvalideException {

		collegueTest.setPhotoUrl("afuejgpa");
		Mockito.when(collegueService.ajouterUnCollegue(collegueTest)).thenThrow(new CollegueInvalideException());
	}

	@Test(expected = CollegueInvalideException.class)
	public void testDateNaissance() throws CollegueInvalideException {

		collegueTest.setDateDeNaissance(LocalDate.of(2004, 12, 4));
		Mockito.when(collegueService.ajouterUnCollegue(collegueTest)).thenThrow(new CollegueInvalideException());
	}

	@Test(expected = CollegueInvalideException.class)
	public void testModifierMauvaisEmailCourt() throws CollegueInvalideException, CollegueNonTrouveException {
		Mockito.when(repoMock.getOne("43")).thenReturn(new Collegue ("43", "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
			LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg"));
		collegueService.modifierEmail("43", "e@");
	}

	@Test(expected = CollegueInvalideException.class)
	public void testModifierMauvaisEmailpasBon() throws CollegueInvalideException, CollegueNonTrouveException {
		Mockito.when(repoMock.getOne("43")).thenReturn(new Collegue ("43", "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg"));
			collegueService.modifierEmail("43", "jaju");
	}

	@Test(expected = CollegueInvalideException.class)
	public void testModifierMauvaisURLPhoto() throws CollegueInvalideException, CollegueNonTrouveException {
		Mockito.when(repoMock.getOne("43")).thenReturn(new Collegue ("43", "Jeltsch", "Julie", "Jeltsch.julie@socite.com",
				LocalDate.of(1994, 12, 4), "https://www.francetvinfo.fr/image/75596evse-9d87/840/472/7205071.jpg"));
			collegueService.modifierPhotoUrl("43","mauvaisUrl");
	}
}
