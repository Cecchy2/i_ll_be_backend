package dariocecchinato.i_ll_be.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "mittente_id")
    private Utente mittente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Utente destinatario;

    private String testo;

    private LocalDateTime dataInvio = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = true)
    private Evento evento;

    public Messaggio(Utente mittente, Utente destinatario, String testo, LocalDateTime dataInvio, Evento evento) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.testo = testo;
        this.dataInvio = dataInvio;
        this.evento = evento;
    }
}
