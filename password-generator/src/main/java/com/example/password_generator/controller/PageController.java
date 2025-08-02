package com.example.password_generator.controller;

import com.example.password_generator.model.PasswordRecord;
import com.example.password_generator.repository.PasswordRecordRepository;
import com.example.password_generator.service.EmailService;
import com.example.password_generator.service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PageController {

    private final PasswordService service;
    private final EmailService emailService;
    private final PasswordRecordRepository repository;

    public PageController(PasswordService service,
                          EmailService emailService,
                          PasswordRecordRepository repository) {
        this.service = service;
        this.emailService = emailService;
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping ("/")
    public String generateAndSend(@RequestParam Integer length,
                                  @RequestParam(defaultValue = "false") boolean upper,
                                  @RequestParam(defaultValue = "false") boolean lower,
                                  @RequestParam(defaultValue = "false") boolean digits,
                                  @RequestParam(defaultValue = "false") boolean symbols,
                                  @RequestParam(required = false) String email,
                                  @RequestParam String action,
                                  Model model) {
        try {
            String password = service.generate(length, upper, lower, digits, symbols);
            model.addAttribute("password", password);

            if ("send".equals(action)) {
                if (email == null || email.isBlank()) {
                    model.addAttribute("error", "Por favor, preencha o e-mail antes de enviar.");
                    return "index";
                }

                // Envia por e-mail
                emailService.sendPassword(email, password);

                // Salva no banco
                PasswordRecord record = new PasswordRecord();
                record.setEmail(email);
                record.setPassword(password);
                repository.save(record);

                model.addAttribute("success", "Senha enviada com sucesso para " + email + "!");
            }

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }
}
