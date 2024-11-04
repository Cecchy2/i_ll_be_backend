package dariocecchinato.i_ll_be.payloads;

import dariocecchinato.i_ll_be.enums.Modo;

import java.time.LocalDate;

public record EventiPayloadDTO(
        String titolo,
        LocalDate dataEvento,
        LocalDate dataFineEvento,
        String luogo,
        String descrizione,
        String categoria,
        Modo modo) {
}
