package dariocecchinato.i_ll_be.controllers;

import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.exceptions.NotFoundException;
import dariocecchinato.i_ll_be.payloads.UtentiPayloadDTO;
import dariocecchinato.i_ll_be.payloads.UtentiResponseDTO;
import dariocecchinato.i_ll_be.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Utente findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtentiPayloadDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono errori con il payload " + message);
        } else {
            return this.utentiService.findByIdAndUpdate(utenteId, body);
        }
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Utente findById(@PathVariable UUID utenteId) {
        Utente found = this.utentiService.findUtenteById(utenteId);
        if (found == null) throw new NotFoundException(utenteId);
        return found;
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDeleteUtente(utenteId);
    }

    @PatchMapping("/{utenteId}/immagine")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE', 'FORNITORE')")
    public Utente uploadImmagine(@PathVariable UUID utenteId, @RequestParam("immagine") MultipartFile immagine) throws IOException {
        return this.utentiService.uploadImmagine(utenteId,immagine);
    }
    @PatchMapping("/{utenteId}/immagineCopertina")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE', 'FORNITORE')")
    public Utente uploadImmagineCopertina(@PathVariable UUID utenteId, @RequestParam("immagineCopertina") MultipartFile immagineCopertina) throws IOException {
        return this.utentiService.uploadImmagineCopertina(utenteId,immagineCopertina);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser, @RequestBody UtentiPayloadDTO body) {
        return this.utentiService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utentiService.findByIdAndDeleteUtente(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/me/immagine")
    @PreAuthorize("isAuthenticated()")
    public Utente uploadImmaginePic(@AuthenticationPrincipal Utente utente, @RequestParam("immagine") MultipartFile immagine) throws IOException {
        return this.utentiService.uploadImmagine(utente.getId(), immagine);
    }

    }


