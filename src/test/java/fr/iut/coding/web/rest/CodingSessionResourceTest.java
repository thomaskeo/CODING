package fr.iut.coding.web.rest;

import fr.iut.coding.Application;
import fr.iut.coding.domain.CodingSession;
import fr.iut.coding.repository.CodingSessionRepository;

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
 * Test class for the CodingSessionResource REST controller.
 *
 * @see CodingSessionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CodingSessionResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_THEME = "SAMPLE_TEXT";
    private static final String UPDATED_THEME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_SESSION_START = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SESSION_START = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SESSION_START_STR = dateTimeFormatter.print(DEFAULT_SESSION_START);

    private static final DateTime DEFAULT_SESSION_END = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SESSION_END = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SESSION_END_STR = dateTimeFormatter.print(DEFAULT_SESSION_END);

    @Inject
    private CodingSessionRepository codingSessionRepository;

    private MockMvc restCodingSessionMockMvc;

    private CodingSession codingSession;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CodingSessionResource codingSessionResource = new CodingSessionResource();
        ReflectionTestUtils.setField(codingSessionResource, "codingSessionRepository", codingSessionRepository);
        this.restCodingSessionMockMvc = MockMvcBuilders.standaloneSetup(codingSessionResource).build();
    }

    @Before
    public void initTest() {
        codingSession = new CodingSession();
        codingSession.setTheme(DEFAULT_THEME);
        codingSession.setSessionStart(DEFAULT_SESSION_START);
        codingSession.setSessionEnd(DEFAULT_SESSION_END);
    }

    @Test
    @Transactional
    public void createCodingSession() throws Exception {
        int databaseSizeBeforeCreate = codingSessionRepository.findAll().size();

        // Create the CodingSession
        restCodingSessionMockMvc.perform(post("/api/codingSessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(codingSession)))
                .andExpect(status().isCreated());

        // Validate the CodingSession in the database
        List<CodingSession> codingSessions = codingSessionRepository.findAll();
        assertThat(codingSessions).hasSize(databaseSizeBeforeCreate + 1);
        CodingSession testCodingSession = codingSessions.get(codingSessions.size() - 1);
        assertThat(testCodingSession.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testCodingSession.getSessionStart().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SESSION_START);
        assertThat(testCodingSession.getSessionEnd().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SESSION_END);
    }

    @Test
    @Transactional
    public void getAllCodingSessions() throws Exception {
        // Initialize the database
        codingSessionRepository.saveAndFlush(codingSession);

        // Get all the codingSessions
        restCodingSessionMockMvc.perform(get("/api/codingSessions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(codingSession.getId().intValue())))
                .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())))
                .andExpect(jsonPath("$.[*].sessionStart").value(hasItem(DEFAULT_SESSION_START_STR)))
                .andExpect(jsonPath("$.[*].sessionEnd").value(hasItem(DEFAULT_SESSION_END_STR)));
    }

    @Test
    @Transactional
    public void getCodingSession() throws Exception {
        // Initialize the database
        codingSessionRepository.saveAndFlush(codingSession);

        // Get the codingSession
        restCodingSessionMockMvc.perform(get("/api/codingSessions/{id}", codingSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(codingSession.getId().intValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()))
            .andExpect(jsonPath("$.sessionStart").value(DEFAULT_SESSION_START_STR))
            .andExpect(jsonPath("$.sessionEnd").value(DEFAULT_SESSION_END_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCodingSession() throws Exception {
        // Get the codingSession
        restCodingSessionMockMvc.perform(get("/api/codingSessions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodingSession() throws Exception {
        // Initialize the database
        codingSessionRepository.saveAndFlush(codingSession);
		
		int databaseSizeBeforeUpdate = codingSessionRepository.findAll().size();

        // Update the codingSession
        codingSession.setTheme(UPDATED_THEME);
        codingSession.setSessionStart(UPDATED_SESSION_START);
        codingSession.setSessionEnd(UPDATED_SESSION_END);
        restCodingSessionMockMvc.perform(put("/api/codingSessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(codingSession)))
                .andExpect(status().isOk());

        // Validate the CodingSession in the database
        List<CodingSession> codingSessions = codingSessionRepository.findAll();
        assertThat(codingSessions).hasSize(databaseSizeBeforeUpdate);
        CodingSession testCodingSession = codingSessions.get(codingSessions.size() - 1);
        assertThat(testCodingSession.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testCodingSession.getSessionStart().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SESSION_START);
        assertThat(testCodingSession.getSessionEnd().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SESSION_END);
    }

    @Test
    @Transactional
    public void deleteCodingSession() throws Exception {
        // Initialize the database
        codingSessionRepository.saveAndFlush(codingSession);
		
		int databaseSizeBeforeDelete = codingSessionRepository.findAll().size();

        // Get the codingSession
        restCodingSessionMockMvc.perform(delete("/api/codingSessions/{id}", codingSession.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CodingSession> codingSessions = codingSessionRepository.findAll();
        assertThat(codingSessions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
