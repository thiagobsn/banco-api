package com.thiagobsn.banco.domain.tipoconta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.tipoconta.model.TipoConta;

@Repository
public interface TipoContaRepository extends JpaRepository<TipoConta, Long> {

}
