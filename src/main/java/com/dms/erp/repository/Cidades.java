package com.dms.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Cidade;
import com.dmsystem.useful.UFBrasil;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long> {

	public List<Cidade> findByEstado(UFBrasil estado);
}
