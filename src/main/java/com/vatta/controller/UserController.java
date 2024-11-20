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
        Optional<User> userOpt = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Debug logs
            System.out.println("Login exitoso:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());
            
            // Guardar en sesión
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "auth/login";
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

    @PostMapping("/register/admin")
    public String registerAdmin(@ModelAttribute("user") User user, Model model) {
        userService.registerAdmin(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalida la sesión al cerrar sesión
        return "redirect:/auth/login";  // Redirige al login
    }
}
