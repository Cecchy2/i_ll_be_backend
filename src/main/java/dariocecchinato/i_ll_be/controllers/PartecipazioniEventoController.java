package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.PartecipazioneEvento;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.payloads.PartecipazioniEventoPayloadDTO;
import dariocecchinato.i_ll_be.payloads.PartecipazioniEventoResponseDTO;
import dariocecchinato.i_ll_be.services.PartecipazioniEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/partecipazioniEvento")
public class PartecipazioniEventoController {
    @Autowired
    private PartecipazioniEventoService partecipazioniEventoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartecipazioniEventoResponseDTO save(@Validated @RequestBody PartecipazioniEventoPayloadDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload " + message);
        }
        PartecipazioneEvento newPartecipazione = partecipazioniEventoService.savePartecipazione(body);
        return new PartecipazioniEventoResponseDTO(newPartecipazione.getId());
    }
}
