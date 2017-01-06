package com.dms.erp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {

	public Optional<Cliente> findByCpfOuCnpj(String cpfOrCnpj);
}
