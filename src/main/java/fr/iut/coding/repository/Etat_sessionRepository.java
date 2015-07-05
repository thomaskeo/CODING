package fr.iut.coding.repository;

import fr.iut.coding.domain.Etat_session;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Etat_session entity.
 */
public interface Etat_sessionRepository extends JpaRepository<Etat_session,Long> {

}
