package com.challenge.dao;

import com.challenge.entities.Userent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserentDAO extends JpaRepository<Userent, Integer> {
    @Query("select u from Userent as u where u.username = :username or u.email = :username")
    Userent findByUsername(String username);
}
