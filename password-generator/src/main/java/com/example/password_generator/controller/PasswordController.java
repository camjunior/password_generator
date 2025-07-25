package com.example.password_generator.controller;

import com.example.password_generator.service.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PasswordController {
    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @GetMapping("/api/password")
    public ResponseEntity<Map<String, Object>> generate(
            @RequestParam int length,
            @RequestParam(defaultValue = "true") boolean upper,
            @RequestParam(defaultValue = "true") boolean lower,
            @RequestParam(defaultValue = "true") boolean digits,
            @RequestParam(defaultValue = "false") boolean symbols) {

        String pwd = service.generate(length, upper, lower, digits, symbols);
        return ResponseEntity.ok(Map.of("length", length, "password", pwd));
    }

}
