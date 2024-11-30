package dariocecchinato.i_ll_be.services;

import dariocecchinato.i_ll_be.entities.Evento;
import dariocecchinato.i_ll_be.exceptions.NotFoundException;
import dariocecchinato.i_ll_be.payloads.EventiPayloadDTO;
import dariocecchinato.i_ll_be.repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventiService {
    @Autowired
    private EventiRepository eventiRepository;

    public Evento saveEvento(EventiPayloadDTO body){
        Evento newEvento = new Evento();
        newEvento.setTitolo(body.titolo());
        newEvento.setDataEvento(body.dataEvento());
        newEvento.setDataFineEvento(body.dataFineEvento());
        newEvento.setLuogo(body.luogo());
        newEvento.setDescrizione(body.descrizione());
        newEvento.setCategoria(body.categoria());
        newEvento.setModo(body.modo());

        return eventiRepository.save(newEvento);
    }

    public Evento findById (UUID eventoId){
        return eventiRepository.findById(eventoId).orElseThrow(()-> new NotFoundException(eventoId));
    }

    public Page<Evento> findAll(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventiRepository.findAll(pageable);
    }
}
