package com.App.StudentManagement.Service;

import com.App.StudentManagement.ErrorHandler.InvalidCredentialsException;
import com.App.StudentManagement.ErrorHandler.UserNotFoundException;
import com.App.StudentManagement.Model.User;
import com.App.StudentManagement.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.StudentManagement.Security.SecurityConfig;
import com.App.StudentManagement.Security.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.App.StudentManagement.Security.JwtTokenProvider;

import java.util.List;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final   JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Kayıt olma
        public void registerUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

        // Login
        public String login(String username, String password) {
            User user = userRepository.findByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return jwtTokenProvider.generateToken(user);
            } else {
                throw new InvalidCredentialsException("Geçersiz kullanıcı adı veya şifre.");
            }
        }



        // Kullanıcı bilgilerini güncelleme
        public void updateUser(User user) {
            User existingUser = userRepository.findById(user.getUserId()).orElse(null);
            if (existingUser != null) {
                existingUser.setFirstName((user.getFirstName()));
                existingUser.setLastName(user.getLastName());
                existingUser.setUsername(user.getUsername());
                existingUser.setSchoolNo(user.getSchoolNo());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
            } else {
                throw new UserNotFoundException("Kullanıcı bulunamadı.");
            }
        }

        // Kullanıcı schoolNo ile kullanıcı bilgisi getirme
        public User getUserBySchoolNo(int schoolNo) {
            return userRepository.findBySchoolNo(schoolNo);
        }


        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        // Kullanıcı silme

        public void deleteUser(Long userId) {
            userRepository.deleteById(userId);
        }
    }


