package com.thiagobsn.banco.domain.tipotransacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.tipotransacao.model.TipoTransacao;

@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long>{

}
