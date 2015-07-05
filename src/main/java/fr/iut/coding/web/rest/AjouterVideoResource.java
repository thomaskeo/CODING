package fr.iut.coding.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.coding.domain.AjouterVideo;
import fr.iut.coding.repository.AjouterVideoRepository;
import fr.iut.coding.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AjouterVideo.
 */
@RestController
@RequestMapping("/api")
public class AjouterVideoResource {

    private final Logger log = LoggerFactory.getLogger(AjouterVideoResource.class);

    @Inject
    private AjouterVideoRepository ajouterVideoRepository;

    /**
     * POST  /ajouterVideos -> Create a new ajouterVideo.
     */
    @RequestMapping(value = "/ajouterVideos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody AjouterVideo ajouterVideo) throws URISyntaxException {
        log.debug("REST request to save AjouterVideo : {}", ajouterVideo);
        if (ajouterVideo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ajouterVideo cannot already have an ID").build();
        }
        ajouterVideoRepository.save(ajouterVideo);
        return ResponseEntity.created(new URI("/api/ajouterVideos/" + ajouterVideo.getId())).build();
    }

    /**
     * PUT  /ajouterVideos -> Updates an existing ajouterVideo.
     */
    @RequestMapping(value = "/ajouterVideos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody AjouterVideo ajouterVideo) throws URISyntaxException {
        log.debug("REST request to update AjouterVideo : {}", ajouterVideo);
        if (ajouterVideo.getId() == null) {
            return create(ajouterVideo);
        }
        ajouterVideoRepository.save(ajouterVideo);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /ajouterVideos -> get all the ajouterVideos.
     */
    @RequestMapping(value = "/ajouterVideos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AjouterVideo>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<AjouterVideo> page = ajouterVideoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ajouterVideos", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ajouterVideos/:id -> get the "id" ajouterVideo.
     */
    @RequestMapping(value = "/ajouterVideos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AjouterVideo> get(@PathVariable Long id) {
        log.debug("REST request to get AjouterVideo : {}", id);
        return Optional.ofNullable(ajouterVideoRepository.findOne(id))
            .map(ajouterVideo -> new ResponseEntity<>(
                ajouterVideo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ajouterVideos/:id -> delete the "id" ajouterVideo.
     */
    @RequestMapping(value = "/ajouterVideos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete AjouterVideo : {}", id);
        ajouterVideoRepository.delete(id);
    }
}
