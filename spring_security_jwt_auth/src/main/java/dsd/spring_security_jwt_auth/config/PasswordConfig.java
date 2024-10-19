package dsd.spring_security_jwt_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordConfig {
    private static final byte[] SECURED_NUMBERS = generatedSecuredNumbers();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom(SECURED_NUMBERS));
    }

    private static byte[] generatedSecuredNumbers() {
        byte[] numberArray = new byte[16];
        new SecureRandom().nextBytes(numberArray);
        return numberArray;
    }
}
