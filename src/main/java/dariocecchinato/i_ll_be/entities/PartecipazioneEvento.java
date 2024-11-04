package dariocecchinato.i_ll_be.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PartecipazioneEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
    private LocalDate dataConferma = LocalDate.now();

    public PartecipazioneEvento(Utente utente, Evento evento) {
        this.utente = utente;
        this.evento = evento;
    }
}
