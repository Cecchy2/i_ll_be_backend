package dariocecchinato.i_ll_be.controllers;


import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.enums.Role;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.payloads.UtenteLoginDTO;
import dariocecchinato.i_ll_be.payloads.UtenteLoginResponseDTO;
import dariocecchinato.i_ll_be.payloads.UtentiPayloadDTO;
import dariocecchinato.i_ll_be.payloads.UtentiResponseDTO;
import dariocecchinato.i_ll_be.services.AuthorizationsService;
import dariocecchinato.i_ll_be.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AuthorizationsService authorizationsService;


    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO body){
        Utente found = this.utentiService.findByEmail(body.email());
        Role role= found.getRole();
        UUID utenteId = found.getId();
        return new UtenteLoginResponseDTO(this.authorizationsService.checkCredenzialiEGeneraToken(body),role, utenteId);
    }

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
