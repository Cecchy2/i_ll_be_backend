package dariocecchinato.i_ll_be.services;

import dariocecchinato.i_ll_be.entities.Evento;
import dariocecchinato.i_ll_be.entities.PartecipazioneEvento;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.payloads.PartecipazioniEventoPayloadDTO;
import dariocecchinato.i_ll_be.repositories.PartecipazioniEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartecipazioniEventoService {
    @Autowired
    private PartecipazioniEventoRepository partecipazioniEventoRepository;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EventiService eventiService;

    public PartecipazioneEvento savePartecipazione(PartecipazioniEventoPayloadDTO body){
        PartecipazioneEvento newPartecipazione = new PartecipazioneEvento();
        Utente partecipante = utentiService.findById(body.utente());
        newPartecipazione.setUtente(partecipante);
        Evento evento = eventiService.findById(body.evento());
        newPartecipazione.setEvento(evento);
        return partecipazioniEventoRepository.save(newPartecipazione);
    }
}
