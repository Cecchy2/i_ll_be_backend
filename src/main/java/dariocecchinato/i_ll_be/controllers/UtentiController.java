package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.payloads.UtentiPayloadDTO;
import dariocecchinato.i_ll_be.payloads.UtentiResponseDTO;
import dariocecchinato.i_ll_be.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public UtentiResponseDTO save(
            @Validated @ModelAttribute UtentiPayloadDTO body,
            @RequestParam(value = "immagine", required = false) MultipartFile immagine,
            BindingResult validationResult) throws IOException {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }

        if (utentiService.existsByEmail(body.email())) {
            throw new BadRequestException("L'email è già in uso.");
        }

        Utente newUser = utentiService.saveUtente(body, immagine);
        return new UtentiResponseDTO(newUser.getId());
    }
    }


