package dariocecchinato.i_ll_be.payloads;

import java.util.UUID;

public record PartecipazioniEventoPayloadDTO(
        UUID utente,
        UUID evento) {
}
