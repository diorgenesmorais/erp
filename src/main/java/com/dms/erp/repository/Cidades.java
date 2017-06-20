package com.dms.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Cidade;
import com.dms.erp.repository.helper.cidade.CidadesQueries;
import com.dms.useful.UFBrasil;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstado(UFBrasil estado);

	public Optional<Cidade> findByNomeAndEstado(String nome, UFBrasil estado);
}