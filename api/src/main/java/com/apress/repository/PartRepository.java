package com.apress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apress.domain.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

}