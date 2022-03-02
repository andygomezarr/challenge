package com.challenge.services;

import com.challenge.dao.UserentDAO;
import com.challenge.entities.Userent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserentService implements UserDetailsService {
    @Autowired
    private UserentDAO userentDAO;

    // carga el usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Userent userent = userentDAO.findByUsername(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userent.getRole().toUpperCase()));
            return new org.springframework.security.core.userdetails.User(username, userent.getPassword(), authorities);
        } catch (Exception exception) {
            throw exception;
        }
    }

    // guarda datos del usuario
    @Transactional
    public Userent save(Userent userent) throws Exception {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userent.setPassword(encoder.encode(userent.getPassword()));
            userentDAO.save(userent);
            return userent;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
