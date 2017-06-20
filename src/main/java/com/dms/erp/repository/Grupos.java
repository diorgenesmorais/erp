package com.dms.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Grupo;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long> {

}
