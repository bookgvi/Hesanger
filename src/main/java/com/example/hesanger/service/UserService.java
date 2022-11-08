package com.example.hesanger.service;

import com.example.hesanger.Repos.UserRepo;
import com.example.hesanger.domain.Role;
import com.example.hesanger.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Value("${spring.mail.username}")
    private String userName;
    @Autowired
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb == null) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            userRepo.save(user);

            if (StringUtils.hasLength(user.getEmail())) {
                String message = String.format(
                        "Hey, %s,\n\t" +
                                "http://localhost:8080/activation/%s", user.getUsername(), user.getActivationCode()
                );
                MailService.BUILDER
                        .mailFrom(userName)
                        .mailTo(user.getEmail())
                        .subject("Activation code")
                        .message(message)
                        .send();
            }
            return true;
        }
        return false;
    }

    public boolean activateUser(String code) {
        User userFromDb = userRepo.findByActivationCode(code);
        if (userFromDb == null) return false;
        userFromDb.setActivationCode(null);
        userRepo.save(userFromDb);
        return true;
    }
}
