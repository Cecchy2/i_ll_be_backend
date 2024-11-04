package dariocecchinato.i_ll_be.services;


import dariocecchinato.i_ll_be.entities.Utente;
import dariocecchinato.i_ll_be.exceptions.UnauthorizedException;
import dariocecchinato.i_ll_be.payloads.UtenteLoginDTO;
import dariocecchinato.i_ll_be.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationsService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiEGeneraToken(UtenteLoginDTO body){
        Utente found = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())){
            return this.jwtTools.createToken(found);
        } else{
            throw new UnauthorizedException("Errore nelle credenziali che hai fornito");
        }
    }
}
