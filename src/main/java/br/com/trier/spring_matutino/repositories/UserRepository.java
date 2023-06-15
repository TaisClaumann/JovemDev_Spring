package br.com.trier.spring_matutino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}