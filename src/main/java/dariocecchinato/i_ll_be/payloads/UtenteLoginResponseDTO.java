package dariocecchinato.i_ll_be.payloads;


import dariocecchinato.i_ll_be.enums.Role;

import java.util.UUID;

public record UtenteLoginResponseDTO(String accessToken, Role role, UUID utenteId) {
}
