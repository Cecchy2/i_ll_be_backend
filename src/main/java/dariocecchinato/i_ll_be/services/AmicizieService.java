package dariocecchinato.i_ll_be.services;

import dariocecchinato.i_ll_be.entities.Amicizia;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.enums.Status;
import dariocecchinato.i_ll_be.exceptions.NotFoundException;
import dariocecchinato.i_ll_be.payloads.AmiciziePayloadDTO;
import dariocecchinato.i_ll_be.repositories.AmicizieRepository;
import dariocecchinato.i_ll_be.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmicizieService {
    @Autowired
    private AmicizieRepository amicizieRepository;
    @Autowired
    private UtentiRepository utentiRepository;

    public Amicizia saveAmicizia(AmiciziePayloadDTO body){
        Amicizia newAmicizia =  new Amicizia();
        Utente utente1 = utentiRepository.findById(body.utente1()).orElseThrow(()-> new NotFoundException(body.utente1()));
        newAmicizia.setUtente1(utente1);
        Utente utente2 = utentiRepository.findById(body.utente2()).orElseThrow(()-> new NotFoundException(body.utente2()));
        newAmicizia.setUtente2(utente2);
        newAmicizia.setStatus(Status.PENDING);

        return amicizieRepository.save(newAmicizia);
    }
}
