package com.thymeleaf.demo.thymeleaf.controller;
import ch.qos.logback.core.model.Model;
import com.thymeleaf.demo.thymeleaf.model.Usuario;
import com.thymeleaf.demo.thymeleaf.repository.UsuarioRepository;
import com.thymeleaf.demo.thymeleaf.service.UsuarioService;
import com.thymeleaf.demo.thymeleaf.service.UsuarioServiceImpl;
import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("user")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    PasswordEncoder passwordEncoder;

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

        // Encriptar la contraseña del usuario antes de guardarla
        String passwordEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncriptada);

        // Establecer la fecha de creación
        user.setFechaCreacion(new Date());

        // Guardar el usuario con la contraseña encriptada
        usuarioService.guardarUsuario(user);

        // Añadir mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado exitosamente.");

        // Redirigir a la página principal o a donde desees
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


}
