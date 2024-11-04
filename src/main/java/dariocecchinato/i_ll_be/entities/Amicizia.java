package dariocecchinato.i_ll_be.entities;

import dariocecchinato.i_ll_be.enums.Status;
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
public class Amicizia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id_1")
    private Utente utente1;

    @ManyToOne
    @JoinColumn(name = "utente_id_2")
    private Utente utente2;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate dataCreazione = LocalDate.now();


    public Amicizia(Utente utente1, Utente utente2, Status status) {
        this.utente1 = utente1;
        this.utente2 = utente2;
        this.status = status;
    }
}
