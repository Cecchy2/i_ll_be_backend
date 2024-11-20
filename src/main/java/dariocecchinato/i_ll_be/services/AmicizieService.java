package dariocecchinato.i_ll_be.services;

import dariocecchinato.i_ll_be.entities.Amicizia;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.enums.Status;
import dariocecchinato.i_ll_be.exceptions.BadRequestException;
import dariocecchinato.i_ll_be.exceptions.NotFoundException;
import dariocecchinato.i_ll_be.payloads.AmiciziePayloadDTO;
import dariocecchinato.i_ll_be.repositories.AmicizieRepository;
import dariocecchinato.i_ll_be.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AmicizieService {
    @Autowired
    private AmicizieRepository amicizieRepository;
    @Autowired
    private UtentiRepository utentiRepository;

    public Amicizia saveAmicizia(AmiciziePayloadDTO body){
        Utente utente1 = utentiRepository.findById(body.utente1()).orElseThrow(()-> new NotFoundException(body.utente1()));
        Utente utente2 = utentiRepository.findById(body.utente2()).orElseThrow(()-> new NotFoundException(body.utente2()));

        boolean exists = amicizieRepository.existsByUtente1AndUtente2(utente1, utente2)
                || amicizieRepository.existsByUtente1AndUtente2(utente2, utente1);

        if (exists) {
            throw new BadRequestException("Siete già amici.");
        }

        Amicizia newAmicizia =  new Amicizia();
        newAmicizia.setUtente1(utente1);
        newAmicizia.setUtente2(utente2);
        newAmicizia.setStatus(Status.PENDING);

        return amicizieRepository.save(newAmicizia);
    }

    public List<Utente> findAllAmici(UUID utenteId) {
        Utente utente = utentiRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException(utenteId));

        List<Amicizia> amicizie = amicizieRepository.findByUtente1OrUtente2(utente, utente);

        return amicizie.stream()
                .map(amicizia ->
                        amicizia.getUtente1().equals(utente) ? amicizia.getUtente2() : amicizia.getUtente1()
                )
                .collect(Collectors.toList());
    }

}
