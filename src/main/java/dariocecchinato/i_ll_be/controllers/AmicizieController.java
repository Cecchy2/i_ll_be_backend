package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.Amicizia;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.exceptions.NotFoundException;
import dariocecchinato.i_ll_be.payloads.AmiciziePayloadDTO;
import dariocecchinato.i_ll_be.payloads.AmicizieResponseDTO;
import dariocecchinato.i_ll_be.services.AmicizieService;
import dariocecchinato.i_ll_be.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/amicizie")
public class AmicizieController {
    @Autowired
    private AmicizieService amicizieService;
    @Autowired
    private UtentiService utentiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AmicizieResponseDTO save(@Validated @RequestBody AmiciziePayloadDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            String message= validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload " + message);
        }
        Amicizia newAmicizia= amicizieService.saveAmicizia(body);
        return new AmicizieResponseDTO(newAmicizia.getId());
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Utente amico (@PathVariable UUID utenteId){
        Utente found = this.utentiService.findUtenteById(utenteId);
        if (found == null) throw new NotFoundException(utenteId);
        return found;
    }
    @GetMapping("/{utenteId}/amici")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<Utente> getAmici(@PathVariable UUID utenteId) {
        return amicizieService.findAllAmici(utenteId);
    }
}
