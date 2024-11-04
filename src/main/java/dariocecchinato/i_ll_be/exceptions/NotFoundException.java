package dariocecchinato.i_ll_be.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("La risorsa con ID : " + id + " non è stata trovata!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
