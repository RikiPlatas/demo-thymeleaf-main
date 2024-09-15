package com.thymeleaf.demo.thymeleaf.controller;
import ch.qos.logback.core.model.Model;
import com.thymeleaf.demo.thymeleaf.model.Usuario;
import com.thymeleaf.demo.thymeleaf.repository.UsuarioRepository;
import com.thymeleaf.demo.thymeleaf.service.UsuarioService;
import com.thymeleaf.demo.thymeleaf.service.UsuarioServiceImpl;
import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/main")
    public String index() {
        return "main"; // Nombre de la plantilla Thymeleaf que deseas cargar por defecto
    }


    @GetMapping("/getUsuario/{id}")
    public String getUsuario(@PathVariable Long id, ModelMap model) {
        Usuario user = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/guardar")
    public String addUsuario(@ModelAttribute Usuario user, RedirectAttributes redirectAttributes) {

        user.setFechaCreacion(new Date());
        usuarioService.guardarUsuario(user);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado exitosamente.");
        return "redirect:/main";
    }

    @PutMapping("/updateUsuario/{id}")
    public String updateUsuario(@PathVariable Long id, @ModelAttribute Usuario user) {
        user.setId(id); // Asignamos el ID del usuario a actualizar
        usuarioService.guardarUsuario(user);
        return "redirect:/main";
    }

    @DeleteMapping("/deleteUsuario/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
        return "redirect:/main";
    }

    // Mostrar el formulario de registro
    @GetMapping("/registro")
    public String registroForm(ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Mostrar el formulario de login
    @GetMapping("/login")
    public String loginForm(ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }


    @PostMapping("/login2")
    public String loginSubmit(@RequestParam String nick, @RequestParam String password, ModelMap model) {
        // Buscar el usuario por nombre de usuario (nick)
        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorNombre(nick);

        // Verificar si el usuario existe y si la contraseña es correcta
        if (usuarioEncontrado != null && usuarioService.verificarLogin(usuarioEncontrado,password)) {
            // Autenticación exitosa
            // Aquí puedes guardar información del usuario en la sesión si es necesario
            model.addAttribute("usuario", usuarioEncontrado);
            return "main"; // Redirige a la vista principal o a otro recurso
        } else {
            // Autenticación fallida
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "login"; // Redirige de nuevo al formulario de login
        }
    }
}
