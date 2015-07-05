package fr.iut.coding.web.rest;

import fr.iut.coding.Application;
import fr.iut.coding.domain.AjouterVideo;
import fr.iut.coding.repository.AjouterVideoRepository;

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
 * Test class for the AjouterVideoResource REST controller.
 *
 * @see AjouterVideoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AjouterVideoResourceTest {

    private static final String DEFAULT_TITRE = "SAMPLE_TEXT";
    private static final String UPDATED_TITRE = "UPDATED_TEXT";
    private static final String DEFAULT_CATEGORIE = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORIE = "UPDATED_TEXT";
    private static final String DEFAULT_SUJET = "SAMPLE_TEXT";
    private static final String UPDATED_SUJET = "UPDATED_TEXT";
    private static final String DEFAULT_URL = "SAMPLE_TEXT";
    private static final String UPDATED_URL = "UPDATED_TEXT";

    @Inject
    private AjouterVideoRepository ajouterVideoRepository;

    private MockMvc restAjouterVideoMockMvc;

    private AjouterVideo ajouterVideo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AjouterVideoResource ajouterVideoResource = new AjouterVideoResource();
        ReflectionTestUtils.setField(ajouterVideoResource, "ajouterVideoRepository", ajouterVideoRepository);
        this.restAjouterVideoMockMvc = MockMvcBuilders.standaloneSetup(ajouterVideoResource).build();
    }

    @Before
    public void initTest() {
        ajouterVideo = new AjouterVideo();
        ajouterVideo.setTitre(DEFAULT_TITRE);
        ajouterVideo.setCategorie(DEFAULT_CATEGORIE);
        ajouterVideo.setSujet(DEFAULT_SUJET);
        ajouterVideo.setUrl(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createAjouterVideo() throws Exception {
        int databaseSizeBeforeCreate = ajouterVideoRepository.findAll().size();

        // Create the AjouterVideo
        restAjouterVideoMockMvc.perform(post("/api/ajouterVideos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ajouterVideo)))
                .andExpect(status().isCreated());

        // Validate the AjouterVideo in the database
        List<AjouterVideo> ajouterVideos = ajouterVideoRepository.findAll();
        assertThat(ajouterVideos).hasSize(databaseSizeBeforeCreate + 1);
        AjouterVideo testAjouterVideo = ajouterVideos.get(ajouterVideos.size() - 1);
        assertThat(testAjouterVideo.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testAjouterVideo.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testAjouterVideo.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testAjouterVideo.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void getAllAjouterVideos() throws Exception {
        // Initialize the database
        ajouterVideoRepository.saveAndFlush(ajouterVideo);

        // Get all the ajouterVideos
        restAjouterVideoMockMvc.perform(get("/api/ajouterVideos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ajouterVideo.getId().intValue())))
                .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
                .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
                .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getAjouterVideo() throws Exception {
        // Initialize the database
        ajouterVideoRepository.saveAndFlush(ajouterVideo);

        // Get the ajouterVideo
        restAjouterVideoMockMvc.perform(get("/api/ajouterVideos/{id}", ajouterVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ajouterVideo.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAjouterVideo() throws Exception {
        // Get the ajouterVideo
        restAjouterVideoMockMvc.perform(get("/api/ajouterVideos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAjouterVideo() throws Exception {
        // Initialize the database
        ajouterVideoRepository.saveAndFlush(ajouterVideo);
		
		int databaseSizeBeforeUpdate = ajouterVideoRepository.findAll().size();

        // Update the ajouterVideo
        ajouterVideo.setTitre(UPDATED_TITRE);
        ajouterVideo.setCategorie(UPDATED_CATEGORIE);
        ajouterVideo.setSujet(UPDATED_SUJET);
        ajouterVideo.setUrl(UPDATED_URL);
        restAjouterVideoMockMvc.perform(put("/api/ajouterVideos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ajouterVideo)))
                .andExpect(status().isOk());

        // Validate the AjouterVideo in the database
        List<AjouterVideo> ajouterVideos = ajouterVideoRepository.findAll();
        assertThat(ajouterVideos).hasSize(databaseSizeBeforeUpdate);
        AjouterVideo testAjouterVideo = ajouterVideos.get(ajouterVideos.size() - 1);
        assertThat(testAjouterVideo.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAjouterVideo.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testAjouterVideo.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testAjouterVideo.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void deleteAjouterVideo() throws Exception {
        // Initialize the database
        ajouterVideoRepository.saveAndFlush(ajouterVideo);
		
		int databaseSizeBeforeDelete = ajouterVideoRepository.findAll().size();

        // Get the ajouterVideo
        restAjouterVideoMockMvc.perform(delete("/api/ajouterVideos/{id}", ajouterVideo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AjouterVideo> ajouterVideos = ajouterVideoRepository.findAll();
        assertThat(ajouterVideos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
