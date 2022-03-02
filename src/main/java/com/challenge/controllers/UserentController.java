package com.challenge.controllers;

import com.challenge.dao.UserentDAO;
import com.challenge.entities.Userent;
import com.challenge.services.UserentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserentController {
    @Autowired
    private UserentService UserentService;
    @Autowired
    private UserentDAO UserentDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserentController(UserentDAO UserentDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.UserentDAO = UserentDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // NO TOMAR EN CUENTA ESTOS METODOS

    /*
//    @PostMapping("/users/")
    public void saveUserent(@RequestBody Userent user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserentDAO.save(user);
    }

//    @GetMapping("/users/")
    public List<Userent> getAllUserents() {
        return UserentDAO.findAll();
    }

//    @GetMapping("/users/{username}")
    public Userent getUserent(@PathVariable String username) {
        return UserentDAO.findByUsername(username);
    }

//    @PostMapping("auth/register")
    public ResponseEntity<Userent> save(@RequestBody Userent user){
        try {
            return  ResponseEntity.ok(UserentService.save(user));
        }catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
    */
}
