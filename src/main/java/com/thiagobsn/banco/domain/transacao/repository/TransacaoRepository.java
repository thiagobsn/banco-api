package com.thiagobsn.banco.domain.transacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.transacao.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	

}
