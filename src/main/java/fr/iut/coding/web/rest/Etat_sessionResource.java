package fr.iut.coding.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.coding.domain.Etat_session;
import fr.iut.coding.repository.Etat_sessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Etat_session.
 */
@RestController
@RequestMapping("/api")
public class Etat_sessionResource {

    private final Logger log = LoggerFactory.getLogger(Etat_sessionResource.class);

    @Inject
    private Etat_sessionRepository etat_sessionRepository;

    /**
     * POST  /etat_sessions -> Create a new etat_session.
     */
    @RequestMapping(value = "/etat_sessions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Etat_session etat_session) throws URISyntaxException {
        log.debug("REST request to save Etat_session : {}", etat_session);
        if (etat_session.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new etat_session cannot already have an ID").build();
        }
        etat_sessionRepository.save(etat_session);
        return ResponseEntity.created(new URI("/api/etat_sessions/" + etat_session.getId())).build();
    }

    /**
     * PUT  /etat_sessions -> Updates an existing etat_session.
     */
    @RequestMapping(value = "/etat_sessions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Etat_session etat_session) throws URISyntaxException {
        log.debug("REST request to update Etat_session : {}", etat_session);
        if (etat_session.getId() == null) {
            return create(etat_session);
        }
        etat_sessionRepository.save(etat_session);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /etat_sessions -> get all the etat_sessions.
     */
    @RequestMapping(value = "/etat_sessions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Etat_session> getAll() {
        log.debug("REST request to get all Etat_sessions");
        return etat_sessionRepository.findAll();
    }

    /**
     * GET  /etat_sessions/:id -> get the "id" etat_session.
     */
    @RequestMapping(value = "/etat_sessions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Etat_session> get(@PathVariable Long id) {
        log.debug("REST request to get Etat_session : {}", id);
        return Optional.ofNullable(etat_sessionRepository.findOne(id))
            .map(etat_session -> new ResponseEntity<>(
                etat_session,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /etat_sessions/:id -> delete the "id" etat_session.
     */
    @RequestMapping(value = "/etat_sessions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Etat_session : {}", id);
        etat_sessionRepository.delete(id);
    }
}
