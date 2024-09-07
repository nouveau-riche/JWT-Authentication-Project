package com.example.JWT.Authentication.Project.repositories;

import com.example.JWT.Authentication.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            return User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public UserDetails loadUserForSignup(UserEntity userEntity) throws UsernameNotFoundException {

        return User.builder().username(userEntity.getUsername()).password(userEntity.getPassword()).roles(userEntity.getRoles().toArray(new String[0])).build();
    }

}
