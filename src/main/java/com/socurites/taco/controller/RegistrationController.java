package com.socurites.taco.controller;

import com.socurites.taco.dto.RegistrationForm;
import com.socurites.taco.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping
  public String registerForm() {
    return "registration";
  }

  @PostMapping
  public String processRegistration(RegistrationForm form) {
    userRepository.save(form.toUser(passwordEncoder));

    return "redirect:/login";
  }
}
