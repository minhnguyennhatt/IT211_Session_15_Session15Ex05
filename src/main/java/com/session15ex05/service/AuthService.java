package com.session15ex05.service;

import com.session15ex05.dto.AuthRequest;
import com.session15ex05.entity.User;
import com.session15ex05.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean register(AuthRequest request){
        String username = request.getUsername();
        String password = request.getPassword();

        if(userRepository.existsByUsername(username)){
            return false;
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .build();

        userRepository.save(user);
        return true;
    }

    public boolean login(AuthRequest request){
        return userRepository.findByUsername(request.getUsername())
                .map(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElse(false);
    }

}
