package dariocecchinato.i_ll_be.payloads;

import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.enums.Status;

import java.util.UUID;

public record AmiciziePayloadDTO(
        UUID utente1,
        UUID utente2
        ) {
}
