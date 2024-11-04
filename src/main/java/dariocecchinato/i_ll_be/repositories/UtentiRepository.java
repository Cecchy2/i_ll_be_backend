package dariocecchinato.i_ll_be.repositories;

import dariocecchinato.i_ll_be.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {

    boolean existsByEmail(String email);

    Optional<Utente> findByEmail(String email);
}
