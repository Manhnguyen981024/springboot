package com.example.webservice.study_springboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webservice.study_springboot.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
