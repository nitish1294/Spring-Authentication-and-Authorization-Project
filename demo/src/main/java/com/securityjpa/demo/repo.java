package com.securityjpa.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repo extends JpaRepository<user, Long> {
	Optional<user> findByName(String name);

}
