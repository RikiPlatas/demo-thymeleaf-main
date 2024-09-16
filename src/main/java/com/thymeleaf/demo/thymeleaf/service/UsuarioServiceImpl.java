package com.thymeleaf.demo.thymeleaf.service;

import com.thymeleaf.demo.thymeleaf.model.Usuario;
import com.thymeleaf.demo.thymeleaf.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor para la inyección de dependencias

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

        // Método para guardar un usuario
        public Usuario guardarUsuario(Usuario usuario) {
            return usuarioRepository.save(usuario);
        }

        // Método para obtener todos los usuarios
        public List<Usuario> obtenerTodosLosUsuarios() {
            return usuarioRepository.findAll();
        }

        // Método para obtener un usuario por su ID
        public Optional<Usuario> obtenerUsuarioPorId(Long id) {
            return usuarioRepository.findById(id);
        }

        // Método para actualizar un usuario
        public Usuario actualizarUsuario(Usuario usuario) {
            return usuarioRepository.save(usuario);
        }

        // Método para eliminar un usuario por su ID
        public void eliminarUsuarioPorId(Long id) {
            usuarioRepository.deleteById(id);
        }

    @Override
    public Usuario buscarUsuarioPorNick(String nick) {
        return usuarioRepository.findByNick(nick);
    }

    @Override
    public boolean verificarLogin(Usuario usuario, String password) {
        return passwordEncoder.matches(password, usuario.getPassword());
    }

}
