package com.thiagobsn.banco.domain.tipooperacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.tipooperacao.model.TipoOperacao;

@Repository
public interface TipoOperacaoRepository extends JpaRepository<TipoOperacao, Long> {

}
