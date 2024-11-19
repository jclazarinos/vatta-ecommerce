package com.vatta.controller;

import com.vatta.dto.UserDTO;
import com.vatta.model.User;
import com.vatta.service.UserService;
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
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        Optional<User> user = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        if (user.isPresent()) {
            // Simulación de sesión (puedes usar HttpSession si prefieres)
            model.addAttribute("username", user.get().getUsername());
            return "redirect:/"; // Redirige a la página principal
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user);
        model.addAttribute("success", "User registered successfully!");
        return "auth/login";
    }
}
