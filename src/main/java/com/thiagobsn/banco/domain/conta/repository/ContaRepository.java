package com.thiagobsn.banco.domain.conta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.conta.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	
}
