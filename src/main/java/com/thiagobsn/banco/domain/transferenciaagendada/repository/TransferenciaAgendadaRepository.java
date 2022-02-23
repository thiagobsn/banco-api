package com.thiagobsn.banco.domain.transferenciaagendada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.transferenciaagendada.model.TransferenciaAgendada;

@Repository
public interface TransferenciaAgendadaRepository extends JpaRepository<TransferenciaAgendada, Long> {

}
