package fr.iut.coding.web.rest;

import fr.iut.coding.Application;
import fr.iut.coding.domain.SeancePrive;
import fr.iut.coding.repository.SeancePriveRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeancePriveResource REST controller.
 *
 * @see SeancePriveResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SeancePriveResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_TITRE = "SAMPLE_TEXT";
    private static final String UPDATED_TITRE = "UPDATED_TEXT";
    private static final String DEFAULT_CATEGORIE = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORIE = "UPDATED_TEXT";
    private static final String DEFAULT_SUJET = "SAMPLE_TEXT";
    private static final String UPDATED_SUJET = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATEDEBUT = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATEDEBUT = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATEDEBUT_STR = dateTimeFormatter.print(DEFAULT_DATEDEBUT);

    private static final DateTime DEFAULT_DATEFIN = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATEFIN = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATEFIN_STR = dateTimeFormatter.print(DEFAULT_DATEFIN);

    private static final Integer DEFAULT_NBUTILISATEUR = 0;
    private static final Integer UPDATED_NBUTILISATEUR = 1;

    @Inject
    private SeancePriveRepository seancePriveRepository;

    private MockMvc restSeancePriveMockMvc;

    private SeancePrive seancePrive;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeancePriveResource seancePriveResource = new SeancePriveResource();
        ReflectionTestUtils.setField(seancePriveResource, "seancePriveRepository", seancePriveRepository);
        this.restSeancePriveMockMvc = MockMvcBuilders.standaloneSetup(seancePriveResource).build();
    }

    @Before
    public void initTest() {
        seancePrive = new SeancePrive();
        seancePrive.setTitre(DEFAULT_TITRE);
        seancePrive.setCategorie(DEFAULT_CATEGORIE);
        seancePrive.setSujet(DEFAULT_SUJET);
        seancePrive.setDatedebut(DEFAULT_DATEDEBUT);
        seancePrive.setDatefin(DEFAULT_DATEFIN);
        seancePrive.setNbutilisateur(DEFAULT_NBUTILISATEUR);
    }

    @Test
    @Transactional
    public void createSeancePrive() throws Exception {
        int databaseSizeBeforeCreate = seancePriveRepository.findAll().size();

        // Create the SeancePrive
        restSeancePriveMockMvc.perform(post("/api/seancePrives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(seancePrive)))
                .andExpect(status().isCreated());

        // Validate the SeancePrive in the database
        List<SeancePrive> seancePrives = seancePriveRepository.findAll();
        assertThat(seancePrives).hasSize(databaseSizeBeforeCreate + 1);
        SeancePrive testSeancePrive = seancePrives.get(seancePrives.size() - 1);
        assertThat(testSeancePrive.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testSeancePrive.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testSeancePrive.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testSeancePrive.getDatedebut().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATEDEBUT);
        assertThat(testSeancePrive.getDatefin().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATEFIN);
        assertThat(testSeancePrive.getNbutilisateur()).isEqualTo(DEFAULT_NBUTILISATEUR);
    }

    @Test
    @Transactional
    public void getAllSeancePrives() throws Exception {
        // Initialize the database
        seancePriveRepository.saveAndFlush(seancePrive);

        // Get all the seancePrives
        restSeancePriveMockMvc.perform(get("/api/seancePrives"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(seancePrive.getId().intValue())))
                .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
                .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
                .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
                .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT_STR)))
                .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN_STR)))
                .andExpect(jsonPath("$.[*].nbutilisateur").value(hasItem(DEFAULT_NBUTILISATEUR)));
    }

    @Test
    @Transactional
    public void getSeancePrive() throws Exception {
        // Initialize the database
        seancePriveRepository.saveAndFlush(seancePrive);

        // Get the seancePrive
        restSeancePriveMockMvc.perform(get("/api/seancePrives/{id}", seancePrive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(seancePrive.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.datedebut").value(DEFAULT_DATEDEBUT_STR))
            .andExpect(jsonPath("$.datefin").value(DEFAULT_DATEFIN_STR))
            .andExpect(jsonPath("$.nbutilisateur").value(DEFAULT_NBUTILISATEUR));
    }

    @Test
    @Transactional
    public void getNonExistingSeancePrive() throws Exception {
        // Get the seancePrive
        restSeancePriveMockMvc.perform(get("/api/seancePrives/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeancePrive() throws Exception {
        // Initialize the database
        seancePriveRepository.saveAndFlush(seancePrive);
		
		int databaseSizeBeforeUpdate = seancePriveRepository.findAll().size();

        // Update the seancePrive
        seancePrive.setTitre(UPDATED_TITRE);
        seancePrive.setCategorie(UPDATED_CATEGORIE);
        seancePrive.setSujet(UPDATED_SUJET);
        seancePrive.setDatedebut(UPDATED_DATEDEBUT);
        seancePrive.setDatefin(UPDATED_DATEFIN);
        seancePrive.setNbutilisateur(UPDATED_NBUTILISATEUR);
        restSeancePriveMockMvc.perform(put("/api/seancePrives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(seancePrive)))
                .andExpect(status().isOk());

        // Validate the SeancePrive in the database
        List<SeancePrive> seancePrives = seancePriveRepository.findAll();
        assertThat(seancePrives).hasSize(databaseSizeBeforeUpdate);
        SeancePrive testSeancePrive = seancePrives.get(seancePrives.size() - 1);
        assertThat(testSeancePrive.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testSeancePrive.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testSeancePrive.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testSeancePrive.getDatedebut().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATEDEBUT);
        assertThat(testSeancePrive.getDatefin().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATEFIN);
        assertThat(testSeancePrive.getNbutilisateur()).isEqualTo(UPDATED_NBUTILISATEUR);
    }

    @Test
    @Transactional
    public void deleteSeancePrive() throws Exception {
        // Initialize the database
        seancePriveRepository.saveAndFlush(seancePrive);
		
		int databaseSizeBeforeDelete = seancePriveRepository.findAll().size();

        // Get the seancePrive
        restSeancePriveMockMvc.perform(delete("/api/seancePrives/{id}", seancePrive.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SeancePrive> seancePrives = seancePriveRepository.findAll();
        assertThat(seancePrives).hasSize(databaseSizeBeforeDelete - 1);
    }
}
