package com.thymeleaf.demo.thymeleaf.service;

import com.thymeleaf.demo.thymeleaf.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface UsuarioService {

    // Método para guardar un usuario
    Usuario guardarUsuario(Usuario usuario);

    // Método para obtener todos los usuarios
    List<Usuario> obtenerTodosLosUsuarios();

    // Método para obtener un usuario por su ID
    Optional<Usuario> obtenerUsuarioPorId(Long id);

    // Método para actualizar un usuario
    Usuario actualizarUsuario(Usuario usuario);

    // Método para eliminar un usuario por su ID
    void eliminarUsuarioPorId(Long id);

    Usuario buscarUsuarioPorNick(String nick);

    // Verificar contraseña del usuario
     boolean verificarLogin(Usuario usuario, String password);
}
