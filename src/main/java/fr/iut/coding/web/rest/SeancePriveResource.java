package fr.iut.coding.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.coding.domain.SeancePrive;
import fr.iut.coding.repository.SeancePriveRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SeancePrive.
 */
@RestController
@RequestMapping("/api")
public class SeancePriveResource {

    private final Logger log = LoggerFactory.getLogger(SeancePriveResource.class);

    @Inject
    private SeancePriveRepository seancePriveRepository;

    /**
     * POST  /seancePrives -> Create a new seancePrive.
     */
    @RequestMapping(value = "/seancePrives",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody SeancePrive seancePrive) throws URISyntaxException {
        log.debug("REST request to save SeancePrive : {}", seancePrive);
        if (seancePrive.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new seancePrive cannot already have an ID").build();
        }
        seancePriveRepository.save(seancePrive);
        return ResponseEntity.created(new URI("/api/seancePrives/" + seancePrive.getId())).build();
    }

    /**
     * PUT  /seancePrives -> Updates an existing seancePrive.
     */
    @RequestMapping(value = "/seancePrives",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody SeancePrive seancePrive) throws URISyntaxException {
        log.debug("REST request to update SeancePrive : {}", seancePrive);
        if (seancePrive.getId() == null) {
            return create(seancePrive);
        }
        seancePriveRepository.save(seancePrive);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /seancePrives -> get all the seancePrives.
     */
    @RequestMapping(value = "/seancePrives",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SeancePrive>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<SeancePrive> page = seancePriveRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seancePrives", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seancePrives/:id -> get the "id" seancePrive.
     */
    @RequestMapping(value = "/seancePrives/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SeancePrive> get(@PathVariable Long id) {
        log.debug("REST request to get SeancePrive : {}", id);
        return Optional.ofNullable(seancePriveRepository.findOne(id))
            .map(seancePrive -> new ResponseEntity<>(
                seancePrive,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /seancePrives/:id -> delete the "id" seancePrive.
     */
    @RequestMapping(value = "/seancePrives/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete SeancePrive : {}", id);
        seancePriveRepository.delete(id);
    }
}
