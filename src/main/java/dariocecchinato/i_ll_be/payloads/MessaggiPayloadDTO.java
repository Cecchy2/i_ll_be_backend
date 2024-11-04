package dariocecchinato.i_ll_be.payloads;

import dariocecchinato.i_ll_be.entities.Evento;
import dariocecchinato.i_ll_be.entities.Utente;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessaggiPayloadDTO(
        UUID mittente,
        UUID destinatario,
        String testo,
        UUID evento) {
}
