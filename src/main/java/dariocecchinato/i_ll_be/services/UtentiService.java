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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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



    public Utente saveUtente(UtentiPayloadDTO body, MultipartFile immagine) throws IOException{
        Utente newUtente = new Utente();
        newUtente.setNome(body.nome());
        newUtente.setCognome(body.cognome());
        newUtente.setEmail(body.email());
        newUtente.setUsername(body.username());
        newUtente.setPassword(body.password());

        newUtente= utentiRepository.save(newUtente);
        if (immagine != null && !immagine.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(immagine.getBytes(), ObjectUtils.emptyMap());
            String immagineUrl = (String) uploadResult.get("secure_url");
            newUtente.setImmagine(immagineUrl);

            newUtente = utentiRepository.save(newUtente);
        }

        mailgunSender.sendRegistrationEmail(newUtente);

        return newUtente;
    }

    public boolean existsByEmail(String email) {
        return utentiRepository.existsByEmail(email);
    }

    public Utente findById (UUID utenteID){
        return utentiRepository.findById(utenteID).orElseThrow(()-> new NotFoundException(utenteID));
    }
}
