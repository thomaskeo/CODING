package fr.iut.coding.repository;

import fr.iut.coding.domain.CodingSession;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CodingSession entity.
 */
public interface CodingSessionRepository extends JpaRepository<CodingSession,Long> {

    @Query("select codingSession from CodingSession codingSession where codingSession.animateur.login = ?#{principal.username}")
    List<CodingSession> findAllForCurrentUser();

}
