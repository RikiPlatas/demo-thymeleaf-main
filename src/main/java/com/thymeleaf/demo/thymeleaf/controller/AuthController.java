package com.thymeleaf.demo.thymeleaf.controller;

import com.thymeleaf.demo.thymeleaf.model.Usuario;
import com.thymeleaf.demo.thymeleaf.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar el formulario de login
    @GetMapping("/login")
    public String loginForm(ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // Procesar el inicio de sesión
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String nick, @RequestParam String password, ModelMap model) {
        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorNick(nick);

        if (usuarioEncontrado != null && usuarioService.verificarLogin(usuarioEncontrado, password)) {
            model.addAttribute("usuario", usuarioEncontrado);
            return "main";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "login";
        }
    }

    // Mostrar el formulario de registro
    @GetMapping("/registro")
    public String registroForm(ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Procesar el registro
    @PostMapping("/registro")
    public String addUsuario(@ModelAttribute Usuario user, RedirectAttributes redirectAttributes) {
        String passwordEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncriptada);
        user.setFechaCreacion(new Date());

        usuarioService.guardarUsuario(user);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado exitosamente.");
        return "redirect:/main";
    }
}
