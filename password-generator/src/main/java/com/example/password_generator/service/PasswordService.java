package com.example.password_generator.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.password_generator.util.CharPool;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final SecureRandom random = new SecureRandom();

    public String generate(int length, boolean upper, boolean lower, boolean digits, boolean symbols) {
        if (length < 8) throw new IllegalArgumentException("length must have 8 or more characters");
        // Monta os grupos ativos
        List<String> activeGroups = new ArrayList<>();
        if (upper)  activeGroups.add(CharPool.UPPER);
        if (lower)  activeGroups.add(CharPool.LOWER);
        if (digits) activeGroups.add(CharPool.DIGITS);
        if (symbols)activeGroups.add(CharPool.SYMBOLS);

        if (activeGroups.isEmpty()) {
            throw new IllegalArgumentException("At least one character group must be selected");
        }
        if (length < activeGroups.size()) {
            throw new IllegalArgumentException("length must be >= number of selected groups");
        }

        String pool = CharPool.buildPool(upper, lower, digits, symbols);

        // 1) Garante 1 de cada grupo selecionado
        List<Character> chars = new ArrayList<>(length);
        for (String group : activeGroups) {
            chars.add(randomChar(group));
        }

        // 2) Preenche o resto com qualquer caractere do pool
        int remaining = length - chars.size();
        for (int i = 0; i < remaining; i++) {
            chars.add(randomChar(pool));
        }

        // 3) Embaralha para não ficar previsível (primeiros N são de cada grupo)
        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder(length);
        for (char c : chars) sb.append(c);
        return sb.toString();
    }

    private char randomChar(String from) {
        int idx = random.nextInt(from.length());
        return from.charAt(idx);
    }
}
