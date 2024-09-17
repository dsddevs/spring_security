package dsd.spring_security.service;

import dsd.spring_security.model.entity.UserEntity;
import dsd.spring_security.model.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            UserEntity user = userRepo.findByUsername(username);
            if (user == null) throw new UsernameNotFoundException("Error: User is not found!");
            List<GrantedAuthority> userAuthorities = new ArrayList<>();
            userAuthorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
            return new User(user.getUsername(), user.getPassword(), userAuthorities);
        }catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
