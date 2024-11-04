package dariocecchinato.i_ll_be.repositories;

import dariocecchinato.i_ll_be.entities.PartecipazioneEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartecipazioniEventoRepository extends JpaRepository<PartecipazioneEvento, UUID> {
}
