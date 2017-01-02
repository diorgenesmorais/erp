package com.dms.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Cidade;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long> {

}
