package com.example.password_generator.controller;

import com.example.password_generator.service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})

public class PageController {
    private final PasswordService service;

    public PageController(PasswordService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) Integer length,
                        @RequestParam (defaultValue ="false") boolean upper,
                        @RequestParam (defaultValue ="false") boolean lower,
                        @RequestParam (defaultValue ="false") boolean digits,
                        @RequestParam (defaultValue ="false") boolean symbols,
                        Model model) {
        String generated = null;
        if (length != null) {
            generated = service.generate(length, upper, lower, digits, symbols);
        }
        model.addAttribute("password", generated);
        return "index";
    }
}
