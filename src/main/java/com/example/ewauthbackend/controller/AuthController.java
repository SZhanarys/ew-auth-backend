package com.example.ewauthbackend.controller;

import com.example.ewauthbackend.dto.AuthRequest;
import com.example.ewauthbackend.dto.AuthResponse;
import com.example.ewauthbackend.model.User;
import com.example.ewauthbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // Проверяем, нет ли уже такого пользователя
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email уже занят"));
        }

        // Создаем и сохраняем нового пользователя
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // В будущем тут обязательно нужно хеширование!
        userRepository.save(user);

        System.out.println("Пользователь сохранен в БД: " + email);
        return ResponseEntity.ok(Map.of("token", "success-token-from-db"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)) // Сверяем пароль
                .map(user -> ResponseEntity.ok(Map.of("token", "login-token-123")))
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Неверный логин или пароль")));
    }
}
