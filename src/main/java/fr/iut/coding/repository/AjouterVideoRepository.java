package fr.iut.coding.repository;

import fr.iut.coding.domain.AjouterVideo;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AjouterVideo entity.
 */
public interface AjouterVideoRepository extends JpaRepository<AjouterVideo,Long> {

}
