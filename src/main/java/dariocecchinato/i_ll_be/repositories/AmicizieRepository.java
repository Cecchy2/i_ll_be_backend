package dariocecchinato.i_ll_be.repositories;

import dariocecchinato.i_ll_be.entities.Amicizia;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AmicizieRepository extends JpaRepository<Amicizia, UUID> {
    boolean existsByUtente1AndUtente2(Utente utente1, Utente utente2);

    List<Amicizia> findByUtente1OrUtente2(Utente utente1, Utente utente2);

}
