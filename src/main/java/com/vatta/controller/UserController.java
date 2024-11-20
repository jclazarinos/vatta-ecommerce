package com.vatta.controller;

import com.vatta.dto.UserDTO;
import com.vatta.model.User;
import com.vatta.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/login"; // Página de login
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("userDTO") UserDTO userDTO, Model model, HttpSession session) {
        Optional<User> user = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        if (user.isPresent()) {
            System.out.println("Usuario autenticado: " + user.get().getUsername());
            session.setAttribute("username", user.get().getUsername());
            session.setAttribute("userRole", user.get().getRole()); // Almacenar el rol del usuario en la sesión
            return "redirect:/";  // Redirige a la página principal (home)
        } else {
            System.out.println("Autenticación fallida para: " + userDTO.getUsername());
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "auth/login";  // Vuelve al formulario de login
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register"; // Página de registro
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user);  // Registrar el usuario en el sistema
        model.addAttribute("success", "Usuario registrado con éxito!");
        return "auth/login";  // Redirige a la página de login después del registro
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalida la sesión al cerrar sesión
        return "redirect:/auth/login";  // Redirige al login
    }
}
