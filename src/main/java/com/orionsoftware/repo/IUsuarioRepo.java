package com.orionsoftware.repo;

import com.orionsoftware.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepo  extends JpaRepository<Usuario, Integer> {

	//select * from usuario where username = ?
	//OBTENEMOS EL USUARIO SEGUN EL username Y TAMBIEN SUS ROLES ASOCIADOS
	Usuario findOneByUsername(String username);
}
