package com.securityjpa.demo;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class myuserdetailservice implements UserDetailsService {

    @Autowired
    private repo myrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<user> user = myrepo.findByName(username); // Ensure that this method exists in your repo

        if (user.isPresent()) {
            var userobj = user.get();
            // Assuming roles like "USER" or "ADMIN", prefix them with "ROLE_"
            return User.builder()
                    .username(userobj.getName())
                    .password(userobj.getPassword()) // Ensure password is bcrypt-encoded
                    .roles(userobj.getRoll()) // Replace "roll" with "role"
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
