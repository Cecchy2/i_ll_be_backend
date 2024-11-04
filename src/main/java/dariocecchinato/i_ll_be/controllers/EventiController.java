package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.Evento;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.payloads.EventiPayloadDTO;
import dariocecchinato.i_ll_be.payloads.EventiResponseDTO;
import dariocecchinato.i_ll_be.services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventiController {
    @Autowired
    private EventiService eventiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public EventiResponseDTO save(@Validated @RequestBody EventiPayloadDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            String message= validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload " + message);
        }
        Evento newEvento = eventiService.saveEvento(body);
        return new EventiResponseDTO(newEvento.getId());
    }

}
