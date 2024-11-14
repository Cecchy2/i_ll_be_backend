package dariocecchinato.i_ll_be.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
@EnableWebSecurity
public class WebSocketSecurityConfig {

    // Definisce il bean richiesto da Spring Security per autorizzare i messaggi WebSocket
    @Bean
    public MessageMatcherDelegatingAuthorizationManager.Builder messageMatcherBuilder() {
        return new MessageMatcherDelegatingAuthorizationManager.Builder();
    }

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages.simpDestMatchers("/user/**").hasRole("USER"); // Imposta l'autorizzazione per i messaggi diretti agli endpoint WebSocket con prefisso "/user"
        return messages.build();
    }
}
