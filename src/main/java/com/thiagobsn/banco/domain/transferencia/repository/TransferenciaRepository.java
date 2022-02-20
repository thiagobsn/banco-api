package com.thiagobsn.banco.domain.transferencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagobsn.banco.domain.transferencia.model.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
	
	public List<Transferencia> findAllByContaOrigemNumeroAndAgenciaOrigemNumeroAndTipoContaOrigemCodigoAndStatus(Long codigoTipoConta, Long numeroAgencia, Long numeroConta, Integer Status);

}
