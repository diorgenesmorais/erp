package com.dms.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dms.erp.model.Usuario;
import com.dms.erp.repository.helper.usuario.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	public Optional<Usuario> findByEmail(String email);

	/**
	 * Obter as permissões do usuário.
	 * 
	 * <pre>
	 * Deve gerar uma query assim:
	 * 
	 * select distinct p.name from usuario u 
	 * inner join usuario_grupo ug on ug.usuario_id=u.id 
	 * inner join grupo g on g.id=ug.grupo_id 
	 * inner join grupo_permissao gp on gp.grupo_id=g.id 
	 * inner join permissao p on p.id=gp.permissao_id 
	 * where u.id=1;
	 * </pre>
	 * 
	 * @param usuario
	 *            {@code Usuario}
	 * @return Uma lista das permissões do usuário.
	 */
	@Query("select distinct p.name from Usuario u inner join u.grupos g inner join g.permissoes p where u = ?1")
	public List<String> getPermissoes(Usuario usuario);
}
