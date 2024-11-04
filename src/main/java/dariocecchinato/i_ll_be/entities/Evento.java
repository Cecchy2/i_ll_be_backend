package dariocecchinato.i_ll_be.entities;

import dariocecchinato.i_ll_be.enums.Modo;
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
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String titolo;
    private LocalDate dataCreazione = LocalDate.now();
    private LocalDate dataEvento;
    private LocalDate dataFineEvento;
    @Enumerated(EnumType.STRING)
    private Modo modo;
    private String luogo;
    private String descrizione;
    private String categoria;
    @ManyToOne
    @JoinColumn(name = "creatoreEvento_id")
    private Utente creatoreEvento;

    public Evento(LocalDate dataEvento, LocalDate dataFineEvento, Modo modo, String luogo, String descrizione, String categoria) {
        this.dataEvento = dataEvento;
        this.dataFineEvento = dataFineEvento;
        this.modo = modo;
        this.luogo = luogo;
        this.descrizione = descrizione;
        this.categoria = categoria;
    }
}
