package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.Messaggio;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.payloads.MessaggiPayloadDTO;
import dariocecchinato.i_ll_be.payloads.MessaggiResponseDTO;
import dariocecchinato.i_ll_be.services.MessaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/messaggi")
public class MessaggiController {
    @Autowired
    private MessaggiService messaggiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessaggiResponseDTO save(@Validated @RequestBody MessaggiPayloadDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + message);
        }
        Messaggio newMessaggio = messaggiService.saveMessaggio(body);
        return new MessaggiResponseDTO(newMessaggio.getId());
    }
}
