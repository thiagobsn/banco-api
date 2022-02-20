package com.thiagobsn.banco.domain.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.agencia.model.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {

}
