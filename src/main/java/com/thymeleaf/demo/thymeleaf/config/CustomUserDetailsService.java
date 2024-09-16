package com.thymeleaf.demo.thymeleaf.config;

import com.thymeleaf.demo.thymeleaf.model.Usuario;
import com.thymeleaf.demo.thymeleaf.service.UsuarioService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public CustomUserDetailsService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarUsuarioPorNick(nick);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + nick);
        }
        return new org.springframework.security.core.userdetails.User(
                usuario.getNick(), usuario.getPassword(), new ArrayList<>()
        );
    }
}
