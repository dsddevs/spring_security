package dsd.spring_security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConfig {

   @Value("${user.password}")
   private String userPassword;

   @Value("${admin.password}")
   private String adminPassword;

   @Bean
   public PasswordEncoder passwordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
   }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        var user = createUser("user", passwordEncoder().encode(userPassword), "USER");
        var admin = createUser("admin", passwordEncoder().encode(adminPassword), "ADMIN");
        users.add(user);
        users.add(admin);
        return new InMemoryUserDetailsManager(users);
    }

    private UserDetails createUser(String login, String password, String role) {
        return User
                .withUsername(login)
                .password(password)
                .roles(role)
                .build();
    }

}
