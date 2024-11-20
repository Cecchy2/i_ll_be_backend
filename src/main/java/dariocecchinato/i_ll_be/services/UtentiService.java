package dariocecchinato.i_ll_be.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.enums.Role;
import dariocecchinato.i_ll_be.exceptions.NotFoundException;
import dariocecchinato.i_ll_be.payloads.UtentiPayloadDTO;
import dariocecchinato.i_ll_be.repositories.UtentiRepository;
import dariocecchinato.i_ll_be.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private MailgunSender mailgunSender;



    public Utente saveUtente(UtentiPayloadDTO body, MultipartFile immagine, MultipartFile immagineCopertina) throws IOException{
        Utente newUtente = new Utente();
        newUtente.setNome(body.nome());
        newUtente.setCognome(body.cognome());
        newUtente.setEmail(body.email());
        newUtente.setUsername(body.username());
        newUtente.setPassword(bcrypt.encode(body.password()));

        newUtente= utentiRepository.save(newUtente);

        if (immagine != null && !immagine.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(immagine.getBytes(), ObjectUtils.emptyMap());
                String immagineUrl = (String) uploadResult.get("secure_url");
                newUtente.setImmagine(immagineUrl);
            } catch (IOException e) {
                throw new IOException("Errore durante il caricamento dell'immagine profilo: " + e.getMessage());
            }
        }
        if (immagineCopertina != null && !immagineCopertina.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(immagineCopertina.getBytes(), ObjectUtils.emptyMap());
                String immagineCopertinaUrl = (String) uploadResult.get("secure_url");
                newUtente.setImmagineCopertina(immagineCopertinaUrl); // Aggiungi un campo immagineCopertina a Utente
            } catch (IOException e) {
                throw new IOException("Errore durante il caricamento dell'immagine di copertina: " + e.getMessage());
            }
        }
        newUtente = utentiRepository.save(newUtente);

        mailgunSender.sendRegistrationEmail(newUtente);

        return newUtente;
    }

    public boolean existsByEmail(String email) {
        return utentiRepository.existsByEmail(email);
    }

    public Utente findById (UUID utenteID){
        return utentiRepository.findById(utenteID).orElseThrow(()-> new NotFoundException(utenteID));
    }

    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public Page<Utente> findAll (int page, int size, String sortBy){
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    public Utente findUtenteById(UUID utenteId) {
        Utente found = this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        return found;
    }

    public Utente findByIdAndUpdate(UUID utenteId, UtentiPayloadDTO body) {
        Utente found = this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        if (found == null) throw new NotFoundException(utenteId);
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(bcrypt.encode(body.password()));
        found.setNome(body.nome());
        found.setCognome(body.cognome());

        return utentiRepository.save(found);
    }

    public Utente uploadImmagine(UUID utenteId, MultipartFile immagine) throws IOException{
        Utente found= this.findUtenteById(utenteId);
        String url= (String) cloudinary.uploader().upload(immagine.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("Url " + url);
        found.setImmagine(url);
        return this.utentiRepository.save(found);
    }

    public Utente uploadImmagineCopertina(UUID utenteId, MultipartFile immagineCopertina) throws IOException{
        Utente found= this.findUtenteById(utenteId);
        String url= (String) cloudinary.uploader().upload(immagineCopertina.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("Url " + url);
        found.setImmagineCopertina(url);
        return this.utentiRepository.save(found);
    }

    public void findByIdAndDeleteUtente(UUID utenteId) {
        Utente found = this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        if (found == null) throw new NotFoundException(utenteId);
        this.utentiRepository.delete(found);
    }

    public List<Utente> findByNome(String nome){
        List<Utente> utenti = this.utentiRepository.findByNome(nome);
        return utenti;
    }
}
