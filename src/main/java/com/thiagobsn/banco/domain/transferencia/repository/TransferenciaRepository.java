package com.thiagobsn.banco.domain.transferencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.transferencia.model.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

}
