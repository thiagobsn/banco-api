package com.thiagobsn.banco.domain.tipotransferencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.tipotransferencia.model.TipoTransferencia;

@Repository
public interface TipoTransferenciaRepostory extends JpaRepository<TipoTransferencia, Long> {

}
