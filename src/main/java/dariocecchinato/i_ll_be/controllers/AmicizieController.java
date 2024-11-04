package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.Amicizia;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.payloads.AmiciziePayloadDTO;
import dariocecchinato.i_ll_be.payloads.AmicizieResponseDTO;
import dariocecchinato.i_ll_be.services.AmicizieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/amicizie")
public class AmicizieController {
    @Autowired
    private AmicizieService amicizieService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AmicizieResponseDTO save(@Validated @RequestBody AmiciziePayloadDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            String message= validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload " + message);
        }
        Amicizia newAmicizia= amicizieService.saveAmicizia(body);
        return new AmicizieResponseDTO(newAmicizia.getId());
    }
}
