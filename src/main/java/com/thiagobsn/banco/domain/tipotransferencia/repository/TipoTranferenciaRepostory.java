package com.thiagobsn.banco.domain.tipotransferencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.tipotransferencia.model.TipoTranferencia;

@Repository
public interface TipoTranferenciaRepostory extends JpaRepository<TipoTranferencia, Long> {

}
