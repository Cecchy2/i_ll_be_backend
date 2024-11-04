package dariocecchinato.i_ll_be.entities;


import dariocecchinato.i_ll_be.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;

    private String immagine;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    @OneToMany(mappedBy = "creatoreEvento")
    private List<Evento> eventi;

    public Utente(String nome, String cognome, String username, String email, String immagine, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.immagine = immagine;
        this.password=password;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", immagine='" + immagine + '\'' +
                ", role=" + role +
                ", eventi=" + eventi +
                '}';
    }
}
