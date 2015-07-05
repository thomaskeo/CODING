package fr.iut.coding.repository;

import fr.iut.coding.domain.SeancePrive;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SeancePrive entity.
 */
public interface SeancePriveRepository extends JpaRepository<SeancePrive,Long> {

    @Query("select seancePrive from SeancePrive seancePrive where seancePrive.user.login = ?#{principal.username}")
    List<SeancePrive> findAllForCurrentUser();

}
