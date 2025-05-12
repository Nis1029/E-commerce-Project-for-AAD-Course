package com.eticaret.backend.security;

import com.eticaret.backend.model.User;
import com.eticaret.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));

        if (user.getRole() == null || user.getRole().isBlank()) {
            throw new RuntimeException("Kullanıcının rolü atanamamış.");
        }
        String role = user.getRole(); // örn. "ROLE_USER"
        System.out.println("🔍 ROL: " + role);


        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(role) // Eğer "ROLE_" ile başlıyorsa bu doğrudan geçerli authority olur
                .build();

        System.out.println("✅ UserDetails Authorities: " + userDetails.getAuthorities());

        return userDetails;
    }

}