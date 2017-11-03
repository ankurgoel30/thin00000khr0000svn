package com.thinkhr.external.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.thinkhr.external.api.db.entities.User;
import com.thinkhr.external.api.repositories.UserRepository;

/**
* The UserAuthenticationManager is to load user's details for authentication\authorization.
*
* @author  Surabhi Bhawsar
* @since   2017-11-01 
*/

@Component
public class UserAuthenticationManager implements UserDetailsService {
	
    @Autowired
    private UserRepository userRepository;

    /**
     * Fetch all users from system matching to given username 
     * @param username
     * @return UserDetails object 
     * @throws UsernameNotFoundException
     *  
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);

        return userDetails;
    }
}