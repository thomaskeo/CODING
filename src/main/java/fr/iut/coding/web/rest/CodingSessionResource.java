package fr.iut.coding.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.coding.domain.CodingSession;
import fr.iut.coding.repository.CodingSessionRepository;
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
 * REST controller for managing CodingSession.
 */
@RestController
@RequestMapping("/api")
public class CodingSessionResource {

    private final Logger log = LoggerFactory.getLogger(CodingSessionResource.class);

    @Inject
    private CodingSessionRepository codingSessionRepository;

    /**
     * POST  /codingSessions -> Create a new codingSession.
     */
    @RequestMapping(value = "/codingSessions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody CodingSession codingSession) throws URISyntaxException {
        log.debug("REST request to save CodingSession : {}", codingSession);
        if (codingSession.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new codingSession cannot already have an ID").build();
        }
        codingSessionRepository.save(codingSession);
        return ResponseEntity.created(new URI("/api/codingSessions/" + codingSession.getId())).build();
    }

    /**
     * PUT  /codingSessions -> Updates an existing codingSession.
     */
    @RequestMapping(value = "/codingSessions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody CodingSession codingSession) throws URISyntaxException {
        log.debug("REST request to update CodingSession : {}", codingSession);
        if (codingSession.getId() == null) {
            return create(codingSession);
        }
        codingSessionRepository.save(codingSession);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /codingSessions -> get all the codingSessions.
     */
    @RequestMapping(value = "/codingSessions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CodingSession>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<CodingSession> page = codingSessionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/codingSessions", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /codingSessions/:id -> get the "id" codingSession.
     */
    @RequestMapping(value = "/codingSessions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CodingSession> get(@PathVariable Long id) {
        log.debug("REST request to get CodingSession : {}", id);
        return Optional.ofNullable(codingSessionRepository.findOne(id))
            .map(codingSession -> new ResponseEntity<>(
                codingSession,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /codingSessions/:id -> delete the "id" codingSession.
     */
    @RequestMapping(value = "/codingSessions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CodingSession : {}", id);
        codingSessionRepository.delete(id);
    }
}
