package com.example.springbootmusic.controller;

import com.example.springbootmusic.model.dto.RegisterUserDTO;
import com.example.springbootmusic.service.AuthentificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    private AuthentificationService authentificationService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterUserDTO());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(Model model, RegisterUserDTO dto, HttpServletRequest request) {
        try {
            authentificationService.register(dto, request);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "account/register";
        }
    }
}
