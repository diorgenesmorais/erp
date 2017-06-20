package com.dms.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Venda;

@Repository
public interface Vendas extends JpaRepository<Venda, Long> {

}
