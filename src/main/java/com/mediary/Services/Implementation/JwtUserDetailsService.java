package com.mediary.Services.Implementation;

import com.mediary.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.mediary.Models.Entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntitiesByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

}
