package com.thymeleaf.demo.thymeleaf.repository;

import com.thymeleaf.demo.thymeleaf.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{

    Usuario findByNick(String nick);

}
