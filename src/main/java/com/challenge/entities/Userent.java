package com.challenge.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class Userent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11)
    private Integer id;
    private Date created;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private Date updated;
    private String username;
    @Column(length = 25)
    private String role;
}
