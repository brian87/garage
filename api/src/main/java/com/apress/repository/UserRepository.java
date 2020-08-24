package com.apress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apress.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}