package fr.iut.coding.web.rest;

import fr.iut.coding.Application;
import fr.iut.coding.domain.Etat_session;
import fr.iut.coding.repository.Etat_sessionRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Etat_sessionResource REST controller.
 *
 * @see Etat_sessionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Etat_sessionResourceTest {

    private static final String DEFAULT_LIBELLE = "SAMPLE_TEXT";
    private static final String UPDATED_LIBELLE = "UPDATED_TEXT";

    @Inject
    private Etat_sessionRepository etat_sessionRepository;

    private MockMvc restEtat_sessionMockMvc;

    private Etat_session etat_session;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Etat_sessionResource etat_sessionResource = new Etat_sessionResource();
        ReflectionTestUtils.setField(etat_sessionResource, "etat_sessionRepository", etat_sessionRepository);
        this.restEtat_sessionMockMvc = MockMvcBuilders.standaloneSetup(etat_sessionResource).build();
    }

    @Before
    public void initTest() {
        etat_session = new Etat_session();
        etat_session.setLibelle(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createEtat_session() throws Exception {
        int databaseSizeBeforeCreate = etat_sessionRepository.findAll().size();

        // Create the Etat_session
        restEtat_sessionMockMvc.perform(post("/api/etat_sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etat_session)))
                .andExpect(status().isCreated());

        // Validate the Etat_session in the database
        List<Etat_session> etat_sessions = etat_sessionRepository.findAll();
        assertThat(etat_sessions).hasSize(databaseSizeBeforeCreate + 1);
        Etat_session testEtat_session = etat_sessions.get(etat_sessions.size() - 1);
        assertThat(testEtat_session.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllEtat_sessions() throws Exception {
        // Initialize the database
        etat_sessionRepository.saveAndFlush(etat_session);

        // Get all the etat_sessions
        restEtat_sessionMockMvc.perform(get("/api/etat_sessions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(etat_session.getId().intValue())))
                .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getEtat_session() throws Exception {
        // Initialize the database
        etat_sessionRepository.saveAndFlush(etat_session);

        // Get the etat_session
        restEtat_sessionMockMvc.perform(get("/api/etat_sessions/{id}", etat_session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(etat_session.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtat_session() throws Exception {
        // Get the etat_session
        restEtat_sessionMockMvc.perform(get("/api/etat_sessions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtat_session() throws Exception {
        // Initialize the database
        etat_sessionRepository.saveAndFlush(etat_session);
		
		int databaseSizeBeforeUpdate = etat_sessionRepository.findAll().size();

        // Update the etat_session
        etat_session.setLibelle(UPDATED_LIBELLE);
        restEtat_sessionMockMvc.perform(put("/api/etat_sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etat_session)))
                .andExpect(status().isOk());

        // Validate the Etat_session in the database
        List<Etat_session> etat_sessions = etat_sessionRepository.findAll();
        assertThat(etat_sessions).hasSize(databaseSizeBeforeUpdate);
        Etat_session testEtat_session = etat_sessions.get(etat_sessions.size() - 1);
        assertThat(testEtat_session.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void deleteEtat_session() throws Exception {
        // Initialize the database
        etat_sessionRepository.saveAndFlush(etat_session);
		
		int databaseSizeBeforeDelete = etat_sessionRepository.findAll().size();

        // Get the etat_session
        restEtat_sessionMockMvc.perform(delete("/api/etat_sessions/{id}", etat_session.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Etat_session> etat_sessions = etat_sessionRepository.findAll();
        assertThat(etat_sessions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
