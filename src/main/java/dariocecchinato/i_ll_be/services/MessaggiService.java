package dariocecchinato.i_ll_be.services;

import dariocecchinato.i_ll_be.entities.Evento;
import dariocecchinato.i_ll_be.entities.Messaggio;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.payloads.MessaggiPayloadDTO;
import dariocecchinato.i_ll_be.repositories.MessaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MessaggiService {
    @Autowired
    private MessaggiRepository messaggiRepository;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EventiService eventiService;

    public Messaggio saveMessaggio(MessaggiPayloadDTO body){
        Messaggio newMessaggio = new Messaggio();

        Utente mittente = utentiService.findById(body.mittente());
        newMessaggio.setMittente(mittente);
        Utente destinatario = utentiService.findById(body.destinatario());
        newMessaggio.setDestinatario(destinatario);
        newMessaggio.setTesto(body.testo());
        newMessaggio.setDataInvio(LocalDateTime.now());
        Evento evento= eventiService.findById(body.evento());
        newMessaggio.setEvento(evento);
        return messaggiRepository.save(newMessaggio);
    }
}
